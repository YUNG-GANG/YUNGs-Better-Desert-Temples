package com.yungnickyoung.minecraft.betterdeserttemples.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawManager;
import com.yungnickyoung.minecraft.yungsapi.module.StructureTypeModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class BetterDesertTempleStructure extends Structure {
    public static final Codec<BetterDesertTempleStructure> CODEC = RecordCodecBuilder.create(builder -> builder
            .group(
                    settingsCodec(builder),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    IntProvider.codec(0, 15).optionalFieldOf("x_offset_in_chunk", ConstantInt.of(0)).forGetter(structure -> structure.xOffsetInChunk),
                    IntProvider.codec(0, 15).optionalFieldOf("z_offset_in_chunk", ConstantInt.of(0)).forGetter(structure -> structure.zOffsetInChunk),
                    Codec.INT.optionalFieldOf("min_y").forGetter(structure -> structure.minY),
                    Codec.INT.optionalFieldOf("max_y").forGetter(structure -> structure.maxY),
                    Codec.INT.optionalFieldOf("min_possible_start_y").forGetter(structure -> structure.minPossibleStartY),
                    Codec.INT.optionalFieldOf("max_possible_start_y").forGetter(structure -> structure.maxPossibleStartY))
            .apply(builder, BetterDesertTempleStructure::new));

    public final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    public final IntProvider xOffsetInChunk;
    public final IntProvider zOffsetInChunk;
    public final Optional<Integer> minY;
    public final Optional<Integer> maxY;
    public final Optional<Integer> minPossibleStartY;
    public final Optional<Integer> maxPossibleStartY;

    public BetterDesertTempleStructure(
            StructureSettings structureSettings,
            Holder<StructureTemplatePool> startPool,
            Optional<ResourceLocation> startJigsawName,
            IntProvider xOffsetInChunk,
            IntProvider zOffsetInChunk,
            Optional<Integer> minY,
            Optional<Integer> maxY,
            Optional<Integer> minPossibleStartY,
            Optional<Integer> maxPossibleStartY
    ) {
        super(structureSettings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.xOffsetInChunk = xOffsetInChunk;
        this.zOffsetInChunk = zOffsetInChunk;
        this.minY = minY;
        this.maxY = maxY;
        this.minPossibleStartY = minPossibleStartY;
        this.maxPossibleStartY = maxPossibleStartY;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        RandomSource randomSource = context.random();

        // Get starting position
        int xOffset = this.xOffsetInChunk.sample(randomSource);
        int zOffset = this.zOffsetInChunk.sample(randomSource);
        int lowestPossibleY = this.minPossibleStartY.orElse(context.heightAccessor().getMinBuildHeight());
        int highestPossibleY = this.maxPossibleStartY.orElse(context.heightAccessor().getMaxBuildHeight());
        int lowestSurfaceYInChunk = getLowestY(context, 15, 15);

        // Clamp starting pos of bottom of pyramid
        int y = Mth.clamp(lowestSurfaceYInChunk, lowestPossibleY, highestPossibleY);

        // Partially bury pyramid underground
        y -= Mth.nextInt(context.random(), 4, 36);
        BlockPos startPos = new BlockPos(chunkPos.getBlockX(xOffset), y, chunkPos.getBlockZ(zOffset));

        return YungJigsawManager.assembleJigsawStructure(
                context,
                this.startPool,
                this.startJigsawName,
                20,
                startPos,
                false,
                Optional.empty(),
                128,
                this.maxY,
                this.minY
        );
    }

    @Override
    public StructureType<?> type() {
        return StructureTypeModule.YUNG_JIGSAW;
    }
}
