package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces polished diorite with sandstone or cut sandstone.
 */


public class DioriteProcessor implements StructureProcessor {
    public static final DioriteProcessor INSTANCE = new DioriteProcessor();
    public static final MapCodec<DioriteProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    private static final BlockStateRandomizer SELECTOR = new BlockStateRandomizer(Blocks.SANDSTONE.defaultBlockState())
            .addBlock(Blocks.CUT_SANDSTONE.defaultBlockState(), 0.25f)
            .addBlock(Blocks.SANDSTONE_SLAB.defaultBlockState(), 0.25f)
            .addBlock(Blocks.SAND.defaultBlockState(), 0.25f);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.DIORITE) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfo.pos());
            BlockState blockState = SELECTOR.get(randomSource);

            if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && levelReader.getFluidState(blockInfo.pos()).is(FluidTags.WATER)) {
                blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, true);
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