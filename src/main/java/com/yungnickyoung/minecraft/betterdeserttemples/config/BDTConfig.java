package com.yungnickyoung.minecraft.betterdeserttemples.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BDTConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigGeneral general;
    public static final ForgeConfigSpec.ConfigValue<String> whitelistedDimensions;
    public static final ForgeConfigSpec.ConfigValue<String> blacklistedBiomes;
    public static final ForgeConfigSpec.ConfigValue<String> additionalWhitelistedBiomes;

    static {
        BUILDER.push("YUNG's Better Desert Temples");

        whitelistedDimensions = BUILDER
                .comment(
                        """
                                List of dimensions that will have Better Desert Temples.
                                List must be comma-separated values enclosed in square brackets.
                                Entries must have the mod namespace included.
                                For example: "[minecraft:overworld, minecraft:the_nether, undergarden:undergarden]"
                                Default: "[minecraft:overworld]""".indent(1))
                .worldRestart()
                .define("Whitelisted Dimensions", "[minecraft:overworld]");

        blacklistedBiomes = BUILDER
                .comment(
                        """
                                List of biomes that will NOT have Better Desert Temples.
                                There is no need to blacklist any non-desert biomes.
                                List must be comma-separated values enclosed in square brackets.
                                Entries must have the mod namespace included.
                                For example: "[minecraft:plains, byg:alps]"
                                Default: "[]""".indent(1))
                .worldRestart()
                .define("Blacklisted Biomes", "[]");

        additionalWhitelistedBiomes = BUILDER
                .comment(
                        """
                                List of additional biomes that WILL have Better Desert Temples.
                                Better Desert Temples will default to spawning in all biomes with the Desert category.
                                You can add any biomes that aren't categorized as Desert here.
                                List must be comma-separated values enclosed in square brackets.
                                Entries must have the mod namespace included.
                                Default: "[]""".indent(1))
                .worldRestart()
                .define("Additional Whitelisted Biomes", "[]");

        general = new ConfigGeneral(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}