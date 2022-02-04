package com.yungnickyoung.minecraft.betterdeserttemples.init;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemples;
import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class BDTModConfig {
    public static void init() {
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, BDTConfig.SPEC, "betterdeserttemples-forge-1_18.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(BDTModConfig::configChanged);
    }

    /**
     * Parses the dimensions & biomes strings and updates the stored values.
     */
    public static void configChanged(ModConfigEvent event) {
        ModConfig config = event.getConfig();

        if (config.getSpec() == BDTConfig.SPEC) {
            /*
             * Dimension whitelisting
             */
            String rawStringofList = BDTConfig.whitelistedDimensions.get();
            int strLen = rawStringofList.length();

            // Validate the string's format
            if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
                BetterDesertTemples.LOGGER.error("INVALID VALUE FOR SETTING 'Whitelisted Dimensions'. Using [minecraft:overworld] instead...");
                BetterDesertTemples.whitelistedDimensions = Lists.newArrayList("minecraft:overworld");
            } else {
                // Parse string to list
                BetterDesertTemples.whitelistedDimensions = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
            }

            /*
             * Biome Blacklisting
             */
            rawStringofList = BDTConfig.blacklistedBiomes.get();
            strLen = rawStringofList.length();

            // Validate the string's format
            if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
                BetterDesertTemples.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using default instead...");
                BetterDesertTemples.blacklistedBiomes = Lists.newArrayList();
            } else {
                // Parse string to list
                BetterDesertTemples.blacklistedBiomes = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
            }

            /*
             * Biome Whitelisting
             */
            rawStringofList = BDTConfig.additionalWhitelistedBiomes.get();
            strLen = rawStringofList.length();

            // Validate the string's format
            if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
                BetterDesertTemples.LOGGER.error("INVALID VALUE FOR SETTING 'Additional Whitelisted Biomes'. Using default instead...");
                BetterDesertTemples.additionalWhitelistedBiomes = Lists.newArrayList();
            } else {
                // Parse string to list
                BetterDesertTemples.additionalWhitelistedBiomes = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
            }
        }
    }
}
