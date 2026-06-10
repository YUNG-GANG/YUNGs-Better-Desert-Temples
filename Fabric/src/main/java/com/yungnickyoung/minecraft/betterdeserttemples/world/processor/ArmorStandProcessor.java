package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ArmorStandChances;
import com.yungnickyoung.minecraft.yungsapi.world.processor.StructureEntityProcessor;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * Gives armor stands random armor depending on the type of armor
 * they are already wearing.
 */
public class ArmorStandProcessor extends StructureEntityProcessor {
    public static final ArmorStandProcessor INSTANCE = new ArmorStandProcessor();
    public static final MapCodec<StructureProcessor> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(ServerLevelAccessor serverLevelAccessor,
                                                               BlockPos structurePiecePos,
                                                               BlockPos structurePieceBottomCenterPos,
                                                               StructureTemplate.StructureEntityInfo localEntityInfo,
                                                               StructureTemplate.StructureEntityInfo globalEntityInfo,
                                                               StructurePlaceSettings structurePlaceSettings) {
        if (globalEntityInfo.nbt.getStringOr("id", "").equals("minecraft:armor_stand")) {
            ListTag armorItems = globalEntityInfo.nbt.getListOrEmpty("ArmorItems");
            RandomSource randomSource = structurePlaceSettings.getRandom(globalEntityInfo.blockPos);

            // Type depends on the helmet and nothing else
            String helmet;
            try {
                helmet = armorItems.getCompoundOrEmpty(3).getStringOr("id", "");
            } catch (Exception e) {
                BetterDesertTemplesCommon.LOGGER.info("Unable to randomize armor stand at {}. Missing helmet?", globalEntityInfo.blockPos);
                return globalEntityInfo;
            }

            // Iron helmet indicates we should use the armory pool. Otherwise, use the wardrobe pool.
            boolean isArmory = helmet.equals("minecraft:iron_helmet");

            CompoundTag newNBT = globalEntityInfo.nbt.copy();
            ListTag armorItemsList = newNBT.getListOrEmpty("ArmorItems");

            // Boots
            String bootsString = isArmory
                    ? BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getArmoryBoots(randomSource)).toString()
                    : BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getWardrobeBoots(randomSource)).toString();
            if (!bootsString.equals("minecraft:air")) {
                putItem(serverLevelAccessor.registryAccess(), armorItemsList, 0, bootsString);
            }

            // Leggings
            String leggingsString = isArmory
                    ? BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getArmoryLeggings(randomSource)).toString()
                    : BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getWardrobeLeggings(randomSource)).toString();
            if (!leggingsString.equals("minecraft:air")) {
                putItem(serverLevelAccessor.registryAccess(), armorItemsList, 1, leggingsString);
            }

            // Chestplate
            String chestplateString = isArmory
                    ? BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getArmoryChestplate(randomSource)).toString()
                    : BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getWardrobeChestplate(randomSource)).toString();
            if (!chestplateString.equals("minecraft:air")) {
                putItem(serverLevelAccessor.registryAccess(), armorItemsList, 2, chestplateString);
            }

            // Helmet
            String helmetString = isArmory
                    ? BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getArmoryHelmet(randomSource)).toString()
                    : BuiltInRegistries.ITEM.getKey(ArmorStandChances.get().getWardrobeHelmet(randomSource)).toString();
            if (!helmetString.equals("minecraft:air")) {
                putItem(serverLevelAccessor.registryAccess(), armorItemsList, 3, helmetString);
            }

            globalEntityInfo = new StructureTemplate.StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNBT);
        }
        return globalEntityInfo;
    }

    private static void putItem(final HolderGetter.Provider registries, final ListTag armorItemsList, final int idx, final String itemId) {
        armorItemsList.setTag(
                idx,
                Util.make(
                        new CompoundTag(), t -> ItemStack.CODEC.encode(
                                new ItemStack(registries
                                                      .get(ResourceKey.create(
                                                              Registries.ITEM,
                                                              Identifier.parse(itemId)))
                                                      .orElseThrow()),
                                NbtOps.INSTANCE,
                                t)));
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