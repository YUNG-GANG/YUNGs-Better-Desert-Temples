package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungsapi.world.structure.processor.ISafeWorldModifier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces purpur pillars with an 8-high tall column of sandstone walls.
 */


public class PurpurPillarProcessor implements StructureProcessor, ISafeWorldModifier {
    public static final PurpurPillarProcessor INSTANCE = new PurpurPillarProcessor();
    public static final MapCodec<PurpurPillarProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.PURPUR_PILLAR) {
            BlockPos.MutableBlockPos mutable = blockInfo.pos().mutable();
            BlockState blockState = Blocks.SANDSTONE_WALL.defaultBlockState()
                    .setValue(WallBlock.EAST, WallSide.NONE)
                    .setValue(WallBlock.WEST, WallSide.NONE)
                    .setValue(WallBlock.NORTH, WallSide.NONE)
                    .setValue(WallBlock.SOUTH, WallSide.NONE)
                    .setValue(WallBlock.UP, true)
                    .setValue(WallBlock.WATERLOGGED, false);
            for (int i = 0; i < 8; i++) {
                setBlockStateSafe(levelReader, mutable, blockState);
                mutable.move(Direction.DOWN);
            }
            blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockState, blockInfo.nbt());
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
