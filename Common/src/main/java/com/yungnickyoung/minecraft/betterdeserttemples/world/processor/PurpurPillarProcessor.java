package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.yungsapi.world.structure.processor.ISafeWorldModifier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces purpur pillars with an 8-high tall column of sandstone walls.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PurpurPillarProcessor extends StructureProcessor implements ISafeWorldModifier {
    public static final PurpurPillarProcessor INSTANCE = new PurpurPillarProcessor();
    public static final MapCodec<PurpurPillarProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.PURPUR_PILLAR) {
            BlockPos.MutableBlockPos mutable = blockInfoGlobal.pos().mutable();
            BlockState blockState = Blocks.SANDSTONE_WALL.defaultBlockState()
                    .setValue(WallBlock.EAST_WALL, WallSide.NONE)
                    .setValue(WallBlock.WEST_WALL, WallSide.NONE)
                    .setValue(WallBlock.NORTH_WALL, WallSide.NONE)
                    .setValue(WallBlock.SOUTH_WALL, WallSide.NONE)
                    .setValue(WallBlock.UP, true)
                    .setValue(WallBlock.WATERLOGGED, false);
            for (int i = 0; i < 8; i++) {
                setBlockStateSafe(levelReader, mutable, blockState);
                mutable.move(Direction.DOWN);
            }
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), blockState, blockInfoGlobal.nbt());
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.PURPUR_PILLAR_PROCESSOR;
    }
}
