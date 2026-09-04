package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.yungsapi.world.banner.Banner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

/**
 * Replaces lime banners with a random banner.
 */


public class LimeBannerProcessor implements StructureProcessor {
    public static final LimeBannerProcessor INSTANCE = new LimeBannerProcessor();
    public static final MapCodec<LimeBannerProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    public static final Banner ANKH_BANNER_0 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.yellow().defaultBlockState())
            .pattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.BLACK)
            .pattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
            .pattern(BannerPatterns.SKULL, DyeColor.YELLOW)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLACK)
            .build();

    public static final Banner ANKH_BANNER_1 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.yellow().defaultBlockState())
            .pattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.BLACK)
            .pattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
            .pattern(BannerPatterns.SKULL, DyeColor.BLACK)
            .pattern(BannerPatterns.SKULL, DyeColor.YELLOW)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.BORDER, DyeColor.YELLOW)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLACK)
            .build();

    public static final Banner ANKH_BANNER_2 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.black().defaultBlockState())
            .pattern(BannerPatterns.FLOWER, DyeColor.YELLOW)
            .pattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
            .pattern(BannerPatterns.SKULL, DyeColor.YELLOW)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.BORDER, DyeColor.BLACK)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .build();

    public static final Banner ANKH_BANNER_3 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.black().defaultBlockState())
            .pattern(BannerPatterns.FLOWER, DyeColor.YELLOW)
            .pattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.SKULL, DyeColor.YELLOW)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.BORDER, DyeColor.BLACK)
            .build();

    public static final Banner ANKH_BANNER_4 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.black().defaultBlockState())
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.TRIANGLE_TOP, DyeColor.YELLOW)
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLACK)
            .pattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
            .pattern(BannerPatterns.BORDER, DyeColor.BLACK)
            .pattern(BannerPatterns.BORDER, DyeColor.BLACK)
            .build();

    public static final Banner CAT_BANNER = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.yellow().defaultBlockState())
            .pattern(BannerPatterns.BRICKS, DyeColor.BLACK)
            .pattern(BannerPatterns.HALF_HORIZONTAL_MIRROR, DyeColor.YELLOW)
            .pattern(BannerPatterns.DIAGONAL_RIGHT, DyeColor.YELLOW)
            .pattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.YELLOW)
            .pattern(BannerPatterns.STRIPE_SMALL, DyeColor.YELLOW)
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLACK)
            .pattern(BannerPatterns.TRIANGLES_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.SQUARE_TOP_LEFT, DyeColor.BLACK)
            .pattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.BLACK)
            .pattern(BannerPatterns.MOJANG, DyeColor.YELLOW)
            .build();

    public static final Banner EMBLEM_BANNER_0 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.yellow().defaultBlockState())
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLACK)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLACK)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLACK)
            .pattern(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLACK)
            .build();

    public static final Banner EMBLEM_BANNER_0_BLUE = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.yellow().defaultBlockState())
            .pattern(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLUE)
            .pattern(BannerPatterns.CURLY_BORDER, DyeColor.BLUE)
            .pattern(BannerPatterns.STRIPE_TOP, DyeColor.BLUE)
            .pattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.BLUE)
            .build();

    public static final Banner EMBLEM_BANNER_1 = new Banner.Builder()
            .blockState(Blocks.WALL_BANNER.black().defaultBlockState())
            .pattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.YELLOW)
            .pattern(BannerPatterns.FLOWER, DyeColor.BLACK)
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
            EMBLEM_BANNER_1
    );

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             BlockPos templateRelativePos,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() instanceof AbstractBannerBlock) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos());

            // Make sure we only operate on the placeholder banners
            var globalNbt = Objects.requireNonNullElseGet(blockInfoGlobal.nbt(), CompoundTag::new);
            if (blockInfoGlobal.state().getBlock() == Blocks.WALL_BANNER.lime() &&
                globalNbt.getList("patterns").filter(l -> !l.isEmpty()).isEmpty()) {
                if (randomSource.nextFloat() > 0.1f) {
                    return new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.AIR.defaultBlockState(), null);
                }

                Banner banner = getRandomBanner(randomSource);
                Direction facing = blockInfoGlobal.state().getValue(BlockStateProperties.HORIZONTAL_FACING);
                BlockState newState = banner.getState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                CompoundTag newNBT = copyNBT(banner.getNbt());

                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), newState, newNBT);
            }
        }
        return blockInfoGlobal;
    }

    private Banner getRandomBanner(RandomSource randomSource) {
        return WALL_BANNERS.get(randomSource.nextInt(WALL_BANNERS.size()));
    }

    private CompoundTag copyNBT(CompoundTag other) {
        CompoundTag nbt = new CompoundTag();
        nbt.merge(other);
        return nbt;
    }

    public MapCodec<? extends StructureProcessor> codec() {
        return StructureProcessorModule.LIME_BANNER_PROCESSOR;
    }
}
