package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Replaces red stained-glass with sandstone, sandstone stairs.
 */
public class RedStainedGlassProcessor extends StructureProcessor {
    public static final RedStainedGlassProcessor INSTANCE = new RedStainedGlassProcessor();
    public static final MapCodec<RedStainedGlassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.RED_STAINED_GLASS) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos());
            float f = randomSource.nextFloat();

            if (f < 0.3f) {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.SAND.defaultBlockState(), blockInfoGlobal.nbt());
            } else if (f < 0.75f) {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.SANDSTONE.defaultBlockState(), blockInfoGlobal.nbt());
            } else {
                BlockState state = Blocks.SANDSTONE_STAIRS.defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource))
                        .setValue(StairBlock.HALF, Half.TOP)
                        .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), state, blockInfoGlobal.nbt());
            }
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.RED_STAINED_GLASS_PROCESSOR;
    }
}
