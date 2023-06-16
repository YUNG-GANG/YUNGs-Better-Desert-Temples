package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.*;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

@AutoRegister(BetterDesertTemplesCommon.MOD_ID)
public class StructureProcessorModule {
    @AutoRegister("polished_diorite_processor")
    public static StructureProcessorType<PolishedDioriteProcessor> POLISHED_DIORITE_PROCESSOR = () -> PolishedDioriteProcessor.CODEC;

    @AutoRegister("diorite_processor")
    public static StructureProcessorType<DioriteProcessor> DIORITE_PROCESSOR = () -> DioriteProcessor.CODEC;

    @AutoRegister("sponge_processor")
    public static StructureProcessorType<SpongeProcessor> SPONGE_PROCESSOR = () -> SpongeProcessor.CODEC;

    @AutoRegister("end_stone_brick_wall_processor")
    public static StructureProcessorType<EndStoneBrickWallProcessor> END_STONE_BRICK_WALL_PROCESSOR = () -> EndStoneBrickWallProcessor.CODEC;

    @AutoRegister("white_stained_glass_processor")
    public static StructureProcessorType<WhiteStainedGlassProcessor> WHITE_STAINED_GLASS_PROCESSOR = () -> WhiteStainedGlassProcessor.CODEC;

    @AutoRegister("lime_banner_processor")
    public static StructureProcessorType<LimeBannerProcessor> LIME_BANNER_PROCESSOR = () -> LimeBannerProcessor.CODEC;

    @AutoRegister("red_banner_processor")
    public static StructureProcessorType<RedBannerProcessor> RED_BANNER_PROCESSOR = () -> RedBannerProcessor.CODEC;

    @AutoRegister("purpur_pillar_processor")
    public static StructureProcessorType<PurpurPillarProcessor> PURPUR_PILLAR_PROCESSOR = () -> PurpurPillarProcessor.CODEC;

    @AutoRegister("quartz_pillar_processor")
    public static StructureProcessorType<QuartzPillarProcessor> QUARTZ_PILLAR_PROCESSOR = () -> QuartzPillarProcessor.CODEC;

    @AutoRegister("acacia_wood_processor")
    public static StructureProcessorType<AcaciaWoodProcessor> ACACIA_WOOD_PROCESSOR = () -> AcaciaWoodProcessor.CODEC;

    @AutoRegister("infested_cracked_stone_bricks_processor")
    public static StructureProcessorType<InfestedCrackedStoneBricksProcessor> INFESTED_CRACKED_STONE_BRICKS_PROCESSOR = () -> InfestedCrackedStoneBricksProcessor.CODEC;

    @AutoRegister("bone_block_processor")
    public static StructureProcessorType<BoneBlockProcessor> BONE_BLOCK_PROCESSOR = () -> BoneBlockProcessor.CODEC;

    @AutoRegister("yellow_wool_processor")
    public static StructureProcessorType<YellowWoolProcessor> YELLOW_WOOL_PROCESSOR = () -> YellowWoolProcessor.CODEC;

    @AutoRegister("yellow_concrete_processor")
    public static StructureProcessorType<YellowConcreteProcessor> YELLOW_CONCRETE_PROCESSOR = () -> YellowConcreteProcessor.CODEC;

    @AutoRegister("blue_concrete_processor")
    public static StructureProcessorType<BlueConcreteProcessor> BLUE_CONCRETE_PROCESSOR = () -> BlueConcreteProcessor.CODEC;

    @AutoRegister("gravel_processor")
    public static StructureProcessorType<GravelProcessor> GRAVEL_PROCESSOR = () -> GravelProcessor.CODEC;

    @AutoRegister("torch_processor")
    public static StructureProcessorType<TorchProcessor> TORCH_PROCESSOR = () -> TorchProcessor.CODEC;

    @AutoRegister("lit_campfire_processor")
    public static StructureProcessorType<LitCampfireProcessor> LIT_CAMPFIRE_PROCESSOR = () -> LitCampfireProcessor.CODEC;

    @AutoRegister("waterlog_processor")
    public static StructureProcessorType<WaterlogProcessor> WATERLOG_PROCESSOR = () -> WaterlogProcessor.CODEC;

    @AutoRegister("yellow_stained_glass_processor")
    public static StructureProcessorType<YellowStainedGlassProcessor> YELLOW_STAINED_GLASS_PROCESSOR = () -> YellowStainedGlassProcessor.CODEC;

    @AutoRegister("orange_stained_glass_processor")
    public static StructureProcessorType<OrangeStainedGlassProcessor> ORANGE_STAINED_GLASS_PROCESSOR = () -> OrangeStainedGlassProcessor.CODEC;

    @AutoRegister("red_wool_processor")
    public static StructureProcessorType<RedWoolProcessor> RED_WOOL_PROCESSOR = () -> RedWoolProcessor.CODEC;

    @AutoRegister("pot_processor")
    public static StructureProcessorType<PotProcessor> POT_PROCESSOR = () -> PotProcessor.CODEC;

    @AutoRegister("armor_stand_processor")
    public static StructureProcessorType<?> ARMOR_STAND_PROCESSOR = () -> Services.PROCESSORS.armorStandProcessorCodec();

    @AutoRegister("item_frame_processor")
    public static StructureProcessorType<?> ITEM_FRAME_PROCESSOR = () -> Services.PROCESSORS.itemFrameProcessorCodec();
}
