package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.*;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        ConfigModuleFabric.init();
        TagModuleFabric.init();
        StructureProcessorModuleFabric.init();
        StructureFeatureModuleFabric.init();
    }
}
