package com.yungnickyoung.minecraft.betterdeserttemples.world.state;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds the state for all temples in a single region,
 * with methods for reading to/writing from file.
 */
public class TempleStateRegion {
    private final String regionKey;
    private final File regionFile;
    private final ConcurrentHashMap<Long, Boolean> templeStateMap = new ConcurrentHashMap<>();

    public TempleStateRegion(Path basePath, String regionKey) {
        this.regionKey = regionKey;
        this.regionFile = basePath.resolve(regionKey).toFile();
        createRegionFileIfDoesNotExist();
    }

    public synchronized void setTempleCleared(BlockPos templePos, boolean isCleared) {
        // Update map
        templeStateMap.put(templePos.asLong(), isCleared);

        // Write changes to file
        createRegionFileIfDoesNotExist();
        CompoundTag compoundTag = readRegionFile();
        compoundTag.putBoolean(templePos.toString(), isCleared);
        writeRegionFile(compoundTag);
    }

    public synchronized boolean isTempleCleared(BlockPos templePos) {
        long templePosAsLong = templePos.asLong();

        // Return map entry if already exists.
        if (templeStateMap.containsKey(templePosAsLong)) {
            return templeStateMap.get(templePosAsLong);
        }

        createRegionFileIfDoesNotExist();

        // Query the region file for the entry.
        boolean isCleared = false;
        CompoundTag compoundTag = readRegionFile();

        if (compoundTag == null) {
            // File might be empty. Create new tag & write entry to file.
            compoundTag = new CompoundTag();
            compoundTag.putBoolean(templePos.toString(), isCleared);
            writeRegionFile(compoundTag);
        } else if (compoundTag.contains(templePos.toString())) {
            // Entry exists in file.
            isCleared = compoundTag.getBoolean(templePos.toString());
        } else {
            // Entry does not exist in file. Write new entry to file.
            compoundTag.putBoolean(templePos.toString(), isCleared);
            writeRegionFile(compoundTag);
        }

        // Update our cache map and return the value we sought.
        templeStateMap.put(templePosAsLong, isCleared);
        return isCleared;
    }

    public synchronized void reset() {
        templeStateMap.clear();
    }

    private void writeRegionFile(CompoundTag compoundTag) {
        try {
            NbtIo.write(compoundTag, regionFile);
        } catch (IOException e) {
            BetterDesertTemplesCommon.LOGGER.error("Encountered error writing data to temple region file {}", regionKey);
            BetterDesertTemplesCommon.LOGGER.error(e);
        }
    }

    private CompoundTag readRegionFile() {
        try {
            return NbtIo.read(regionFile);
        } catch (IOException e) {
            BetterDesertTemplesCommon.LOGGER.error("Encountered error reading data from temple region file {}", regionKey);
            BetterDesertTemplesCommon.LOGGER.error(e);
            return new CompoundTag();
        }
    }

    private synchronized void createRegionFileIfDoesNotExist() {
        if (!regionFile.exists()) {
            try {
                regionFile.createNewFile();
                NbtIo.write(new CompoundTag(), regionFile);
            } catch (IOException e) {
                BetterDesertTemplesCommon.LOGGER.error("Unable to create temple region file for region {}", regionKey);
                BetterDesertTemplesCommon.LOGGER.error(e);
            }
        }
    }
}
