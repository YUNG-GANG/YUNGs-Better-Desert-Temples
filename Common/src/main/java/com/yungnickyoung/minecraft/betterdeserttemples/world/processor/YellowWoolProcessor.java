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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Replaces yellow wool with buffed husk spawner.
 */


public class YellowWoolProcessor implements StructureProcessor {
    public static final YellowWoolProcessor INSTANCE = new YellowWoolProcessor();
    public static final MapCodec<YellowWoolProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                              BlockPos jigsawPiecePos,
                                                              BlockPos jigsawPieceBottomCenterPos,
                                                              BlockPos blockPos,
                                                              StructureTemplate.StructureBlockInfo blockInfo,
                                                              StructurePlaceSettings structurePlacementData) {
        if (blockInfo.state().getBlock() == Blocks.WOOL.pick(DyeColor.YELLOW)) {
            MobSpawnerData spawnerData = MobSpawnerData.builder()
                    .setEntityType(BuiltInRegistries.ENTITY_TYPE.get(Identifier.fromNamespaceAndPath("minecraft", "husk")).orElseThrow().value())
                    .maxNearbyEntities(8)
                    .requiredPlayerRange(24)
                    .build();
            spawnerData.nextSpawnData.getEntityToSpawn().put("HandItems", Util.make(new ListTag(), (handItemsTag) -> {
                handItemsTag.add(Util.make(new CompoundTag(), (handItem1Tag) -> {
                    handItem1Tag.putString("id", "minecraft:golden_sword");
                    handItem1Tag.putByte("Count", (byte) 1);
                }));
                handItemsTag.add(Util.make(new CompoundTag(), (handItem2Tag) -> {
                    handItem2Tag.putString("id", "minecraft:shield");
                    handItem2Tag.putByte("Count", (byte) 1);
                    handItem2Tag.put("tag", Util.make(new CompoundTag(), (tagNbt) -> {
                        tagNbt.put("BlockEntityTag", Util.make(new CompoundTag(), (blockEntityTag) -> {
                            blockEntityTag.putInt("Base", 11);
                            blockEntityTag.put("Patterns", Util.make(new ListTag(), (patternsTag) -> {
                                patternsTag.add(Util.make(new CompoundTag(), (patternTag) -> {
                                    patternTag.putInt("Color", 4);
                                    patternTag.putString("Pattern", "ss");
                                }));
                                patternsTag.add(Util.make(new CompoundTag(), (patternTag) -> {
                                    patternTag.putInt("Color", 4);
                                    patternTag.putString("Pattern", "ms");
                                }));
                                patternsTag.add(Util.make(new CompoundTag(), (patternTag) -> {
                                    patternTag.putInt("Color", 4);
                                    patternTag.putString("Pattern", "mc");
                                }));
                                patternsTag.add(Util.make(new CompoundTag(), (patternTag) -> {
                                    patternTag.putInt("Color", 15);
                                    patternTag.putString("Pattern", "hhb");
                                }));
                            }));
                        }));
                    }));
                }));
                handItemsTag.add(new CompoundTag());
            }));
            spawnerData.nextSpawnData.getEntityToSpawn().put("ArmorItems", Util.make(new ListTag(), (armorItemsTag) -> {
                armorItemsTag.add(Util.make(new CompoundTag(), (armorItem1Tag) -> {
                    armorItem1Tag.putString("id", "minecraft:leather_boots");
                    armorItem1Tag.putByte("Count", (byte) 1);
                    armorItem1Tag.put("tag", Util.make(new CompoundTag(), (tagNbt) -> {
                        tagNbt.put("display", Util.make(new CompoundTag(), (displayTag) -> {
                            displayTag.putInt("color", 0);
                        }));
                    }));
                }));
                armorItemsTag.add(Util.make(new CompoundTag(), (armorItem2Tag) -> {
                    armorItem2Tag.putString("id", "minecraft:leather_leggings");
                    armorItem2Tag.putByte("Count", (byte) 1);
                    armorItem2Tag.put("tag", Util.make(new CompoundTag(), (tagNbt) -> {
                        tagNbt.put("display", Util.make(new CompoundTag(), (displayTag) -> {
                            displayTag.putInt("color", 1114367);
                        }));
                    }));
                }));
                armorItemsTag.add(Util.make(new CompoundTag(), (armorItem3Tag) -> {
                    armorItem3Tag.putString("id", "minecraft:golden_chestplate");
                    armorItem3Tag.putByte("Count", (byte) 1);
                }));
                armorItemsTag.add(Util.make(new CompoundTag(), (armorItem4Tag) -> {
                    armorItem4Tag.putString("id", "minecraft:chainmail_helmet");
                    armorItem4Tag.putByte("Count", (byte) 1);
                }));
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
