package com.yungnickyoung.minecraft.betterdeserttemples.init;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemples;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class BDModConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_DESERT_TEMPLE = BDTModStructureFeatures.DESERT_TEMPLE.get()
            .configured(new YungJigsawConfig(new ResourceLocation(BetterDesertTemples.MOD_ID, "starts"), 20));
}
