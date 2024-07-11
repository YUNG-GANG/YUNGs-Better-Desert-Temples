package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Replaces orange stained-glass sand and, rarely, suspicious sand.
 */
public class OrangeStainedGlassProcessor extends StructureProcessor {
    public static final OrangeStainedGlassProcessor INSTANCE = new OrangeStainedGlassProcessor();
    public static final MapCodec<OrangeStainedGlassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.ORANGE_STAINED_GLASS) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfoGlobal.pos());
            float f = randomSource.nextFloat();
            if (f < 0.01f) {
                // place suspicious sand
                CompoundTag nbt = new CompoundTag();
                nbt.putString("LootTable", "minecraft:archaeology/desert_pyramid");
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.SUSPICIOUS_SAND.defaultBlockState(), nbt);
            } else if (f < 0.1f) {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.SAND.defaultBlockState(), blockInfoGlobal.nbt());
            } else {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.CUT_SANDSTONE.defaultBlockState(), blockInfoGlobal.nbt());
            }
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.ORANGE_STAINED_GLASS_PROCESSOR;
    }
}
