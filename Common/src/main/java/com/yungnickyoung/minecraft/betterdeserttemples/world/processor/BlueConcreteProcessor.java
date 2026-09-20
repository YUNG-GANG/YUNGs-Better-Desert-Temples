package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces blue concrete with blue terracotta.
 */


public class BlueConcreteProcessor implements StructureProcessor {
    public static final BlueConcreteProcessor INSTANCE = new BlueConcreteProcessor();
    public static final MapCodec<BlueConcreteProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             BlockPos templateRelativePos,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.CONCRETE.blue()) {
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.DYED_TERRACOTTA.blue().defaultBlockState(), blockInfoGlobal.nbt());
        }
        return blockInfoGlobal;
    }

    public MapCodec<? extends StructureProcessor> codec() {
        return StructureProcessorModule.BLUE_CONCRETE_PROCESSOR;
    }
}
