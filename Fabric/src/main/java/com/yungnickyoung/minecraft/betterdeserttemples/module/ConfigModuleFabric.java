package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

public class ConfigModuleFabric {
    public static void init() {
        AutoConfig.register(BDTConfigFabric.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(BDTConfigFabric.class).registerSaveListener(ConfigModuleFabric::bakeConfig);
        AutoConfig.getConfigHolder(BDTConfigFabric.class).registerLoadListener(ConfigModuleFabric::bakeConfig);
    }

    private static InteractionResult bakeConfig(ConfigHolder<BDTConfigFabric> configHolder, BDTConfigFabric configFabric) {
        bakeConfig(configFabric);
        return InteractionResult.SUCCESS;
    }

    private static void bakeConfig(BDTConfigFabric configFabric) {
        BetterDesertTemplesCommon.CONFIG.general.startMinY = configFabric.general.startMinY;
        BetterDesertTemplesCommon.CONFIG.general.startMaxY = configFabric.general.startMaxY;
    }
}
