package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.yungsapi.world.banner.Banner;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

/**
 * Replaces lime banners with a random banner.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RedBannerProcessor extends StructureProcessor {
    public static final RedBannerProcessor INSTANCE = new RedBannerProcessor();
    public static final Codec<RedBannerProcessor> CODEC = Codec.unit(() -> INSTANCE);

    public static final Banner ANKH_BANNER_0 = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("bs", 15)
            .pattern("sc", 4)
            .pattern("sku", 4)
            .pattern("mc", 15)
            .pattern("ts", 15)
            .pattern("cbo", 15)
            .build();

    public static final Banner ANKH_BANNER_1 = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("bs", 15)
            .pattern("sc", 4)
            .pattern("sku", 15)
            .pattern("sku", 4)
            .pattern("mc", 15)
            .pattern("bo", 4)
            .pattern("ts", 15)
            .pattern("cbo", 15)
            .build();

    public static final Banner ANKH_BANNER_2 = new Banner.Builder()
            .blockState(Blocks.BLACK_WALL_BANNER.defaultBlockState())
            .pattern("flo", 4)
            .pattern("mr", 15)
            .pattern("sc", 4)
            .pattern("sku", 4)
            .pattern("mc", 15)
            .pattern("bo", 15)
            .pattern("ts", 15)
            .build();

    public static final Banner ANKH_BANNER_3 = new Banner.Builder()
            .blockState(Blocks.BLACK_WALL_BANNER.defaultBlockState())
            .pattern("flo", 4)
            .pattern("sc", 4)
            .pattern("ts", 15)
            .pattern("sku", 4)
            .pattern("mc", 15)
            .pattern("bo", 15)
            .build();

    public static final Banner ANKH_BANNER_4 = new Banner.Builder()
            .blockState(Blocks.BLACK_WALL_BANNER.defaultBlockState())
            .pattern("ts", 15)
            .pattern("tt", 4)
            .pattern("cbo", 15)
            .pattern("sc", 4)
            .pattern("bo", 15)
            .pattern("bo", 15)
            .build();

    public static final Banner CAT_BANNER = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("bri", 15)
            .pattern("hhb", 4)
            .pattern("rd", 4)
            .pattern("mr", 4)
            .pattern("ss", 4)
            .pattern("cbo", 15)
            .pattern("tts", 15)
            .pattern("tl", 15)
            .pattern("bs", 15)
            .pattern("moj", 4)
            .build();

    public static final Banner EMBLEM_BANNER_0 = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("cbo", 15)
            .pattern("ts", 15)
            .pattern("mc", 15)
            .pattern("bt", 15)
            .build();

    public static final Banner EMBLEM_BANNER_0_BLUE = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("bt", 11)
            .pattern("cbo", 11)
            .pattern("ts", 11)
            .pattern("mc", 11)
            .build();

    public static final Banner EMBLEM_BANNER_1 = new Banner.Builder()
            .blockState(Blocks.BLACK_WALL_BANNER.defaultBlockState())
            .pattern("mr", 4)
            .pattern("flo", 15)
            .build();

    public static final Banner PYRAMID_BANNER = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("bri", 0)
            .pattern("bri", 4)
            .pattern("ld", 3)
            .pattern("rud", 3)
            .pattern("tr", 4)
            .build();

    public static final Banner SPHINX_BANNER = new Banner.Builder()
            .blockState(Blocks.YELLOW_WALL_BANNER.defaultBlockState())
            .pattern("hh", 4)
            .pattern("tl", 3)
            .pattern("cbo", 3)
            .pattern("moj", 4)
            .pattern("tts", 3)
            .pattern("hhb", 1)
            .build();

    public static final List<Banner> WALL_BANNERS = Lists.newArrayList(
            ANKH_BANNER_0,
            ANKH_BANNER_1,
            ANKH_BANNER_2,
            ANKH_BANNER_3,
            ANKH_BANNER_4,
            CAT_BANNER,
            EMBLEM_BANNER_0,
            EMBLEM_BANNER_0_BLUE,
            EMBLEM_BANNER_1,
            PYRAMID_BANNER,
            SPHINX_BANNER
    );

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state.getBlock() instanceof AbstractBannerBlock) {
            // Make sure we only operate on the placeholder banners
            if (blockInfoGlobal.state.getBlock() == Blocks.RED_WALL_BANNER && (blockInfoGlobal.nbt.get("Patterns") == null || blockInfoGlobal.nbt.getList("Patterns", 10).size() == 0)) {
                Banner banner = getRandomBanner(structurePlacementData.getRandom(blockInfoGlobal.pos));
                Direction facing = blockInfoGlobal.state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                BlockState newState = banner.getState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                CompoundTag newNBT = copyNBT(banner.getNbt());

                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, newState, newNBT);
            }
        }
        return blockInfoGlobal;
    }

    private Banner getRandomBanner(Random random) {
        return WALL_BANNERS.get(random.nextInt(WALL_BANNERS.size()));
    }

    private CompoundTag copyNBT(CompoundTag other) {
        CompoundTag nbt = new CompoundTag();
        nbt.merge(other);
        return nbt;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.LIME_BANNER_PROCESSOR;
    }
}
