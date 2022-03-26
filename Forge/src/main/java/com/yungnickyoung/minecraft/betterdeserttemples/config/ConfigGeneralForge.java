package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigGeneralForge {
    public final ForgeConfigSpec.ConfigValue<Integer> startMinY;
    public final ForgeConfigSpec.ConfigValue<Integer> startMaxY;

    public ConfigGeneralForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
                .comment(
                        """
                                ##########################################################################################################
                                # General settings.
                                ##########################################################################################################""")
                .push("General");

        startMinY = BUILDER
                .comment(
                        """
                                The minimum y-value at which the desert temple can spawn.
                                Default: 61""".indent(1))
                .worldRestart()
                .define("Min Start Y", 61);

        startMaxY = BUILDER
                .comment(
                        """
                                The maximum y-value at which the desert temple can spawn.
                                Default: 200""".indent(1))
                .worldRestart()
                .define("Max Start Y", 200);

        BUILDER.pop();
    }
}

