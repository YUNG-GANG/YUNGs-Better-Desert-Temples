package com.yungnickyoung.minecraft.betterdeserttemples.world.structure;

import com.yungnickyoung.minecraft.betterdeserttemples.config.BDTConfig;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawConfig;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

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
            // Get starting position with random y-value
            int minY = BDTConfig.general.startMinY.get();
            int maxY = BDTConfig.general.startMaxY.get();
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
            worldgenRandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);
//            int y = worldgenRandom.nextInt(maxY - minY) + minY;
            int y = 70;
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
