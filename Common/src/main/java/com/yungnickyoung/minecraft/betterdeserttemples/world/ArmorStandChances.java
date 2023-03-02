package com.yungnickyoung.minecraft.betterdeserttemples.world;

import com.yungnickyoung.minecraft.yungsapi.api.world.randomize.ItemRandomizer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Singleton class holding ItemRandomizers for armor pieces on armor stands.
 * The class is a singleton so that it may be synchronized with the JSON file as a single source of truth.
 * If no JSON exists, this class will be populated with the default values shown below
 * (and a JSON with the default values created)
 */
public class ArmorStandChances {
    /** Singleton stuff **/

    public static ArmorStandChances instance; // This technically shouldn't be public, but it is necessary for loading data from JSON
    public static ArmorStandChances get() {
        if (instance == null) {
            instance = new ArmorStandChances();
        }
        return instance;
    }

    private ArmorStandChances() {
        // Armory
        armoryHelmets = new ItemRandomizer(Items.AIR)
            .addItem(Items.CHAINMAIL_HELMET, .3f)
            .addItem(Items.GOLDEN_HELMET, .2f);

        armoryChestplates = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_CHESTPLATE, .3f)
                .addItem(Items.GOLDEN_CHESTPLATE, .2f);

        armoryLeggings = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_LEGGINGS, .3f)
                .addItem(Items.GOLDEN_LEGGINGS, .2f);

        armoryBoots = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_BOOTS, .3f)
                .addItem(Items.GOLDEN_BOOTS, .2f);

        // Wardrobe
        wardrobeHelmets = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_HELMET, .2f)
                .addItem(Items.LEATHER_HELMET, .4f);

        wardrobeChestplates = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_CHESTPLATE, .2f)
                .addItem(Items.LEATHER_CHESTPLATE, .4f);

        wardrobeLeggings = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_LEGGINGS, .2f)
                .addItem(Items.LEATHER_LEGGINGS, .4f);

        wardrobeBoots = new ItemRandomizer(Items.AIR)
                .addItem(Items.CHAINMAIL_BOOTS, .2f)
                .addItem(Items.LEATHER_BOOTS, .4f);
    }

    /** Instance variables and methods **/

    // Helmets
    private ItemRandomizer armoryHelmets;
    private ItemRandomizer wardrobeHelmets;

    // Chestplates
    private ItemRandomizer armoryChestplates;
    private ItemRandomizer wardrobeChestplates;

    // Leggings
    private ItemRandomizer armoryLeggings;
    private ItemRandomizer wardrobeLeggings;

    // Boots
    private ItemRandomizer armoryBoots;
    private ItemRandomizer wardrobeBoots;

    public Item getArmoryHelmet(RandomSource randomSource) {
        return armoryHelmets.get(randomSource);
    }

    public Item getWardrobeHelmet(RandomSource randomSource) {
         return wardrobeHelmets.get(randomSource);
    }

    public Item getArmoryChestplate(RandomSource randomSource) {
        return armoryChestplates.get(randomSource);
    }

    public Item getWardrobeChestplate(RandomSource randomSource) {
        return wardrobeChestplates.get(randomSource);
    }

    public Item getArmoryLeggings(RandomSource randomSource) {
        return armoryLeggings.get(randomSource);
    }

    public Item getWardrobeLeggings(RandomSource randomSource) {
        return wardrobeLeggings.get(randomSource);
    }

    public Item getArmoryBoots(RandomSource randomSource) {
        return armoryBoots.get(randomSource);
    }

    public Item getWardrobeBoots(RandomSource randomSource) {
        return wardrobeBoots.get(randomSource);
    }
}
