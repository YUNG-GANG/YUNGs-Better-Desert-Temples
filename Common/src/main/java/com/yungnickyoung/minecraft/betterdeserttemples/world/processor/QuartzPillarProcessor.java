package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungsapi.world.structure.processor.ISafeWorldModifier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces quartz pillars with an 8-high tall column of sandstone.
 */


public class QuartzPillarProcessor implements StructureProcessor, ISafeWorldModifier {
    public static final QuartzPillarProcessor INSTANCE = new QuartzPillarProcessor();
    public static final MapCodec<QuartzPillarProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.QUARTZ_PILLAR) {
            BlockPos.MutableBlockPos mutable = blockInfo.pos().mutable();
            BlockState blockState = Blocks.SANDSTONE.defaultBlockState();
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
