package com.yungnickyoung.minecraft.betterdeserttemples.mixin.pharaoh;

import com.yungnickyoung.minecraft.betterdeserttemples.entity.IPharaohData;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Husk.class)
public class HuskMixin implements IPharaohData {
    @Unique
    private Vec3 bdtOriginalSpawnPos;

    @Unique
    @Override
    public void setOriginalSpawnPos(Vec3 pos) {
        this.bdtOriginalSpawnPos = pos;
    }

    @Unique
    @Override
    public Vec3 getOriginalSpawnPos() {
        return this.bdtOriginalSpawnPos;
    }
}
