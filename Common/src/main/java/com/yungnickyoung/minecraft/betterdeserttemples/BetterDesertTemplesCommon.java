package com.yungnickyoung.minecraft.betterdeserttemples;

import com.yungnickyoung.minecraft.betterdeserttemples.module.ConfigModule;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.world.structure.locate.LocateReplacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterDesertTemplesCommon {
    public static final String MOD_ID = "betterdeserttemples";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ConfigModule CONFIG = new ConfigModule();

    /** Global var for placing debug blocks when generating spider dungeons **/
    public static final boolean DEBUG_MODE = false;

    public static void init() {
        YungAutoRegister.scanPackageForAnnotations("com.yungnickyoung.minecraft.betterdeserttemples.module");
        Services.MODULES.loadModules();
        LocateReplacer.register(BuiltinStructures.DESERT_PYRAMID,
                ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(MOD_ID, "desert_temple")),
                () -> CONFIG.general.disableVanillaPyramids);
    }
}
