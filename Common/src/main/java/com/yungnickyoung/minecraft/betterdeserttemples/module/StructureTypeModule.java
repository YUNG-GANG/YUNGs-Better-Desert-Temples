package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.betterdeserttemples.world.structure.BetterDesertTempleStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureTypeModule {
    public static StructureType<BetterDesertTempleStructure> BETTER_DESERT_TEMPLE = () -> BetterDesertTempleStructure.CODEC;

    public static void init() {
        Services.REGISTRY.registerStructureType(new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple"), BETTER_DESERT_TEMPLE);
    }
}
