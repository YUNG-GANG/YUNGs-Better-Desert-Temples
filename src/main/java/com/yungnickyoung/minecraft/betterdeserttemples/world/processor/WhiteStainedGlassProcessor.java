package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.init.BDTModProcessors;
import com.yungnickyoung.minecraft.yungsapi.world.BlockSetSelector;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * Replaces white stained glass with cobwebs or air.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WhiteStainedGlassProcessor extends StructureProcessor {
    public static final WhiteStainedGlassProcessor INSTANCE = new WhiteStainedGlassProcessor();
    public static final Codec<WhiteStainedGlassProcessor> CODEC = Codec.unit(() -> INSTANCE);

    private static final BlockSetSelector SELECTOR = new BlockSetSelector()
            .addBlock(Blocks.COBWEB.defaultBlockState(), 0.4f);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state.getBlock() == Blocks.WHITE_STAINED_GLASS) {
            Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, SELECTOR.get(random), blockInfoGlobal.nbt);
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return BDTModProcessors.WHITE_STAINED_GLASS_PROCESSOR;
    }
}
