package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.BlockStateRandomizer;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces white stained glass with cobwebs or air.
 */


public class WhiteStainedGlassProcessor implements StructureProcessor {
    public static final WhiteStainedGlassProcessor INSTANCE = new WhiteStainedGlassProcessor();
    public static final MapCodec<WhiteStainedGlassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    private static final BlockStateRandomizer SELECTOR = new BlockStateRandomizer()
            .addBlock(Blocks.COBWEB.defaultBlockState(), 0.4f);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.STAINED_GLASS.pick(DyeColor.WHITE)) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfo.pos());
            blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), SELECTOR.get(randomSource), blockInfo.nbt());
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
