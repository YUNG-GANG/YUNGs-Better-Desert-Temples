package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigGeneralForge {
    public final ForgeConfigSpec.ConfigValue<Boolean> disableVanillaPyramids;
    public final ForgeConfigSpec.ConfigValue<Boolean> applyMiningFatigue;

    public ConfigGeneralForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
                .comment(
                        """
                                ##########################################################################################################
                                # General settings.
                                ##########################################################################################################""")
                .push("General");

        disableVanillaPyramids = BUILDER
                .comment(
                        """
                                Whether or not vanilla desert pyramids should be disabled.
                                Default: true""".indent(1))
                .worldRestart()
                .define("Disable Vanilla Pyramids", true);

        applyMiningFatigue = BUILDER
                .comment(
                        """
                                Whether or not mining fatigue is applied to players in the desert temple if it has not yet been cleared.
                                Default: true""".indent(1))
                .worldRestart()
                .define("Apply Mining Fatigue", true);

        BUILDER.pop();
    }
}

