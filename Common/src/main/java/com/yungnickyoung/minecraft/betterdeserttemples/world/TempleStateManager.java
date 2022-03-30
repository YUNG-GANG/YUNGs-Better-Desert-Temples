package com.yungnickyoung.minecraft.betterdeserttemples.world;

import net.minecraft.core.BlockPos;

import java.util.concurrent.ConcurrentHashMap;

public class TempleStateManager {
    public static ConcurrentHashMap<Long, Boolean> TEMPLE_STATE_MAP = new ConcurrentHashMap<>();

    public static void setTempleCleared(BlockPos templePos, boolean isCleared) {
        TEMPLE_STATE_MAP.put(templePos.asLong(), isCleared);
    }

    public static boolean isTempleCleared(BlockPos templePos) {
        return TEMPLE_STATE_MAP.computeIfAbsent(templePos.asLong(), pos -> false);
    }
}
