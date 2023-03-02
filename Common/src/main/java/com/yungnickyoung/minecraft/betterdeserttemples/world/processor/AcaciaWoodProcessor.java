package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces acacia wood with various sandstone blocks for a ruined effect.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AcaciaWoodProcessor extends StructureProcessor {
    public static final AcaciaWoodProcessor INSTANCE = new AcaciaWoodProcessor();
    public static final Codec<AcaciaWoodProcessor> CODEC = Codec.unit(() -> INSTANCE);

    private static final BlockStateRandomizer SELECTOR = new BlockStateRandomizer(Blocks.SANDSTONE.defaultBlockState())
            .addBlock(Blocks.SANDSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM), 0.1f)
            .addBlock(Blocks.SANDSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 0.1f)
            .addBlock(Blocks.SANDSTONE_STAIRS.defaultBlockState()
                            .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT)
                            .setValue(StairBlock.HALF, Half.TOP)
                            .setValue(StairBlock.FACING, Direction.NORTH),
                    0.025f)
            .addBlock(Blocks.SANDSTONE_STAIRS.defaultBlockState()
                            .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT)
                            .setValue(StairBlock.HALF, Half.TOP)
                            .setValue(StairBlock.FACING, Direction.EAST),
                    0.025f)
            .addBlock(Blocks.SANDSTONE_STAIRS.defaultBlockState()
                            .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT)
                            .setValue(StairBlock.HALF, Half.TOP)
                            .setValue(StairBlock.FACING, Direction.SOUTH),
                    0.025f)
            .addBlock(Blocks.SANDSTONE_STAIRS.defaultBlockState()
                            .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT)
                            .setValue(StairBlock.HALF, Half.TOP)
                            .setValue(StairBlock.FACING, Direction.WEST),
                    0.025f);
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state.getBlock() == Blocks.ACACIA_WOOD) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos);
            BlockState blockState = SELECTOR.get(randomSource);

            if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && levelReader.getFluidState(blockInfoGlobal.pos).is(FluidTags.WATER)) {
                blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, true);
            }

            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, blockState, blockInfoGlobal.nbt);
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.ACACIA_WOOD_PROCESSOR;
    }
}