package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.structure.DesertTempleStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

public class StructureFeatureModuleForge {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(StructureFeature.class, StructureFeatureModuleForge::registerStructures);
    }

    private static void registerStructures(RegistryEvent.Register<StructureFeature<?>> event) {
        IForgeRegistry<StructureFeature<?>> registry = event.getRegistry();
        StructureFeatureModule.DESERT_TEMPLE = register(registry, "desert_temple", new DesertTempleStructure());
    }

    private static <FC extends FeatureConfiguration> StructureFeature<FC> register(IForgeRegistry<StructureFeature<?>> registry, String name, StructureFeature<FC> structureFeature) {
        structureFeature.setRegistryName(new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, name));
        registry.register(structureFeature);
        return structureFeature;
    }
}
