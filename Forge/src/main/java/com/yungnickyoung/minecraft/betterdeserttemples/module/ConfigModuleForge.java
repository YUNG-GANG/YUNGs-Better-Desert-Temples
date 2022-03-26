package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfigForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ConfigModuleForge {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BDTConfigForge.SPEC, "betterdeserttemples-forge-1_18.toml");
        MinecraftForge.EVENT_BUS.addListener(ConfigModuleForge::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigModuleForge::onConfigChange);
    }

    private static void onWorldLoad(WorldEvent.Load event) {
        bakeConfig();
    }

    private static void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getSpec() == BDTConfigForge.SPEC) {
            bakeConfig();
        }
    }

    private static void bakeConfig() {
        BetterDesertTemplesCommon.CONFIG.general.startMinY = BDTConfigForge.general.startMinY.get();
        BetterDesertTemplesCommon.CONFIG.general.startMaxY = BDTConfigForge.general.startMaxY.get();
        BetterDesertTemplesCommon.CONFIG.general.disableVanillaPyramids = BDTConfigForge.general.disableVanillaPyramids.get();
    }
}
