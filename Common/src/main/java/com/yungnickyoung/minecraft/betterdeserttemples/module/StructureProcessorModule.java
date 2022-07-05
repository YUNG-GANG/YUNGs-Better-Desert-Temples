package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class StructureProcessorModule {
    public static StructureProcessorType<PolishedDioriteProcessor> POLISHED_DIORITE_PROCESSOR = () -> PolishedDioriteProcessor.CODEC;
    public static StructureProcessorType<DioriteProcessor> DIORITE_PROCESSOR = () -> DioriteProcessor.CODEC;
    public static StructureProcessorType<SpongeProcessor> SPONGE_PROCESSOR = () -> SpongeProcessor.CODEC;
    public static StructureProcessorType<EndStoneBrickWallProcessor> END_STONE_BRICK_WALL_PROCESSOR = () -> EndStoneBrickWallProcessor.CODEC;
    public static StructureProcessorType<WhiteStainedGlassProcessor> WHITE_STAINED_GLASS_PROCESSOR = () -> WhiteStainedGlassProcessor.CODEC;
    public static StructureProcessorType<LimeBannerProcessor> LIME_BANNER_PROCESSOR = () -> LimeBannerProcessor.CODEC;
    public static StructureProcessorType<RedBannerProcessor> RED_BANNER_PROCESSOR = () -> RedBannerProcessor.CODEC;
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
    public static StructureProcessorType<RedWoolProcessor> RED_WOOL_PROCESSOR = () -> RedWoolProcessor.CODEC;
    public static StructureProcessorType<?> ARMOR_STAND_PROCESSOR = () -> Services.PROCESSORS.armorStandProcessorCodec();
    public static StructureProcessorType<?> ITEM_FRAME_PROCESSOR = () -> Services.PROCESSORS.itemFrameProcessorCodec();

    public static void init() {
        register("polished_diorite_processor", POLISHED_DIORITE_PROCESSOR);
        register("diorite_processor", DIORITE_PROCESSOR);
        register("sponge_processor", SPONGE_PROCESSOR);
        register("end_stone_brick_wall_processor", END_STONE_BRICK_WALL_PROCESSOR);
        register("white_stained_glass_processor", WHITE_STAINED_GLASS_PROCESSOR);
        register("lime_banner_processor", LIME_BANNER_PROCESSOR);
        register("red_banner_processor", RED_BANNER_PROCESSOR);
        register("purpur_pillar_processor", PURPUR_PILLAR_PROCESSOR);
        register("quartz_pillar_processor", QUARTZ_PILLAR_PROCESSOR);
        register("acacia_wood_processor", ACACIA_WOOD_PROCESSOR);
        register("infested_cracked_stone_bricks_processor", INFESTED_CRACKED_STONE_BRICKS_PROCESSOR);
        register("bone_block_processor", BONE_BLOCK_PROCESSOR);
        register("yellow_wool_processor", YELLOW_WOOL_PROCESSOR);
        register("yellow_concrete_processor", YELLOW_CONCRETE_PROCESSOR);
        register("blue_concrete_processor", BLUE_CONCRETE_PROCESSOR);
        register("gravel_processor", GRAVEL_PROCESSOR);
        register("torch_processor", TORCH_PROCESSOR);
        register("lit_campfire_processor", LIT_CAMPFIRE_PROCESSOR);
        register("waterlog_processor", WATERLOG_PROCESSOR);
        register("yellow_stained_glass_processor", YELLOW_STAINED_GLASS_PROCESSOR);
        register("red_wool_processor", RED_WOOL_PROCESSOR);
        register("armor_stand_processor", ARMOR_STAND_PROCESSOR);
        register("item_frame_processor", ITEM_FRAME_PROCESSOR);
    }

    private static void register(String name, StructureProcessorType<?> processorType) {
        Services.REGISTRY.registerStructureProcessorType(new ResourceLocation(BetterDesertTemplesCommon.MOD_ID, name), processorType);
    }
}
