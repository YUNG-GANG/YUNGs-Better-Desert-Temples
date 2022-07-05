package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.StructurePlacementTypeModule;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureTypeModule;

public interface IModulesLoader {
    default void loadModules() {
        StructureProcessorModule.init();
        StructureTypeModule.init();
        StructurePlacementTypeModule.init();
    }
}
