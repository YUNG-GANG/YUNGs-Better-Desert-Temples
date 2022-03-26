package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigGeneralForge {
    public final ForgeConfigSpec.ConfigValue<Integer> startMinY;
    public final ForgeConfigSpec.ConfigValue<Integer> startMaxY;
    public final ForgeConfigSpec.ConfigValue<Boolean> disableVanillaPyramids;

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

        disableVanillaPyramids = BUILDER
                .comment(
                        """
                                Whether or not vanilla desert pyramids should be disabled.
                                Default: true""".indent(1))
                .worldRestart()
                .define("Disable Vanilla Pyramids", true);

        BUILDER.pop();
    }
}

