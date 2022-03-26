package com.yungnickyoung.minecraft.betterdeserttemples;

import net.fabricmc.api.ModInitializer;

public class BetterDesertTemplesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterDesertTemplesCommon.init();
    }
}
