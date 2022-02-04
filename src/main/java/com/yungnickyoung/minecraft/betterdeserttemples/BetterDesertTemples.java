package com.yungnickyoung.minecraft.betterdeserttemples;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.betterdeserttemples.init.*;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(BetterDesertTemples.MOD_ID)
public class BetterDesertTemples {
    public static final String MOD_ID = "betterdeserttemples";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /**
     * Lists of whitelisted dimensions and blacklisted biomes.
     * Will be reinitialized later w/ values from config.
     */
    public static List<String> whitelistedDimensions = Lists.newArrayList("minecraft:overworld");
    public static List<String> blacklistedBiomes = Lists.newArrayList();
    public static List<String> additionalWhitelistedBiomes = Lists.newArrayList();


    public BetterDesertTemples() {
        init();
    }

    private void init() {
        BDTModConfig.init();
        BDTModProcessors.init();
        BDTModWorld.init();
        BDTModStructureFeatures.init();
    }
}
