package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.ConfigModuleForge;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleForge.init();
    }
}
