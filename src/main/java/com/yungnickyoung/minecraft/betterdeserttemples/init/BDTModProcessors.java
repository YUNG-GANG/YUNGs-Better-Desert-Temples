package com.yungnickyoung.minecraft.betterdeserttemples.init;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemples;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class BDTModProcessors {
    public static StructureProcessorType<PolishedDioriteProcessor> POLISHED_DIORITE_PROCESSOR = () -> PolishedDioriteProcessor.CODEC;
    public static StructureProcessorType<DioriteProcessor> DIORITE_PROCESSOR = () -> DioriteProcessor.CODEC;
    public static StructureProcessorType<SpongeProcessor> SPONGE_PROCESSOR = () -> SpongeProcessor.CODEC;
    public static StructureProcessorType<EndStoneBrickWallProcessor> END_STONE_BRICK_WALL_PROCESSOR = () -> EndStoneBrickWallProcessor.CODEC;
    public static StructureProcessorType<WhiteStainedGlassProcessor> WHITE_STAINED_GLASS_PROCESSOR = () -> WhiteStainedGlassProcessor.CODEC;
    public static StructureProcessorType<LimeBannerProcessor> LIME_BANNER_PROCESSOR = () -> LimeBannerProcessor.CODEC;
    public static StructureProcessorType<PurpurPillarProcessor> PURPUR_PILLAR_PROCESSOR = () -> PurpurPillarProcessor.CODEC;
    public static StructureProcessorType<QuartzPillarProcessor> QUARTZ_PILLAR_PROCESSOR = () -> QuartzPillarProcessor.CODEC;
    public static StructureProcessorType<AcaciaWoodProcessor> ACACIA_WOOD_PROCESSOR = () -> AcaciaWoodProcessor.CODEC;
    public static StructureProcessorType<InfestedCrackedStoneBricksProcessor> INFESTED_CRACKED_STONE_BRICKS_PROCESSOR = () -> InfestedCrackedStoneBricksProcessor.CODEC;
    public static StructureProcessorType<BoneBlockProcessor> BONE_BLOCK_PROCESSOR = () -> BoneBlockProcessor.CODEC;
    public static StructureProcessorType<YellowWoolProcessor> YELLOW_WOOL_PROCESSOR = () -> YellowWoolProcessor.CODEC;
    public static StructureProcessorType<YellowConcreteProcessor> YELLOW_CONCRETE_PROCESSOR = () -> YellowConcreteProcessor.CODEC;
    public static StructureProcessorType<BlueConcreteProcessor> BLUE_CONCRETE_PROCESSOR = () -> BlueConcreteProcessor.CODEC;
    public static StructureProcessorType<GravelProcessor> GRAVEL_PROCESSOR = () -> GravelProcessor.CODEC;
    public static StructureProcessorType<TorchProcessor> TORCH_PROCESSOR = () -> TorchProcessor.CODEC;
    public static StructureProcessorType<LitCampfireProcessor> LIT_CAMPFIRE_PROCESSOR = () -> LitCampfireProcessor.CODEC;
    public static StructureProcessorType<WaterlogProcessor> WATERLOG_PROCESSOR = () -> WaterlogProcessor.CODEC;
    public static StructureProcessorType<YellowStainedGlassProcessor> YELLOW_STAINED_GLASS_PROCESSOR = () -> YellowStainedGlassProcessor.CODEC;

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(BDTModProcessors::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "polished_diorite_processor"), POLISHED_DIORITE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "diorite_processor"), DIORITE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "sponge_processor"), SPONGE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "end_stone_brick_wall_processor"), END_STONE_BRICK_WALL_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "white_stained_glass_processor"), WHITE_STAINED_GLASS_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "lime_banner_processor"), LIME_BANNER_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "purpur_pillar_processor"), PURPUR_PILLAR_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "quartz_pillar_processor"), QUARTZ_PILLAR_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "acacia_wood_processor"), ACACIA_WOOD_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "infested_cracked_stone_bricks_processor"), INFESTED_CRACKED_STONE_BRICKS_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "bone_block_processor"), BONE_BLOCK_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "yellow_wool_processor"), YELLOW_WOOL_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "yellow_concrete_processor"), YELLOW_CONCRETE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "blue_concrete_processor"), BLUE_CONCRETE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "gravel_processor"), GRAVEL_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "torch_processor"), TORCH_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "lit_campfire_processor"), LIT_CAMPFIRE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "waterlog_processor"), WATERLOG_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(BetterDesertTemples.MOD_ID, "yellow_stained_glass_processor"), YELLOW_STAINED_GLASS_PROCESSOR);
        });
    }
}
