package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.TagModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 * Applies/removes mining fatigue from players in temples, based on if the temple has been "cleared",
 * i.e. pharaoh has been killed.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTickMixin extends Player {
    public ServerPlayerTickMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Shadow
    public ServerGamePacketListenerImpl connection;

    @Shadow
    public abstract ServerLevel getLevel();

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectMethod(CallbackInfo info) {
        if (this.tickCount % 20 == 0) {
            if (!this.getLevel().getServer().getWorldData().worldGenSettings().generateFeatures()) return;
            if (!BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue) return;

            // Here we begin to find the nearest mining fatigue structure. By default, this is just Better Desert Temples,
            // but other mods may add their structures to the tag as well.
            BlockPos playerPos = this.blockPosition();
            Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> nearestStructurePair;
            Optional<HolderSet.Named<ConfiguredStructureFeature<?, ?>>> miningFatigueStructures = this.getLevel()
                    .registryAccess()
                    .registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY)
                    .getTag(TagModule.APPLIES_MINING_FATIGUE);

            // Stop if no mining fatigue structures found at all
            if (miningFatigueStructures.isEmpty()) {
                return;
            }

            // Find nearest mining fatigue structure
            nearestStructurePair = this.getLevel()
                    .getChunkSource()
                    .getGenerator()
                    .findNearestMapFeature(this.getLevel(), miningFatigueStructures.get(), playerPos, 8, false);

            // Stop if no mining fatigue structures found within search radius
            if (nearestStructurePair == null) {
                return;
            }

            StructureStart structureStart = this.getLevel().structureFeatureManager().getStructureWithPieceAt(playerPos, nearestStructurePair.getSecond().value());

            // Do not apply mining fatigue if player is not in structure or config option is disabled
            boolean isPlayerInMiningFatigueStructure = this.getLevel().isLoaded(playerPos) && structureStart.isValid();
            if (!isPlayerInMiningFatigueStructure) return;

            // Do not apply mining fatigue if temple has already been cleared (i.e. pharaoh killed)
            boolean isTempleCleared = ((ITempleStateCacheProvider)this.getLevel()).getTempleStateCache().isTempleCleared(structureStart.getChunkPos().getWorldPosition());
            if (isTempleCleared) return;

            // Apply mining fatigue
            if (!this.hasEffect(MobEffects.DIG_SLOWDOWN) || this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier() < 2 || this.getEffect(MobEffects.DIG_SLOWDOWN).getDuration() < 120) {
                if (!this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    this.connection.send(new ClientboundSoundPacket(SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F));
                }
                this.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 2), this);
            }
        }
    }
}
