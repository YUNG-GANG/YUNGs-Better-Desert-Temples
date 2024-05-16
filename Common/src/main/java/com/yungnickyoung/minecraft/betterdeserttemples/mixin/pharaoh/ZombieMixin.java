package com.yungnickyoung.minecraft.betterdeserttemples.mixin.pharaoh;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.entity.IPharaohData;
import com.yungnickyoung.minecraft.betterdeserttemples.util.PharaohUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class ZombieMixin {
    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    private void betterdeserttemples_readPharaohOriginalSpawnPosFromNbt(CompoundTag compoundTag, CallbackInfo info) {
        if (PharaohUtil.isPharaoh(this)) {
            ListTag originalSpawnPos = compoundTag.getList("bdtOriginalSpawnPos", ListTag.TAG_DOUBLE);

            if (originalSpawnPos.size() != 3) {
//                BetterDesertTemplesCommon.LOGGER.error("Pharaoh entity is missing original spawn position data. Unable to read original spawn position.");
                return;
            }

            double spawnX = originalSpawnPos.getDouble(0);
            double spawnY = originalSpawnPos.getDouble(1);
            double spawnZ = originalSpawnPos.getDouble(2);
            ((IPharaohData) this).setOriginalSpawnPos(new Vec3(spawnX, spawnY, spawnZ));
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void betterdeserttemples_writePharaohOriginalSpawnPosToNbt(CompoundTag compoundTag, CallbackInfo info) {
        if (PharaohUtil.isPharaoh(this)) {
            Vec3 originalSpawnPos = ((IPharaohData) this).getOriginalSpawnPos();

            if (originalSpawnPos == null) {
//                BetterDesertTemplesCommon.LOGGER.error("Pharaoh entity is missing original spawn position data. Unable to write original spawn position to NBT.");
                return;
            }

            ListTag originalSpawnPosList = new ListTag();
            originalSpawnPosList.add(DoubleTag.valueOf(originalSpawnPos.x));
            originalSpawnPosList.add(DoubleTag.valueOf(originalSpawnPos.y));
            originalSpawnPosList.add(DoubleTag.valueOf(originalSpawnPos.z));
            compoundTag.put("bdtOriginalSpawnPos", originalSpawnPosList);
        }
    }
}
