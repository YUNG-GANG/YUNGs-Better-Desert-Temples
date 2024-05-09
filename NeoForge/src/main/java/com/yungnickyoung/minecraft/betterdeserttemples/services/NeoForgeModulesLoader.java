package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.ConfigModuleNeoForge;

public class NeoForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        ConfigModuleNeoForge.init();
    }
}
