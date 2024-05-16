package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.TagModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies/removes mining fatigue from players in temples, based on if the temple has been "cleared",
 * i.e. pharaoh has been killed.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTickMixin extends Player {
    @Shadow
    public ServerGamePacketListenerImpl connection;

    public ServerPlayerTickMixin(Level $$0, BlockPos $$1, float $$2, GameProfile $$3) {
        super($$0, $$1, $$2, $$3);
    }

    @Shadow @Final public ServerPlayerGameMode gameMode;

    @Shadow public abstract ServerLevel serverLevel();

    @Inject(method = "tick", at = @At("HEAD"))
    private void betterdeserttemples_playerTick(CallbackInfo info) {
        if (this.gameMode.isSurvival() && this.tickCount % 100 == 0) { // Check every 5 seconds
            // Do not apply mining fatigue if config option is disabled
            if (!BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue) return;

            BlockPos playerPos = this.blockPosition();
            StructureStart structureStart = this.serverLevel().structureManager().getStructureWithPieceAt(playerPos, TagModule.APPLIES_MINING_FATIGUE);

            // Do not apply mining fatigue if player is not in temple
            boolean isInTemple = this.serverLevel().isLoaded(playerPos) && structureStart.isValid();
            if (!isInTemple) return;

            // Do not apply mining fatigue if temple has been cleared
            boolean isTempleCleared = ((ITempleStateCacheProvider) this.serverLevel()).getTempleStateCache().isTempleCleared(structureStart.getChunkPos().getWorldPosition());
            if (isTempleCleared) return;

            // Apply mining fatigue
            if (!this.hasEffect(MobEffects.DIG_SLOWDOWN) || this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier() < 2 || this.getEffect(MobEffects.DIG_SLOWDOWN).getDuration() < 120) {
                if (!this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    this.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ELDER_GUARDIAN_CURSE), SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F, this.serverLevel().getSeed()));
                }
                this.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 2), this);
            }
        }
    }
}
