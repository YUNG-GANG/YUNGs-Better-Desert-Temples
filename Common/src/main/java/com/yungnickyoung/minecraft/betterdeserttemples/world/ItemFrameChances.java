package com.yungnickyoung.minecraft.betterdeserttemples.world;

import com.yungnickyoung.minecraft.yungsapi.world.ItemRandomizer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Singleton class holding ItemRandomizers for items in item frames.
 * The class is a singleton so that it may be synchronized with the JSON file as a single source of truth.
 * If no JSON exists, this class will be populated with the default values shown below
 * (and a JSON with the default values created)
 */
public class ItemFrameChances {
    /** Singleton stuff **/

    public static ItemFrameChances instance; // This technically shouldn't be public, but it is necessary for loading data from JSON
    public static ItemFrameChances get() {
        if (instance == null) {
            instance = new ItemFrameChances();
        }
        return instance;
    }

    private ItemFrameChances() {
        armouryItems = new ItemRandomizer(Items.AIR)
            .addItem(Items.STONE_SWORD, .05f)
            .addItem(Items.GOLDEN_SWORD, .1f)
            .addItem(Items.STONE_AXE, .05f)
            .addItem(Items.GOLDEN_AXE, .1f)
            .addItem(Items.SHIELD, .1f)
            .addItem(Items.BOW, .1f)
            .addItem(Items.ARROW, .05f)
            .addItem(Items.NAME_TAG, .05f);

        storageItems = new ItemRandomizer(Items.AIR)
            .addItem(Items.BREAD, .2f)
            .addItem(Items.POTATO, .2f)
            .addItem(Items.HONEY_BOTTLE, .1f)
            .addItem(Items.COOKIE, .1f)
            .addItem(Items.CAKE, .1f)
            .addItem(Items.SLIME_BALL, .05f)
            .addItem(Items.BEETROOT_SEEDS, .025f)
            .addItem(Items.WHEAT_SEEDS, .025f)
            .addItem(Items.MELON_SEEDS, .025f)
            .addItem(Items.PUMPKIN_SEEDS, .025f)
            .addItem(Items.RABBIT_FOOT, .01f);
    }

    /**
     * Instance variables and methods
     **/

    private ItemRandomizer armouryItems;
    private ItemRandomizer storageItems;

    public Item getArmouryItem(RandomSource randomSource) {
        return armouryItems.get(randomSource);
    }

    public Item getStorageItem(RandomSource randomSource) {
        return storageItems.get(randomSource);
    }
}