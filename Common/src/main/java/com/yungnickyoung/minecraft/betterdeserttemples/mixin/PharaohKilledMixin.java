package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.TempleStateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class PharaohKilledMixin extends Entity {
    private static final ResourceLocation templeResourceLocation = new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple");

    public PharaohKilledMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void die(DamageSource damageSource, CallbackInfo info) {
        if (isHusk(this) && level instanceof ServerLevel) {
            ResourceKey<ConfiguredStructureFeature<?, ?>> templeKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, templeResourceLocation);
            StructureStart structureStart = ((ServerLevel) this.level).structureFeatureManager().getStructureWithPieceAt(this.blockPosition(), templeKey);
            if (structureStart.isValid()) {
                BlockPos originPos = structureStart.getChunkPos().getWorldPosition();

                // Clear temple state
                TempleStateManager.setTempleCleared(originPos, true);

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

    private boolean isHusk(Object object) {
        return object instanceof Husk;
    }
}
