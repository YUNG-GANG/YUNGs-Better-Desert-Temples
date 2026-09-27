package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Replaces red stained-glass with sandstone, sandstone stairs.
 */
public class RedStainedGlassProcessor implements StructureProcessor {
    public static final RedStainedGlassProcessor INSTANCE = new RedStainedGlassProcessor();
    public static final MapCodec<RedStainedGlassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.STAINED_GLASS.pick(DyeColor.RED)) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfo.pos());
            float f = randomSource.nextFloat();

            if (f < 0.3f) {
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SAND.defaultBlockState(), blockInfo.nbt());
            } else if (f < 0.75f) {
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SANDSTONE.defaultBlockState(), blockInfo.nbt());
            } else {
                BlockState state = Blocks.SANDSTONE_STAIRS.defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource))
                        .setValue(StairBlock.HALF, Half.TOP)
                        .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), state, blockInfo.nbt());
            }
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
