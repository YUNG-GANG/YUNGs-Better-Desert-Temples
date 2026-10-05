package com.yungnickyoung.minecraft.betterdeserttemples.module;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.services.Services;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.*;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

@AutoRegister(BetterDesertTemplesCommon.MOD_ID)
public class StructureProcessorModule {
    @AutoRegister("polished_diorite_processor")
    public static MapCodec<PolishedDioriteProcessor> POLISHED_DIORITE_PROCESSOR = PolishedDioriteProcessor.CODEC;

    @AutoRegister("diorite_processor")
    public static MapCodec<DioriteProcessor> DIORITE_PROCESSOR = DioriteProcessor.CODEC;

    @AutoRegister("sponge_processor")
    public static MapCodec<SpongeProcessor> SPONGE_PROCESSOR = SpongeProcessor.CODEC;

    @AutoRegister("end_stone_brick_wall_processor")
    public static MapCodec<EndStoneBrickWallProcessor> END_STONE_BRICK_WALL_PROCESSOR = EndStoneBrickWallProcessor.CODEC;

    @AutoRegister("white_stained_glass_processor")
    public static MapCodec<WhiteStainedGlassProcessor> WHITE_STAINED_GLASS_PROCESSOR = WhiteStainedGlassProcessor.CODEC;

    @AutoRegister("lime_banner_processor")
    public static MapCodec<LimeBannerProcessor> LIME_BANNER_PROCESSOR = LimeBannerProcessor.CODEC;

    @AutoRegister("red_banner_processor")
    public static MapCodec<RedBannerProcessor> RED_BANNER_PROCESSOR = RedBannerProcessor.CODEC;

    @AutoRegister("purpur_pillar_processor")
    public static MapCodec<PurpurPillarProcessor> PURPUR_PILLAR_PROCESSOR = PurpurPillarProcessor.CODEC;

    @AutoRegister("quartz_pillar_processor")
    public static MapCodec<QuartzPillarProcessor> QUARTZ_PILLAR_PROCESSOR = QuartzPillarProcessor.CODEC;

    @AutoRegister("acacia_wood_processor")
    public static MapCodec<AcaciaWoodProcessor> ACACIA_WOOD_PROCESSOR = AcaciaWoodProcessor.CODEC;

    @AutoRegister("infested_cracked_stone_bricks_processor")
    public static MapCodec<InfestedCrackedStoneBricksProcessor> INFESTED_CRACKED_STONE_BRICKS_PROCESSOR = InfestedCrackedStoneBricksProcessor.CODEC;

    @AutoRegister("bone_block_processor")
    public static MapCodec<BoneBlockProcessor> BONE_BLOCK_PROCESSOR = BoneBlockProcessor.CODEC;

    @AutoRegister("yellow_wool_processor")
    public static MapCodec<YellowWoolProcessor> YELLOW_WOOL_PROCESSOR = YellowWoolProcessor.CODEC;

    @AutoRegister("yellow_concrete_processor")
    public static MapCodec<YellowConcreteProcessor> YELLOW_CONCRETE_PROCESSOR = YellowConcreteProcessor.CODEC;

    @AutoRegister("blue_concrete_processor")
    public static MapCodec<BlueConcreteProcessor> BLUE_CONCRETE_PROCESSOR = BlueConcreteProcessor.CODEC;

    @AutoRegister("gravel_processor")
    public static MapCodec<GravelProcessor> GRAVEL_PROCESSOR = GravelProcessor.CODEC;

    @AutoRegister("torch_processor")
    public static MapCodec<TorchProcessor> TORCH_PROCESSOR = TorchProcessor.CODEC;

    @AutoRegister("lit_campfire_processor")
    public static MapCodec<LitCampfireProcessor> LIT_CAMPFIRE_PROCESSOR = LitCampfireProcessor.CODEC;

    @AutoRegister("yellow_stained_glass_processor")
    public static MapCodec<YellowStainedGlassProcessor> YELLOW_STAINED_GLASS_PROCESSOR = YellowStainedGlassProcessor.CODEC;

    @AutoRegister("orange_stained_glass_processor")
    public static MapCodec<OrangeStainedGlassProcessor> ORANGE_STAINED_GLASS_PROCESSOR = OrangeStainedGlassProcessor.CODEC;

    @AutoRegister("red_stained_glass_processor")
    public static MapCodec<RedStainedGlassProcessor> RED_STAINED_GLASS_PROCESSOR = RedStainedGlassProcessor.CODEC;

    @AutoRegister("red_wool_processor")
    public static MapCodec<RedWoolProcessor> RED_WOOL_PROCESSOR = RedWoolProcessor.CODEC;

    @AutoRegister("pot_processor")
    public static MapCodec<PotProcessor> POT_PROCESSOR = PotProcessor.CODEC;

    @AutoRegister("armor_stand_processor")
    public static MapCodec<? extends StructureProcessor> ARMOR_STAND_PROCESSOR = Services.PROCESSORS.armorStandProcessorCodec();

    @AutoRegister("item_frame_processor")
    public static MapCodec<? extends StructureProcessor> ITEM_FRAME_PROCESSOR = Services.PROCESSORS.itemFrameProcessorCodec();

    @AutoRegister("pharaoh_processor")
    public static MapCodec<? extends StructureProcessor> PHARAOH_PROCESSOR = Services.PROCESSORS.pharaohProcessorCodec();
}
