package com.yungnickyoung.minecraft.betterdeserttemples.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.betterdeserttemples.module.StructurePlacementTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

/**
 * Custom structure placement that avoids intersecting with oceans and rivers.
 */
public class BetterDesertTemplePlacement extends RandomSpreadStructurePlacement {
    public static final Codec<BetterDesertTemplePlacement> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(BetterDesertTemplePlacement::locateOffset),
            FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(BetterDesertTemplePlacement::frequencyReductionMethod),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(BetterDesertTemplePlacement::frequency),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(BetterDesertTemplePlacement::salt),
            ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(BetterDesertTemplePlacement::exclusionZone),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("spacing").forGetter(BetterDesertTemplePlacement::spacing),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("separation").forGetter(BetterDesertTemplePlacement::separation),
            RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(BetterDesertTemplePlacement::spreadType)
    ).apply(instance, instance.stable(BetterDesertTemplePlacement::new)));

    public BetterDesertTemplePlacement(Vec3i locateOffset,
                                       FrequencyReductionMethod frequencyReductionMethod,
                                       Float frequency,
                                       Integer salt,
                                       Optional<ExclusionZone> exclusionZone,
                                       Integer spacing,
                                       Integer separation,
                                       RandomSpreadType randomSpreadType) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, randomSpreadType);
    }

    @Override
    protected boolean isPlacementChunk(ChunkGenerator chunkGenerator, RandomState randomState, long seed, int chunkX, int chunkZ) {
        ChunkPos chunkPos = this.getPotentialStructureChunk(seed, chunkX, chunkZ);
        if (chunkPos.x == chunkX && chunkPos.z == chunkZ) {
            BlockPos structurePos = chunkPos.getMiddleBlockPosition(120);
            boolean isOceanOrRiverNear = chunkGenerator
                    .getBiomeSource()
                    .findBiomeHorizontal(structurePos.getX(), structurePos.getY(), structurePos.getZ(),
                            48, 2,
                            biomeHolder -> biomeHolder.is(BiomeTags.IS_RIVER) || biomeHolder.is(BiomeTags.IS_OCEAN),
                            randomState.oreRandom().at(structurePos), true,
                            randomState.sampler()
                    ) != null;
            return !isOceanOrRiverNear;
        }
        return false;
    }

    @Override
    public StructurePlacementType<?> type() {
        return StructurePlacementTypeModule.BETTER_DESERT_TEMPLE_PLACEMENT;
    }
}
