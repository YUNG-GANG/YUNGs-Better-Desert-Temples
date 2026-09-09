package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Replaces orange stained-glass with sand and, rarely, suspicious sand.
 */
public class OrangeStainedGlassProcessor implements StructureProcessor {
    public static final OrangeStainedGlassProcessor INSTANCE = new OrangeStainedGlassProcessor();
    public static final MapCodec<OrangeStainedGlassProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.STAINED_GLASS.pick(DyeColor.ORANGE)) {
            RandomSource randomSource = structurePlacementData.getRandom(blockInfo.pos());
            float f = randomSource.nextFloat();
            if (f < 0.01f) {
                // place suspicious sand
                CompoundTag nbt = new CompoundTag();
                nbt.putString("LootTable", "minecraft:archaeology/desert_pyramid");
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SUSPICIOUS_SAND.defaultBlockState(), nbt);
            } else if (f < 0.1f) {
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SAND.defaultBlockState(), blockInfo.nbt());
            } else if (f < 0.2f) {
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SANDSTONE.defaultBlockState(), blockInfo.nbt());
            } else if (f < 0.45f) {
                BlockState state = Blocks.SANDSTONE_STAIRS.defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource))
                        .setValue(StairBlock.HALF, Half.TOP)
                        .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), state, blockInfo.nbt());
            } else {
                blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.CUT_SANDSTONE.defaultBlockState(), blockInfo.nbt());
            }
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
