package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ArmorStandProcessor;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ItemFrameProcessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class StructureProcessorModuleForge {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(StructureProcessorModuleForge::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            register("polished_diorite_processor", StructureProcessorModule.POLISHED_DIORITE_PROCESSOR);
            register("diorite_processor", StructureProcessorModule.DIORITE_PROCESSOR);
            register("sponge_processor", StructureProcessorModule.SPONGE_PROCESSOR);
            register("end_stone_brick_wall_processor", StructureProcessorModule.END_STONE_BRICK_WALL_PROCESSOR);
            register("white_stained_glass_processor", StructureProcessorModule.WHITE_STAINED_GLASS_PROCESSOR);
            register("lime_banner_processor", StructureProcessorModule.LIME_BANNER_PROCESSOR);
            register("red_banner_processor", StructureProcessorModule.RED_BANNER_PROCESSOR);
            register("purpur_pillar_processor", StructureProcessorModule.PURPUR_PILLAR_PROCESSOR);
            register("quartz_pillar_processor", StructureProcessorModule.QUARTZ_PILLAR_PROCESSOR);
            register("acacia_wood_processor", StructureProcessorModule.ACACIA_WOOD_PROCESSOR);
            register("infested_cracked_stone_bricks_processor", StructureProcessorModule.INFESTED_CRACKED_STONE_BRICKS_PROCESSOR);
            register("bone_block_processor", StructureProcessorModule.BONE_BLOCK_PROCESSOR);
            register("yellow_wool_processor", StructureProcessorModule.YELLOW_WOOL_PROCESSOR);
            register("yellow_concrete_processor", StructureProcessorModule.YELLOW_CONCRETE_PROCESSOR);
            register("blue_concrete_processor", StructureProcessorModule.BLUE_CONCRETE_PROCESSOR);
            register("gravel_processor", StructureProcessorModule.GRAVEL_PROCESSOR);
            register("torch_processor", StructureProcessorModule.TORCH_PROCESSOR);
            register("lit_campfire_processor", StructureProcessorModule.LIT_CAMPFIRE_PROCESSOR);
            register("waterlog_processor", StructureProcessorModule.WATERLOG_PROCESSOR);
            register("yellow_stained_glass_processor", StructureProcessorModule.YELLOW_STAINED_GLASS_PROCESSOR);
            register("red_wool_processor", StructureProcessorModule.RED_WOOL_PROCESSOR);
            StructureProcessorModule.ARMOR_STAND_PROCESSOR = register("armor_stand_processor", () -> ArmorStandProcessor.CODEC);
            StructureProcessorModule.ITEM_FRAME_PROCESSOR = register("item_frame_processor", () -> ItemFrameProcessor.CODEC);
        });
    }

    private static <P extends StructureProcessor> StructureProcessorType<P> register(String name, StructureProcessorType<P> processorType) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, name), processorType);
    }
}
