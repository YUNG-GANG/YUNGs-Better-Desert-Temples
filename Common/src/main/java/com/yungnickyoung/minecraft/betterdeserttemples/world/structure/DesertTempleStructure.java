package com.yungnickyoung.minecraft.betterdeserttemples.world.structure;

import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawConfig;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public class DesertTempleStructure extends StructureFeature<YungJigsawConfig> {
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
            int minY = BetterDesertTemplesCommon.CONFIG.general.startMinY;
            int maxY = BetterDesertTemplesCommon.CONFIG.general.startMaxY;
            int lowestSurfaceYInChunk = context.getLowestY(15, 15);
            int y = Mth.clamp(lowestSurfaceYInChunk, minY, maxY); // Clamp starting pos of bottom of pyramid
            y -= Mth.nextInt(worldgenRandom, 4, 36); // Partially bury pyramid underground
            BlockPos startPos = new BlockPos(context.chunkPos().getMiddleBlockX(), y, context.chunkPos().getMiddleBlockZ());

            // Only generate if location is valid
            if (!checkLocation(context, startPos, worldgenRandom)) {
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

    private static boolean checkLocation(PieceGeneratorSupplier.Context<YungJigsawConfig> context, BlockPos startPos, Random random) {
        boolean isVillageNear = context.chunkGenerator()
                .hasFeatureChunkInRange(BuiltinStructureSets.VILLAGES, context.seed(), startPos.getX() >> 4, startPos.getZ() >> 4, 10);
        boolean isOceanOrRiverNear = context.chunkGenerator()
                .getBiomeSource()
                .findBiomeHorizontal(startPos.getX(), startPos.getY(), startPos.getZ(),
                        80, 2,
                        biomeHolder -> biomeHolder.is(BiomeTags.IS_OCEAN) || biomeHolder.is(BiomeTags.IS_OCEAN),
                        random, true,
                        context.chunkGenerator().climateSampler()) != null;
        boolean isBiomeValid = context.validBiome().test(context.chunkGenerator().getNoiseBiome(
                        QuartPos.fromBlock(startPos.getX()),
                        QuartPos.fromBlock(startPos.getY()),
                        QuartPos.fromBlock(startPos.getZ())));
        return !isVillageNear && !isOceanOrRiverNear && isBiomeValid;
    }
}
