package com.yungnickyoung.minecraft.betterdeserttemples;

import com.yungnickyoung.minecraft.betterdeserttemples.module.ConfigModule;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterDesertTemplesCommon {
    public static final String MOD_ID = "betterdeserttemples";
    public static final String MOD_NAME = "YUNG's Better Desert Temples";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ConfigModule CONFIG = new ConfigModule();

    /** Global var for placing debug blocks when generating spider dungeons **/
    public static final boolean DEBUG_MODE = false;

    public static void init() {
        YungAutoRegister.scanPackageForAnnotations("com.yungnickyoung.minecraft.betterdeserttemples.module");
        Services.MODULES.loadModules();
    }
}
