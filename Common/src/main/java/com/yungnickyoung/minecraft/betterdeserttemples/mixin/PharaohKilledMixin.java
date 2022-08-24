package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.TagModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

/**
 * Update's a temple's "cleared" status when its pharaoh mob is killed.
 * This status update is immediately stored to file, so that the temple remains "cleared" after a server restart.
 * For more information on this persistence, see {@link com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateCache}
 * and {@link com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateRegion}.
 */
@Mixin(LivingEntity.class)
public abstract class PharaohKilledMixin extends Entity {
    public PharaohKilledMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void betterdeserttemples_pharaohDie(DamageSource damageSource, CallbackInfo info) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!isPharaoh(this)) return;
        if (!serverLevel.getServer().getWorldData().worldGenSettings().generateFeatures()) return;

        BlockPos pharaohPos = this.blockPosition();
        Optional<HolderSet.Named<ConfiguredStructureFeature<?, ?>>> miningFatigueStructures = serverLevel
                .registryAccess()
                .registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY)
                .getTag(TagModule.APPLIES_MINING_FATIGUE);

        // Stop if no mining fatigue structures found at all
        if (miningFatigueStructures.isEmpty()) return;

        // Iterate mining fatigue structures to see if any are at the pharaoh's position
        for (Holder<ConfiguredStructureFeature<?, ?>> holder : miningFatigueStructures.get()) {
            StructureStart structureStart = serverLevel.structureFeatureManager().getStructureAt(pharaohPos, holder.value());
            if (!structureStart.isValid()) continue;

            // Structure start pos for structure at pharaoh's position
            BlockPos structureStartPos = structureStart.getChunkPos().getWorldPosition();

            // Clear temple state
            ((ITempleStateCacheProvider) serverLevel).getTempleStateCache().setTempleCleared(structureStartPos, true);

            // Clear mining fatigue from all players in temple
            List<ServerPlayer> players = serverLevel.players();
            players.forEach(player -> {
                BlockPos playerPos = player.blockPosition();

                // Check if player is in mining fatigue structure
                for (Holder<ConfiguredStructureFeature<?, ?>> holder2 : miningFatigueStructures.get()) {
                    StructureStart playerStructureStart = serverLevel.structureFeatureManager().getStructureAt(playerPos, holder2.value());
                    if (!playerStructureStart.isValid()) continue;

                    // Clear mining fatigue if the selected structure start is same as pharaoh's
                    if (serverLevel.isLoaded(player.blockPosition())
                            && playerStructureStart.isValid()
                            && playerStructureStart.getChunkPos().getWorldPosition().equals(structureStartPos)
                    ) {
                        player.connection.send(new ClientboundSoundPacket(SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F));
                        player.removeEffect(MobEffects.DIG_SLOWDOWN);
                    }
                }
            });

            BetterDesertTemplesCommon.LOGGER.info("CLEARED TEMPLE AT {}", structureStartPos);
        }
    }

    private boolean isPharaoh(Object object) {
        if (!(object instanceof Husk)) {
            return false;
        }

        Mob mob = (Mob) object;

        for (ItemStack armorItem : mob.getArmorSlots()) {
            if (armorItem.is(Items.PLAYER_HEAD)) {
                if (!armorItem.hasTag()) continue;
                CompoundTag compoundTag = armorItem.getTag();

                if (compoundTag.contains("SkullOwner", 10)) {
                    CompoundTag skullOwner = compoundTag.getCompound("SkullOwner");

                    if (skullOwner.contains("Properties", 10)) {
                        CompoundTag properties = skullOwner.getCompound("Properties");

                        if (properties.contains("textures", 9)) {
                            ListTag textures = properties.getList("textures", 10);
                            if (textures.size() == 1) {
                                CompoundTag texture1 = (CompoundTag) textures.get(0);

                                if (texture1.getString("Value").equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM1MGMwNDk5YTY4YmNkOWM3NWIyNWMxOTIzMTQzOWIxMDhkMDI3NTlmNDM1ZTMzZTRhZWU5ZWQxZGQyNDFhMiJ9fX0=")) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
