package com.yungnickyoung.minecraft.betterdeserttemples.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigGeneralFabric {
    @ConfigEntry.Gui.Tooltip
    public int startMinY = 61;

    @ConfigEntry.Gui.Tooltip
    public int startMaxY = 200;

    @ConfigEntry.Gui.Tooltip
    public boolean disableVanillaPyramids = true;
}
