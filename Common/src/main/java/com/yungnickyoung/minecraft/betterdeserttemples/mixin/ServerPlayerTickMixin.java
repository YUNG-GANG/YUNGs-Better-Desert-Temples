package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTickMixin extends Player {
    private static final ResourceLocation templeResourceLocation = new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple");

    public ServerPlayerTickMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectMethod(CallbackInfo info) {
        if (this.tickCount % 20 == 0) {
            BlockPos blockpos = this.blockPosition();
            ResourceKey<ConfiguredStructureFeature<?, ?>> templeKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, templeResourceLocation);
            boolean isInTemple = this.level instanceof ServerLevel
                    && this.level.isLoaded(blockpos)
                    && ((ServerLevel) this.level).structureFeatureManager().getStructureWithPieceAt(blockpos, templeKey).isValid();
            if (isInTemple && BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue) {
                if (!this.hasEffect(MobEffects.DIG_SLOWDOWN) || this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier() < 2 || this.getEffect(MobEffects.DIG_SLOWDOWN).getDuration() < 120) {
                    this.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 2), this);
                }
            }
        }
    }
}
