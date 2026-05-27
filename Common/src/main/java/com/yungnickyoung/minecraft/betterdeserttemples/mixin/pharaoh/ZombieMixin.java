package com.yungnickyoung.minecraft.betterdeserttemples.mixin.pharaoh;

import com.yungnickyoung.minecraft.betterdeserttemples.entity.IPharaohData;
import com.yungnickyoung.minecraft.betterdeserttemples.util.PharaohUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Zombie.class)
public class ZombieMixin {
    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    private void betterdeserttemples_readPharaohOriginalSpawnPosFromNbt(final ValueInput input, final CallbackInfo ci) {
        if (PharaohUtil.isPharaoh(this)) {
            input.read("bdtOriginalSpawnPos", Vec3.CODEC).ifPresent(originalSpawnPos -> {
                ((IPharaohData) this).setOriginalSpawnPos(originalSpawnPos);
            });

        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void betterdeserttemples_writePharaohOriginalSpawnPosToNbt(final ValueOutput output, final CallbackInfo ci) {
        if (PharaohUtil.isPharaoh(this)) {
            Vec3 originalSpawnPos = ((IPharaohData) this).getOriginalSpawnPos();
            output.store("bdtOriginalSpawnPos", Vec3.CODEC, originalSpawnPos);
        }
    }
}
