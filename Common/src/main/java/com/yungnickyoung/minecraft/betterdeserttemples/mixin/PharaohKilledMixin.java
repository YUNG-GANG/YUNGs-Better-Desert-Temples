package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

/**
 * Update's a temple's "cleared" status when its pharaoh mob is killed.
 * This status update is immediately stored to file, so that the temple remains "cleared" after a server restart.
 * For more information on this persistence, see {@link com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateCache}
 * and {@link com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateRegion}.
 */
@Mixin(LivingEntity.class)
public abstract class PharaohKilledMixin extends Entity {
    private static final ResourceLocation templeResourceLocation = new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple");

    public PharaohKilledMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void die(DamageSource damageSource, CallbackInfo info) {
        if (level instanceof ServerLevel && isPharaoh(this)) {
            ResourceKey<ConfiguredStructureFeature<?, ?>> templeKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, templeResourceLocation);
            StructureStart structureStart = ((ServerLevel) this.level).structureFeatureManager().getStructureWithPieceAt(this.blockPosition(), templeKey);
            if (structureStart.isValid()) {
                BlockPos originPos = structureStart.getChunkPos().getWorldPosition();

                // Clear temple state
                ((ITempleStateCacheProvider) this.level).getTempleStateCache().setTempleCleared(originPos, true);

                // Clear mining fatigue from all players in temple
                List<ServerPlayer> players = ((ServerLevel) this.level).players();
                players.forEach(player -> {
                    if (level.isLoaded(player.blockPosition()) && ((ServerLevel) level).structureFeatureManager().getStructureWithPieceAt(player.blockPosition(), templeKey).isValid()) {
                        player.connection.send(new ClientboundSoundPacket(SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F));
                        player.removeEffect(MobEffects.DIG_SLOWDOWN);
                    }
                });

                BetterDesertTemplesCommon.LOGGER.info("CLEARED TEMPLE AT {}", originPos);
            }
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
