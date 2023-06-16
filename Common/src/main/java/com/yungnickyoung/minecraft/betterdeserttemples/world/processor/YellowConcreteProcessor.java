package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces yellow concrete with yellow terracotta.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YellowConcreteProcessor extends StructureProcessor {
    public static final YellowConcreteProcessor INSTANCE = new YellowConcreteProcessor();
    public static final Codec<YellowConcreteProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.YELLOW_CONCRETE) {
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.YELLOW_TERRACOTTA.defaultBlockState(), blockInfoGlobal.nbt());
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.YELLOW_CONCRETE_PROCESSOR;
    }
}
