package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ArmorStandChances;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * Gives armor stands random armor depending on the type of armor
 * they are already wearing.
 */
@ParametersAreNonnullByDefault
public class ArmorStandProcessor extends StructureProcessor {
    public static final ArmorStandProcessor INSTANCE = new ArmorStandProcessor();
    public static final Codec<ArmorStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader levelReader,
                                                               BlockPos structurePiecePos,
                                                               StructureTemplate.StructureEntityInfo localEntityInfo,
                                                               StructureTemplate.StructureEntityInfo globalEntityInfo,
                                                               StructurePlaceSettings structurePlaceSettings,
                                                               StructureTemplate template) {
        if (globalEntityInfo.nbt.getString("id").equals("minecraft:armor_stand")) {
            ListTag armorItems = globalEntityInfo.nbt.getList("ArmorItems", 10);
            Random random = structurePlaceSettings.getRandom(globalEntityInfo.blockPos);

            // Type depends on the helmet and nothing else
            String helmet;
            try {
                helmet = ((CompoundTag) armorItems.get(3)).get("id").toString();
            } catch(Exception e) {
                BetterDesertTemplesCommon.LOGGER.info("Unable to randomize armor stand at {}. Missing helmet?", globalEntityInfo.blockPos);
                return globalEntityInfo;
            }

            CompoundTag newNBT = globalEntityInfo.nbt.copy();

            if (helmet.equals("\"minecraft:iron_helmet\"")) {
                // Armor stand has iron helmet -> should use the armory pool
                ListTag armorItemsList = newNBT.getList("ArmorItems", 10);

                // Boots
                String bootsString = Registry.ITEM.getKey(ArmorStandChances.get().getArmoryBoots(random)).toString();
                armorItemsList.getCompound(0).putString("id", bootsString);
                armorItemsList.getCompound(0).putByte("Count", (byte) 1);
                armorItemsList.getCompound(0).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Leggings
                String leggingsString = Registry.ITEM.getKey(ArmorStandChances.get().getArmoryLeggings(random)).toString();
                armorItemsList.getCompound(1).putString("id", leggingsString);
                armorItemsList.getCompound(1).putByte("Count", (byte) 1);
                armorItemsList.getCompound(1).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Chestplate
                String chestplateString = Registry.ITEM.getKey(ArmorStandChances.get().getArmoryChestplate(random)).toString();
                armorItemsList.getCompound(2).putString("id", chestplateString);
                armorItemsList.getCompound(2).putByte("Count", (byte) 1);
                armorItemsList.getCompound(2).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Helmet
                String helmetString = Registry.ITEM.getKey(ArmorStandChances.get().getArmoryHelmet(random)).toString();
                armorItemsList.getCompound(3).putString("id", helmetString);
                armorItemsList.getCompound(3).putByte("Count", (byte) 1);
                armorItemsList.getCompound(3).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

            } else if (helmet.equals("\"minecraft:leather_helmet\"")) {
                // Armor stand has leather helmet -> should use the wardrobe pool
                ListTag armorItemsList = newNBT.getList("ArmorItems", 10);

                // Boots
                String bootsString = Registry.ITEM.getKey(ArmorStandChances.get().getWardrobeBoots(random)).toString();
                armorItemsList.getCompound(0).putString("id", bootsString);
                armorItemsList.getCompound(0).putByte("Count", (byte) 1);
                armorItemsList.getCompound(0).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Leggings
                String leggingsString = Registry.ITEM.getKey(ArmorStandChances.get().getWardrobeLeggings(random)).toString();
                armorItemsList.getCompound(1).putString("id", leggingsString);
                armorItemsList.getCompound(1).putByte("Count", (byte) 1);
                armorItemsList.getCompound(1).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Chestplate
                String chestplateString = Registry.ITEM.getKey(ArmorStandChances.get().getWardrobeChestplate(random)).toString();
                armorItemsList.getCompound(2).putString("id", chestplateString);
                armorItemsList.getCompound(2).putByte("Count", (byte) 1);
                armorItemsList.getCompound(2).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

                // Helmet
                String helmetString = Registry.ITEM.getKey(ArmorStandChances.get().getWardrobeHelmet(random)).toString();
                armorItemsList.getCompound(3).putString("id", helmetString);
                armorItemsList.getCompound(3).putByte("Count", (byte) 1);
                armorItemsList.getCompound(3).put("tag", Util.make(new CompoundTag(), compoundTag -> compoundTag.putInt("Damage", 0)));

            } else {
                // Missing helmet... This should never happen!
                BetterDesertTemplesCommon.LOGGER.info("Armor stand at {} has invalid helmet. Found: {}", globalEntityInfo.blockPos, helmet);
                return globalEntityInfo;
            }
            globalEntityInfo = new StructureTemplate.StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNBT);
        }
        return globalEntityInfo;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader,
                                                             BlockPos jigsawPiecePos,
                                                             BlockPos jigsawPieceBottomCenterPos,
                                                             StructureTemplate.StructureBlockInfo blockInfoLocal,
                                                             StructureTemplate.StructureBlockInfo blockInfoGlobal,
                                                             StructurePlaceSettings structurePlacementData) {
        return blockInfoGlobal;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorModule.ARMOR_STAND_PROCESSOR;
    }
}