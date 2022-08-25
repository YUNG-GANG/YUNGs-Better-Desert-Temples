package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.TagModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.Nullable;
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

    public ServerPlayerTickMixin(Level $$0, BlockPos $$1, float $$2, GameProfile $$3, @Nullable ProfilePublicKey $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Shadow
    public abstract ServerLevel getLevel();

    @Inject(method = "tick", at = @At("HEAD"))
    private void betterdeserttemples_playerTick(CallbackInfo info) {
        if (this.tickCount % 20 == 0) {
            BlockPos playerPos = this.blockPosition();
            StructureStart structureStart = this.getLevel().structureManager().getStructureWithPieceAt(playerPos, TagModule.APPLIES_MINING_FATIGUE);

            // Do not apply mining fatigue if player is not in temple or config option is disabled
            boolean isInTemple = this.getLevel().isLoaded(playerPos) && structureStart.isValid();
            if (!isInTemple || !BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue) return;

            // Do not apply mining fatigue if temple has been cleared
            boolean isTempleCleared = ((ITempleStateCacheProvider) this.getLevel()).getTempleStateCache().isTempleCleared(structureStart.getChunkPos().getWorldPosition());
            if (isTempleCleared) return;

            // Apply mining fatigue
            if (!this.hasEffect(MobEffects.DIG_SLOWDOWN) || this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier() < 2 || this.getEffect(MobEffects.DIG_SLOWDOWN).getDuration() < 120) {
                if (!this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    this.connection.send(new ClientboundSoundPacket(SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, this.getX(), this.getY(), this.getZ(), 1.0F, 1.0F, ((ServerLevel) level).getSeed()));
                }
                this.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 2), this);
            }
        }
    }
}
