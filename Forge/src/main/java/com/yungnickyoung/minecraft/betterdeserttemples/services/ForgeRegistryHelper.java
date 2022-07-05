package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.yungnickyoung.minecraft.betterdeserttemples.module.StructurePlacementTypeModuleForge;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorTypeModuleForge;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureTypeModuleForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ForgeRegistryHelper implements IRegistryHelper {
    @Override
    public void registerStructureType(ResourceLocation resourceLocation, StructureType<? extends Structure> structureType) {
        StructureTypeModuleForge.STRUCTURE_TYPES.put(resourceLocation, structureType);
    }

    @Override
    public void registerStructureProcessorType(ResourceLocation resourceLocation, StructureProcessorType<? extends StructureProcessor> structureProcessorType) {
        StructureProcessorTypeModuleForge.STRUCTURE_PROCESSOR_TYPES.put(resourceLocation, structureProcessorType);
    }

    @Override
    public void registerStructurePlacementType(ResourceLocation resourceLocation, StructurePlacementType<? extends StructurePlacement> structurePlacementType) {
        StructurePlacementTypeModuleForge.STRUCTURE_PLACEMENT_TYPES.put(resourceLocation, structurePlacementType);
    }
}
