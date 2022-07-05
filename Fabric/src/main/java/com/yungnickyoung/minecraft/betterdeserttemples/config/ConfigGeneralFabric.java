package com.yungnickyoung.minecraft.betterdeserttemples.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigGeneralFabric {
    @ConfigEntry.Gui.Tooltip
    public boolean disableVanillaPyramids = true;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean applyMiningFatigue = true;
}
