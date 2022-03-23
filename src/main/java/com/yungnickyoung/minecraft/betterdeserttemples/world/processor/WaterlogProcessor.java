package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.init.BDTModProcessors;
import com.yungnickyoung.minecraft.yungsapi.world.processor.ISafeWorldModifier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WaterlogProcessor extends StructureProcessor implements ISafeWorldModifier {
    public static final WaterlogProcessor INSTANCE = new WaterlogProcessor();
    public static final Codec<WaterlogProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
//        if (blockInfoGlobal.state.hasProperty(BlockStateProperties.WATERLOGGED)) {
//            if (!blockInfoGlobal.state.getValue(BlockStateProperties.WATERLOGGED) && !levelReader.getBlockState(blockInfoGlobal.pos).getFluidState().isEmpty()) {
//                // Block should NOT be waterlogged, but water is there in the world, so we need to remove it
//                setBlockStateSafe(levelReader, blockInfoGlobal.pos, Blocks.AIR.defaultBlockState());
//
//                // Process neighbors
//                BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
//                for (Direction direction : Direction.values()) {
//                    mutable.set(blockInfoGlobal.pos).move(direction);
//
//                    if (mutable.getY() < levelReader.getMinBuildHeight() || mutable.getY() >= levelReader.getMaxBuildHeight()) {
//                        continue;
//                    }
//
//                    if (!getFluidStateSafe(levelReader, mutable).isEmpty()) {
//                        setBlockStateSafe(levelReader, mutable, Blocks.AIR.defaultBlockState());
//                    }
//                }
//            }
//        } else if (blockInfoGlobal.state.isAir() && !levelReader.getBlockState(blockInfoGlobal.pos).getFluidState().isEmpty()) {
//            setBlockStateSafe(levelReader, blockInfoGlobal.pos, Blocks.AIR.defaultBlockState());
//
//            // Process neighbors
//            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
//            for (Direction direction : Direction.values()) {
//                mutable.set(blockInfoGlobal.pos).move(direction);
//
//                if (mutable.getY() < levelReader.getMinBuildHeight() || mutable.getY() >= levelReader.getMaxBuildHeight()) {
//                    continue;
//                }
//
//                if (!getFluidStateSafe(levelReader, mutable).isEmpty()) {
//                    setBlockStateSafe(levelReader, mutable, Blocks.AIR.defaultBlockState());
//                }
//            }
//        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return BDTModProcessors.WATERLOG_PROCESSOR;
    }
}