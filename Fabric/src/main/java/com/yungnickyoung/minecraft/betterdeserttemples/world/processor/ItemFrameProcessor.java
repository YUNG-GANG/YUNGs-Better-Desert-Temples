package com.yungnickyoung.minecraft.betterdeserttemples.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructureProcessorModule;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ItemFrameChances;
import com.yungnickyoung.minecraft.yungsapi.world.processor.StructureEntityProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Fills item frames with a random item.
 * The type of random item depends on the item already in the frame.
 */
public class ItemFrameProcessor extends StructureEntityProcessor {
    public static final ItemFrameProcessor INSTANCE = new ItemFrameProcessor();
    public static final Codec<ItemFrameProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(ServerLevelAccessor serverLevelAccessor,
                                                               BlockPos structurePiecePos,
                                                               BlockPos structurePieceBottomCenterPos,
                                                               StructureTemplate.StructureEntityInfo localEntityInfo,
                                                               StructureTemplate.StructureEntityInfo globalEntityInfo,
                                                               StructurePlaceSettings structurePlaceSettings) {
        if (globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame")) {
            Random random = structurePlaceSettings.getRandom(globalEntityInfo.blockPos);

            // Type depends on the item currently in the frame
            String item;
            try {
                item = globalEntityInfo.nbt.getCompound("Item").get("id").toString();
            } catch (Exception e) {
                BetterDesertTemplesCommon.LOGGER.info("Unable to randomize item frame at {}", globalEntityInfo.blockPos);
                return globalEntityInfo;
            }

            if (item.equals("\"minecraft:iron_sword\"")) {
                // Item frame has iron sword -> should use the armory pool
                String randomItemString = Registry.ITEM.getKey(ItemFrameChances.get().getArmouryItem(random)).toString();
                if (!randomItemString.equals("minecraft:air")) {
                    globalEntityInfo.nbt.getCompound("Item").putString("id", randomItemString);
                }
            } else if (item.equals("\"minecraft:bread\"")) {
                // Item frame has bread -> should use the storage pool
                String randomItemString = Registry.ITEM.getKey(ItemFrameChances.get().getStorageItem(random)).toString();
                if (!randomItemString.equals("minecraft:air")) {
                    globalEntityInfo.nbt.getCompound("Item").putString("id", randomItemString);
                }
            } else {
                // No match -- item frame must be for a different purpose
                return globalEntityInfo;
            }

            // Randomize rotation
            int randomRotation = random.nextInt(8);
            globalEntityInfo.nbt.putByte("ItemRotation", (byte) randomRotation);
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
        return StructureProcessorModule.ITEM_FRAME_PROCESSOR;
    }
}