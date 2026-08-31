package com.yungnickyoung.minecraft.betterdeserttemples.world.processor.entity;

import com.yungnickyoung.minecraft.betterdeserttemples.world.ArmorStandChances;
import com.yungnickyoung.minecraft.betterdeserttemples.world.ItemFrameChances;
import com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Function;

import static com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder.*;

public final class ItemFrameStructureEntityProcessor {
    private ItemFrameStructureEntityProcessor() {}

    private static Function<RandomSource, Item> choose(Item original, Function<RandomSource, Item> armory, Function<RandomSource, Item> storage) {
        return original == Items.IRON_SWORD
               ? armory
               : original == Items.BREAD
                 ? storage
                 : _ -> Items.AIR;
    }

    public static StructureProcessorType<StructureProcessor> create() {
        var chances = ItemFrameChances.get();
        return build(createItemFrameProcessor(
                i -> choose(i, chances::getArmouryItem, chances::getStorageItem)));
    }
}
