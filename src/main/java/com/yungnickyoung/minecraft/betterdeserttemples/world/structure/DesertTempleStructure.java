package com.yungnickyoung.minecraft.betterdeserttemples.world.structure;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfig;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawConfig;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DesertTempleStructure extends StructureFeature<YungJigsawConfig> {
    public static final List<MobSpawnSettings.SpawnerData> ENEMIES = List.of(
            new MobSpawnSettings.SpawnerData(EntityType.HUSK, 100, 4, 15));

    public static final List<MobSpawnSettings.SpawnerData> CREATURES = List.of(
            new MobSpawnSettings.SpawnerData(EntityType.CAT, 100, 4, 15));

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    public DesertTempleStructure() {
        super(YungJigsawConfig.CODEC, context -> {
            // Random
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
            worldgenRandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);

            // Get starting position with random y-value
            int minY = BDTConfig.general.startMinY.get();
            int maxY = BDTConfig.general.startMaxY.get();
            int lowestSurfaceYInChunk = context.getLowestY(15, 15);
            int y = Mth.clamp(lowestSurfaceYInChunk, minY, maxY); // Clamp starting pos of bottom of pyramid
            y -= Mth.nextInt(worldgenRandom, 4, 24); // Partially bury pyramid underground
            BlockPos startPos = new BlockPos(context.chunkPos().getMiddleBlockX(), y, context.chunkPos().getMiddleBlockZ());

            // Only generate if location is valid
            if (!checkLocation(context, startPos)) {
                return Optional.empty();
            }

            return YungJigsawManager.assembleJigsawStructure(
                    context,
                    PoolElementStructurePiece::new,
                    startPos,
                    false,
                    false,
                    80);
        });
    }

    private static boolean checkLocation(PieceGeneratorSupplier.Context<YungJigsawConfig> context, BlockPos startPos) {
        return context.validBiome().test(context.chunkGenerator().getNoiseBiome(
                QuartPos.fromBlock(startPos.getX()),
                QuartPos.fromBlock(startPos.getY()),
                QuartPos.fromBlock(startPos.getZ()))
        );
    }
}
