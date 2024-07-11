package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Replaces sponges w/ candles of random amount & color.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpongeProcessor extends StructureProcessor {
    public static final SpongeProcessor INSTANCE = new SpongeProcessor();
    public static final MapCodec<SpongeProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    private static final List<Block> CANDLES = List.of(Blocks.CANDLE, Blocks.WHITE_CANDLE, Blocks.GRAY_CANDLE,
            Blocks.LIGHT_GRAY_CANDLE, Blocks.BROWN_CANDLE, Blocks.ORANGE_CANDLE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        Block block = blockInfoGlobal.state().getBlock();
        if (block == Blocks.SPONGE || block == Blocks.WET_SPONGE || block == Blocks.CANDLE) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos());
            // Chance of spawning candle
            if (randomSource.nextFloat() < 0.8f) {
                // Determine number of candles
                int numCandles = 1;
                float r = randomSource.nextFloat();
                if (r < .1f) numCandles = 2;
                else if (r < .15f) numCandles = 3;
                else if (r < .2f) numCandles = 4;

                // Determine lit or not
                boolean lit = randomSource.nextFloat() < .4f;

                BlockState newBlockState = getRandomCandle(randomSource).defaultBlockState()
                        .setValue(CandleBlock.CANDLES, numCandles)
                        .setValue(CandleBlock.LIT, lit);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), newBlockState, blockInfoGlobal.nbt());
            } else {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.AIR.defaultBlockState(), null);
            }
        }
        return blockInfoGlobal;
    }

    private static Block getRandomCandle(RandomSource randomSource) {
        int i = randomSource.nextInt(CANDLES.size());
        return CANDLES.get(i);
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.SPONGE_PROCESSOR;
    }
}
