package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.betterdeserttemples.world.placement.BetterDesertTemplePlacement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class StructurePlacementTypeModule {
    public static final StructurePlacementType<BetterDesertTemplePlacement> BETTER_DESERT_TEMPLE_PLACEMENT = () -> BetterDesertTemplePlacement.CODEC;

    public static void init() {
        Services.REGISTRY.registerStructurePlacementType(new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "desert_temple"), BETTER_DESERT_TEMPLE_PLACEMENT);
    }
}
