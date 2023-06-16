package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Randomizes decorated pots' sherds.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PotProcessor extends StructureProcessor {
    public static final PotProcessor INSTANCE = new PotProcessor();
    public static final Codec<PotProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.DECORATED_POT) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos());

            CompoundTag newNBT = blockInfoGlobal.nbt() == null ? new CompoundTag() : blockInfoGlobal.nbt();
            ListTag sherds = new ListTag();
            for (int i = 0; i < 4; i++) {
                sherds.add(StringTag.valueOf(getRandomSherd(randomSource)));
            }
            newNBT.put("sherds", sherds);
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.DECORATED_POT.defaultBlockState(), newNBT);
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.POT_PROCESSOR;
    }

    private String getRandomSherd(RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.0625f) {
            return "minecraft:archer_pottery_sherd";
        } else if (f < 0.125f) {
            return "minecraft:miner_pottery_sherd";
        } else if (f < 0.1875f) {
            return "minecraft:prize_pottery_sherd";
        } else if (f < 0.25f) {
            return "minecraft:skull_pottery_sherd";
        }
        return "minecraft:brick";
    }
}
