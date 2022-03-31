package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

/**
 * Applies/removes mining fatigue from players in temples, based on if the temple has been "cleared",
 * i.e. pharaoh has been killed.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTickMixin extends Player {
    private static final ResourceLocation templeResourceLocation = new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple");

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
            BlockPos blockpos = this.blockPosition();
            ResourceKey<ConfiguredStructureFeature<?, ?>> templeKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, templeResourceLocation);
            StructureStart templeStart = this.getLevel().structureFeatureManager().getStructureWithPieceAt(blockpos, templeKey);

            // Do not apply mining fatigue if player is not in temple or config option is disabled
            boolean isInTemple = this.level instanceof ServerLevel
                    && this.level.isLoaded(blockpos)
                    && templeStart.isValid();
            if (!isInTemple || !BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue) return;

            // Do not apply mining fatigue if temple has been cleared
            boolean isTempleCleared = ((ITempleStateCacheProvider)this.getLevel()).getTempleStateCache().isTempleCleared(templeStart.getChunkPos().getWorldPosition());
            if (isTempleCleared) {
                return;
            }

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
