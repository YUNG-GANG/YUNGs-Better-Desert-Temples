package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.*;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        ConfigModuleForge.init();
        TagModuleForge.init();
        StructureProcessorModuleForge.init();
        StructureFeatureModuleForge.init();
    }
}
