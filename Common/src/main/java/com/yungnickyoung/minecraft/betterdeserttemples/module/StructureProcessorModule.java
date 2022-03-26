package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class StructureProcessorModule {
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
}
