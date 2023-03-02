package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfigFabric;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ArmorStandChances;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ItemFrameChances;
import com.yungnickyoung.minecraft.yungsapi.io.JSON;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigModuleFabric {
    public static final String CUSTOM_CONFIG_PATH = "betterdeserttemples";
    public static final String VERSION_PATH = "fabric-1_19_3";

    public static void init() {
        initCustomFiles();
        AutoConfig.register(BDTConfigFabric.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(BDTConfigFabric.class).registerSaveListener(ConfigModuleFabric::bakeConfig);
        AutoConfig.getConfigHolder(BDTConfigFabric.class).registerLoadListener(ConfigModuleFabric::bakeConfig);
        bakeConfig(AutoConfig.getConfigHolder(BDTConfigFabric.class).get());
    }

    private static InteractionResult bakeConfig(ConfigHolder<BDTConfigFabric> configHolder, BDTConfigFabric configFabric) {
        bakeConfig(configFabric);
        loadJSON();
        return InteractionResult.SUCCESS;
    }

    private static void initCustomFiles() {
        createDirectory();
        createBaseReadMe();
        createJsonReadMe();
        loadJSON();
    }

    private static void loadJSON() {
        loadArmorStandsJSON();
        loadItemFramesJSON();
    }

    private static void createDirectory() {
        File parentDir = new File(FabricLoader.getInstance().getConfigDir().toString(), CUSTOM_CONFIG_PATH);
        File customConfigDir = new File(parentDir, VERSION_PATH);
        try {
            String filePath = customConfigDir.getCanonicalPath();
            if (customConfigDir.mkdirs()) {
                BetterDesertTemplesCommon.LOGGER.info("Creating directory for additional Better Desert Temples configuration at {}", filePath);
            }
        } catch (IOException e) {
            BetterDesertTemplesCommon.LOGGER.error("ERROR creating Better Desert Temples config directory: {}", e.toString());
        }
    }

    private static void createBaseReadMe() {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), CUSTOM_CONFIG_PATH, "README.txt");
        File readme = new File(path.toString());
        if (!readme.exists()) {
            String readmeText =
                    """
                            This directory is for a few additional options for YUNG's Better Desert Temples.
                            Options provided may vary by version.
                            This directory contains subdirectories for supported versions. The first time you run Better Desert Temples, a version subdirectory will be created if that version supports advanced options.
                            For example, the first time you use Better Desert Temples for 1.18.2 on Forge, the 'forge-1_18_2' subdirectory will be created in this folder.
                            If no subdirectory for your version is created, then that version probably does not support the additional options.
                            NOTE -- MOST OPTIONS CAN BE FOUND IN A CONFIG FILE OUTSIDE THIS FOLDER!
                            For example, on Forge 1.18.2 the file is 'betterdeserttemples-forge-1_18_2.toml'.""";
            try {
                Files.write(path, readmeText.getBytes());
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Unable to create README file!");
            }
        }
    }

    private static void createJsonReadMe() {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), CUSTOM_CONFIG_PATH, VERSION_PATH, "README.txt");
        File readme = new File(path.toString());
        if (!readme.exists()) {
            String readmeText =
                    """
                            ######################################
                            #          armorstands.json          #
                            ######################################
                              This file contains ItemRandomizers describing the probability distribution of armor on armor stands.
                            Armor stands spawn in armory rooms and wardrobe rooms.
                            For information on ItemRandomizers, see the bottom of this README.
                            ######################################
                            #          itemframes.json          #
                            ######################################
                              This file contains ItemRandomizers describing the probability distribution of items in item frames.
                            Item frames only spawn in food storage rooms and armoury rooms.
                            For information on ItemRandomizers, see the bottom of this README.
                            ######################################
                            #         ItemRandomizers          #
                            ######################################
                            Describes a set of items and the probability of each item being chosen.
                             - entries: An object where each entry's key is a item, and each value is that item's probability of being chosen.
                                  The total sum of all probabilities SHOULD NOT exceed 1.0!
                             - defaultItem: The item used for any leftover probability ranges.
                                  For example, if the total sum of all the probabilities of the entries is 0.6, then
                                  there is a 0.4 chance of the defaultItem being selected.
                            Here's an example ItemRandomizer:
                            "entries": {
                              "minecraft:stone_axe": 0.25,
                              "minecraft:shield": 0.2,
                              "minecraft:air": 0.1
                            },
                            "defaultItem": "minecraft:iron_axe"
                            For each item, this randomizer has a 25% chance of returning a stone axe, 20% chance of choosing a shield,
                            10% chance of choosing air (nothing), and a 100 - (25 + 20 + 10) = 45% chance of choosing an iron axe (since it's the default item).
                            """;

            try {
                Files.write(path, readmeText.getBytes());
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Unable to create armorstands and itemframes README file!");
            }
        }
    }

    /**
     * If a JSON file already exists, it loads its contents into ArmorStandChances.
     * Otherwise, it creates a default JSON and from the default options in ArmorStandChances.
     */
    private static void loadArmorStandsJSON() {
        Path jsonPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), CUSTOM_CONFIG_PATH, VERSION_PATH, "armorstands.json");
        File jsonFile = new File(jsonPath.toString());

        if (!jsonFile.exists()) {
            // Create default file if JSON file doesn't already exist
            try {
                JSON.createJsonFileFromObject(jsonPath, ArmorStandChances.get());
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Unable to create armorstands.json file: {}", e.toString());
            }
        } else {
            // If file already exists, load data into ArmorStandChances singleton instance
            if (!jsonFile.canRead()) {
                BetterDesertTemplesCommon.LOGGER.error("Better Desert Temples armorstands.json file not readable! Using default configuration...");
                return;
            }

            try {
                ArmorStandChances.instance = JSON.loadObjectFromJsonFile(jsonPath, ArmorStandChances.class);
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Error loading Better Desert Temples armorstands.json file: {}", e.toString());
                BetterDesertTemplesCommon.LOGGER.error("Using default configuration...");
            }
        }
    }

    /**
     * If a JSON file already exists, it loads its contents into ItemFrameChances.
     * Otherwise, it creates a default JSON and from the default options in ItemFrameChances.
     */
    private static void loadItemFramesJSON() {
        Path jsonPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), CUSTOM_CONFIG_PATH, VERSION_PATH, "itemframes.json");
        File jsonFile = new File(jsonPath.toString());

        if (!jsonFile.exists()) {
            // Create default file if JSON file doesn't already exist
            try {
                JSON.createJsonFileFromObject(jsonPath, ItemFrameChances.get());
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Unable to create itemframes.json file: {}", e.toString());
            }
        } else {
            // If file already exists, load data into ArmorStandChances singleton instance
            if (!jsonFile.canRead()) {
                BetterDesertTemplesCommon.LOGGER.error("Better Desert Temples itemframes.json file not readable! Using default configuration...");
                return;
            }

            try {
                ItemFrameChances.instance = JSON.loadObjectFromJsonFile(jsonPath, ItemFrameChances.class);
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Error loading Better Desert Temples itemframes.json file: {}", e.toString());
                BetterDesertTemplesCommon.LOGGER.error("Using default configuration...");
            }
        }
    }

    private static void bakeConfig(BDTConfigFabric configFabric) {
        BetterDesertTemplesCommon.CONFIG.general.disableVanillaPyramids = configFabric.general.disableVanillaPyramids;
        BetterDesertTemplesCommon.CONFIG.general.applyMiningFatigue = configFabric.general.applyMiningFatigue;
    }
}
