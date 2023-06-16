package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.yungsapi.world.spawner.MobSpawnerData;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces bone blocks with unarmed skeleton spawner.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BoneBlockProcessor extends StructureProcessor {
    public static final BoneBlockProcessor INSTANCE = new BoneBlockProcessor();
    public static final Codec<BoneBlockProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        if (blockInfoGlobal.state().getBlock() == Blocks.BONE_BLOCK) {
            MobSpawnerData spawnerData = MobSpawnerData.builder()
                    .setEntityType(EntityType.SKELETON)
                    .requiredPlayerRange(32)
                    .build();
            spawnerData.nextSpawnData.getEntityToSpawn().put("HandItems", Util.make(new ListTag(), (handItemsTag) -> {
            }));
            CompoundTag nbt = spawnerData.save();
            blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos(), Blocks.SPAWNER.defaultBlockState(), nbt);
        }
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.BONE_BLOCK_PROCESSOR;
    }
}
