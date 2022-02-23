package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.init.BDTModProcessors;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces end stone brick walls w/ sandstone walls.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EndStoneBrickWallProcessor extends StructureProcessor {
    public static final EndStoneBrickWallProcessor INSTANCE = new EndStoneBrickWallProcessor();
    public static final Codec<EndStoneBrickWallProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state.getBlock() == Blocks.END_STONE_BRICK_WALL) {
            BlockState blockState = Blocks.SANDSTONE_WALL.defaultBlockState()
                    .setValue(WallBlock.EAST_WALL, blockInfoGlobal.state.getValue(WallBlock.EAST_WALL))
                    .setValue(WallBlock.WEST_WALL, blockInfoGlobal.state.getValue(WallBlock.WEST_WALL))
                    .setValue(WallBlock.NORTH_WALL, blockInfoGlobal.state.getValue(WallBlock.NORTH_WALL))
                    .setValue(WallBlock.SOUTH_WALL, blockInfoGlobal.state.getValue(WallBlock.SOUTH_WALL))
                    .setValue(WallBlock.UP, blockInfoGlobal.state.getValue(WallBlock.UP))
                    .setValue(WallBlock.WATERLOGGED, blockInfoGlobal.state.getValue(WallBlock.WATERLOGGED));
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, blockState, blockInfoGlobal.nbt);
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return BDTModProcessors.END_STONE_BRICK_WALL_PROCESSOR;
    }
}
