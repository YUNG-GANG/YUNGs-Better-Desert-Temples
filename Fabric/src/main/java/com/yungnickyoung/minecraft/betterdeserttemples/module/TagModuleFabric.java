package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagModuleFabric {
    public static void init() {
        TagModule.HAS_BETTER_DESERT_TEMPLE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "has_better_desert_temple"));
        TagModule.APPLIES_MINING_FATIGUE = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, "applies_mining_fatigue"));
    }
}
