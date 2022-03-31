package com.yungnickyoung.minecraft.betterdeserttemples.world.state;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Primary class for fetching and storing a temple's clear state.
 * Each temple's state is stored according to the region it belongs to.
 */
public class TempleStateCache {
    public ConcurrentHashMap<String, TempleStateRegion> templeStateRegionMap = new ConcurrentHashMap<>();
    private final Path savePath;

    public TempleStateCache(Path dimensionPath) {
        this.savePath = dimensionPath.resolve("betterdeserttemples");
        createDirectoryIfDoesNotExist();
    }

    public boolean isTempleCleared(BlockPos templePos) {
        String templeRegionKey = getRegionKey(templePos);
        TempleStateRegion templeStateRegion = templeStateRegionMap.computeIfAbsent(templeRegionKey, key -> new TempleStateRegion(savePath, key));
        return templeStateRegion.isTempleCleared(templePos);
    }

    public void setTempleCleared(BlockPos templePos, boolean isCleared) {
        String templeRegionKey = getRegionKey(templePos);
        TempleStateRegion templeStateRegion = templeStateRegionMap.computeIfAbsent(templeRegionKey, key -> new TempleStateRegion(savePath, key));
        templeStateRegion.setTempleCleared(templePos, isCleared);
    }

    private String getRegionKey(BlockPos templePos) {
        ChunkPos chunkPos = new ChunkPos(templePos);
        return "r." + chunkPos.getRegionX() + "." + chunkPos.getRegionZ() + ".temples";
    }

    private void createDirectoryIfDoesNotExist() {
        try {
            Files.createDirectories(this.savePath);
        } catch (IOException e) {
            BetterDesertTemplesCommon.LOGGER.error("Unable to create temples save path {}", this.savePath);
            BetterDesertTemplesCommon.LOGGER.error(e);
        }
    }
}
