package com.yungnickyoung.minecraft.betterdeserttemples.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="betterdeserttemples-fabric-1_20")
public class BDTConfigFabric implements ConfigData {
    @ConfigEntry.Category("Better Desert Temples")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigGeneralFabric general = new ConfigGeneralFabric();
}
