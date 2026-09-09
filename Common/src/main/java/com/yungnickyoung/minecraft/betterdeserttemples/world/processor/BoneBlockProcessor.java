package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungsapi.world.spawner.MobSpawnerData;

import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces bone blocks with unarmed skeleton spawner.
 */


public class BoneBlockProcessor implements StructureProcessor {
    public static final BoneBlockProcessor INSTANCE = new BoneBlockProcessor();
    public static final MapCodec<BoneBlockProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.BONE_BLOCK) {
            MobSpawnerData spawnerData = MobSpawnerData.builder()
                    .setEntityType(BuiltInRegistries.ENTITY_TYPE.get(Identifier.fromNamespaceAndPath("minecraft", "skeleton")).orElseThrow().value())
                    .requiredPlayerRange(32)
                    .build();
            spawnerData.nextSpawnData.getEntityToSpawn().put("HandItems", Util.make(new ListTag(), (handItemsTag) -> {
            }));
            CompoundTag nbt = spawnerData.save();
            blockInfo = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), Blocks.SPAWNER.defaultBlockState(), nbt);
        }
        return blockInfo;
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
