package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BDTConfigForge {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigGeneralForge general;

    static {
        BUILDER.push("YUNG's Better Desert Temples");

        general = new ConfigGeneralForge(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}