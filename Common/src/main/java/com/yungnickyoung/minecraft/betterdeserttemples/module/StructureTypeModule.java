package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.structure.BetterDesertTempleStructure;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.minecraft.world.level.levelgen.structure.StructureType;

@AutoRegister(BetterDesertTemplesCommon.MOD_ID)
public class StructureTypeModule {
    @AutoRegister("desert_temple")
    public static StructureType<BetterDesertTempleStructure> BETTER_DESERT_TEMPLE = () -> BetterDesertTempleStructure.CODEC;
}
