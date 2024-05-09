package com.yungnickyoung.minecraft.betterdeserttemples;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BetterDesertTemplesCommon.MOD_ID)
public class BetterDesertTemplesNeoForge {
    public static IEventBus loadingContextEventBus;

    public BetterDesertTemplesNeoForge(IEventBus eventBus) {
        BetterDesertTemplesNeoForge.loadingContextEventBus = eventBus;

        BetterDesertTemplesCommon.init();
    }
}
