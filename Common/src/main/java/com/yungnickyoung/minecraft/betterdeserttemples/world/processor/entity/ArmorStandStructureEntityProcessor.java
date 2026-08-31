package com.yungnickyoung.minecraft.betterdeserttemples.world.processor.entity;

import com.yungnickyoung.minecraft.betterdeserttemples.world.ArmorStandChances;
import com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Function;

import static com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder.*;

public final class ArmorStandStructureEntityProcessor {
    private ArmorStandStructureEntityProcessor() {}

    private static Function<RandomSource, Item> choose(Item key, Function<RandomSource, Item> armory, Function<RandomSource, Item> wardrobe) {
        return key == Items.IRON_HELMET ? armory : wardrobe;
    }

    public static StructureProcessorType<StructureProcessor> create() {
        var chances = ArmorStandChances.get();
        return build(createArmorStandProcessor(
                i -> choose(i, chances::getArmoryBoots, chances::getWardrobeBoots),
                i -> choose(i, chances::getArmoryLeggings, chances::getWardrobeLeggings),
                i -> choose(i, chances::getArmoryChestplate, chances::getWardrobeChestplate),
                i -> choose(i, chances::getArmoryHelmet, chances::getWardrobeHelmet)));
    }
}
