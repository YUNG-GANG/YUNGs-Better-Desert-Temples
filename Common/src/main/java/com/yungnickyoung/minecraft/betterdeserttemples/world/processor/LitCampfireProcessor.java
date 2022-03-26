package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Randomly replaces torches with air.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LitCampfireProcessor extends StructureProcessor {
    public static final LitCampfireProcessor INSTANCE = new LitCampfireProcessor();
    public static final Codec<LitCampfireProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state.getBlock() == Blocks.CAMPFIRE && blockInfoGlobal.state.getValue(CampfireBlock.LIT)) {
            if (structurePlacementData.getRandom(blockInfoGlobal.pos).nextFloat() > .25f) {
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, blockInfoGlobal.state.setValue(CampfireBlock.LIT, false), blockInfoGlobal.nbt);
            }
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.LIT_CAMPFIRE_PROCESSOR;
    }
}
