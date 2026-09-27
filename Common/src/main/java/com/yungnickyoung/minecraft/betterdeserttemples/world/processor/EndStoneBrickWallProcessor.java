package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces end stone brick walls w/ sandstone walls.
 */


public class EndStoneBrickWallProcessor implements StructureProcessor {
    public static final EndStoneBrickWallProcessor INSTANCE = new EndStoneBrickWallProcessor();
    public static final MapCodec<EndStoneBrickWallProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.END_STONE_BRICK_WALL) {
            BlockState blockState = Blocks.SANDSTONE_WALL.defaultBlockState()
                    .setValue(WallBlock.EAST, blockInfo.state().getValue(WallBlock.EAST))
                    .setValue(WallBlock.WEST, blockInfo.state().getValue(WallBlock.WEST))
                    .setValue(WallBlock.NORTH, blockInfo.state().getValue(WallBlock.NORTH))
                    .setValue(WallBlock.SOUTH, blockInfo.state().getValue(WallBlock.SOUTH))
                    .setValue(WallBlock.UP, blockInfo.state().getValue(WallBlock.UP))
                    .setValue(WallBlock.WATERLOGGED, blockInfo.state().getValue(WallBlock.WATERLOGGED));
            blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockState, blockInfo.nbt());
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
