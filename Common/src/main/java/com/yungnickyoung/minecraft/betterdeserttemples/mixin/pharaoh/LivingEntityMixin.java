package com.yungnickyoung.minecraft.betterdeserttemples.mixin.pharaoh;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.entity.IPharaohData;
import com.yungnickyoung.minecraft.betterdeserttemples.module.TagModule;
import com.yungnickyoung.minecraft.betterdeserttemples.util.PharaohUtil;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void betterdeserttemples_clearTempleOnPharaohDeath(DamageSource damageSource, CallbackInfo info) {
        if (!(level() instanceof ServerLevel serverLevel)) return;
        if (!PharaohUtil.isPharaoh(this)) return;

        // We determine which temple to clear based on the pharaoh's original spawn position.
        Vec3 originalSpawnPos = ((IPharaohData) this).getOriginalSpawnPos();

        if (originalSpawnPos == null) {
            // No original spawn position data -> clear the temple the pharaoh is inside of
            BetterDesertTemplesCommon.LOGGER.error("Pharaoh entity is missing original spawn position data. Attempting to clear the temple it's inside of instead...");
            tryClearTempleAtPosition(serverLevel, this.blockPosition(), damageSource);
        } else {
            // Clear the temple at the pharaoh's original spawn position
            BlockPos pharaohSpawnPos = new BlockPos((int) originalSpawnPos.x, (int) originalSpawnPos.y, (int) originalSpawnPos.z);
            tryClearTempleAtPosition(serverLevel, pharaohSpawnPos, damageSource);
        }
    }

    @Unique
    private void tryClearTempleAtPosition(ServerLevel serverLevel, BlockPos pos, DamageSource damageSource) {
        StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(pos, TagModule.APPLIES_MINING_FATIGUE);

        if (structureStart.isValid()) {
            BlockPos structureStartPos = structureStart.getChunkPos().getWorldPosition();

            // Clear temple state
            ((ITempleStateCacheProvider) this.level()).getTempleStateCache().setTempleCleared(structureStartPos, true);

            // Collect list of all players in the temple
            List<ServerPlayer> playersInTemple = serverLevel.players().stream()
                    .filter(player -> level().isLoaded(player.blockPosition())
                            && serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), TagModule.APPLIES_MINING_FATIGUE).isValid())
                    .toList();

            // Clear mining fatigue for all players in the temple
            playersInTemple.forEach(player -> {
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BEACON_DEACTIVATE), SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F, serverLevel.getSeed()));
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
            });

            // Also clear mining fatigue for the player who killed the pharaoh, just in case they aren't in the temple
            if (damageSource.getEntity() instanceof ServerPlayer killer && !playersInTemple.contains(killer)) {
                killer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BEACON_DEACTIVATE), SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F, serverLevel.getSeed()));
                killer.removeEffect(MobEffects.DIG_SLOWDOWN);
            }

            BetterDesertTemplesCommon.LOGGER.info("Cleared Better Desert Temple at x={}, z={}", structureStartPos.getX(), structureStartPos.getZ());
        } else {
            BetterDesertTemplesCommon.LOGGER.error("Position provided is not inside a Better Desert Temple. Unable to clear temple.");
        }
    }
}
