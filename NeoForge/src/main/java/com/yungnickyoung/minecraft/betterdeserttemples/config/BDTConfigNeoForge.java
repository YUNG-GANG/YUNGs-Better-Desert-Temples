package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BDTConfigNeoForge {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ConfigGeneralNeoForge general;

    static {
        BUILDER.push("YUNG's Better Desert Temples");

        general = new ConfigGeneralNeoForge(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}