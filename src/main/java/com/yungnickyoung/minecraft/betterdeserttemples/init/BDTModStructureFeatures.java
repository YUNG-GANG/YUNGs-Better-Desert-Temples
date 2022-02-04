package com.yungnickyoung.minecraft.betterdeserttemples.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemples;
import com.yungnickyoung.minecraft.betterdeserttemples.mixin.accessor.ChunkGeneratorAccessor;
import com.yungnickyoung.minecraft.betterdeserttemples.mixin.accessor.StructureSettingsAccessor;
import com.yungnickyoung.minecraft.betterdeserttemples.world.structure.DesertTempleStructure;
import com.yungnickyoung.minecraft.yungsapi.api.YungJigsawConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BDTModStructureFeatures {
    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BetterDesertTemples.MOD_ID);
    public static final RegistryObject<StructureFeature<YungJigsawConfig>> DESERT_TEMPLE = register("desert_temple", DesertTempleStructure::new);
    public static StructureFeatureConfiguration DESERT_TEMPLE_CONFIG = new StructureFeatureConfiguration(20, 10, 92746251);

    public static void init() {
        // Register our deferred registry
        DEFERRED_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register event listeners
        FMLJavaModLoadingContext.get().getModEventBus().addListener(BDTModStructureFeatures::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(BDTModStructureFeatures::setupStructureSpawns);
        MinecraftForge.EVENT_BUS.addListener(BDTModStructureFeatures::addStructuresToBiomesAndDimensions);
    }

    /**
     * Registers the structures.
     */
    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Add structures
            addStructure(DESERT_TEMPLE.get(), DESERT_TEMPLE_CONFIG);

            // Register configured structure features
            Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
            Registry.register(registry, new ResourceLocation(BetterDesertTemples.MOD_ID, "desert_temple"), BDModConfiguredStructures.CONFIGURED_DESERT_TEMPLE);
        });
    }

    /**
     * Adds the provided structure to StructureFeature's registry map, and adds separation settings.
     */
    private static void addStructure(StructureFeature<?> structureFeature, StructureFeatureConfiguration structureFeatureConfig) {
        // Add the structure to the structures map
        StructureFeature.STRUCTURES_REGISTRY.put(structureFeature.getRegistryName().toString(), structureFeature);

        // Add our structure and its spacing to the default settings map
        StructureSettingsAccessor.setDEFAULTS(
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structureFeature, structureFeatureConfig)
                        .build());

        // Add our structure and its spacing to the noise generation settings registry.
        // This usually isn't necessary but is good to have for mod compat, i.e. Terraforged.
        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structureFeature, structureFeatureConfig);
                ((StructureSettingsAccessor)settings.getValue().structureSettings()).setStructureConfig(tempMap);
            } else {
                structureMap.put(structureFeature, structureFeatureConfig);
            }
        });
    }

    /**
     * Limits the spawns for each structure to the appropriate mobs.
     */
    public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
        if (event.getStructure() == DESERT_TEMPLE.get()) {
            event.addEntitySpawns(MobCategory.MONSTER, DesertTempleStructure.ENEMIES);
            event.addEntitySpawns(MobCategory.CREATURE, DesertTempleStructure.CREATURES);
        }
    }

    private static void addStructuresToBiomesAndDimensions(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel serverLevel) {
            addStructureToBiomes(serverLevel);
            addStructureToDimensions(serverLevel);
        }
    }

    /**
     * Adds our structures to whitelisted biomes.
     * Currently uses a workaround method, since Forge's BiomeLoadingEvent is broken for structures.
     */
    private static void addStructureToBiomes(ServerLevel serverLevel) {
        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
        StructureSettings worldStructureSettings = chunkGenerator.getSettings();

        // Make a copy of the structure-biome map
        ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
        ((StructureSettingsAccessor) worldStructureSettings).getConfiguredStructures().entrySet().forEach(tempStructureToMultiMap::put);

        // Create multimaps of Configured Structures to biomes
        ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> biomeMap = ImmutableMultimap.builder();

        // Add the structures to the whitelisted biomes
        for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistry(Registry.BIOME_REGISTRY).get().entrySet()) {
            String biomeName = biomeEntry.getKey().location().toString();

            if ((biomeEntry.getValue().getBiomeCategory() == Biome.BiomeCategory.DESERT || BetterDesertTemples.additionalWhitelistedBiomes.contains(biomeName)) && !BetterDesertTemples.blacklistedBiomes.contains(biomeName)) {
                biomeMap.put(BDModConfiguredStructures.CONFIGURED_DESERT_TEMPLE, biomeEntry.getKey());
            }
        }

        // Add our structures and their associated configured structures + containing biomes to the settings
        tempStructureToMultiMap.put(BDTModStructureFeatures.DESERT_TEMPLE.get(), biomeMap.build());

        // Save our updates
        ((StructureSettingsAccessor) worldStructureSettings).setConfiguredStructures(tempStructureToMultiMap.build());

    }

    /**
     * Adds our structures to whitelisted dimensions.
     */
    private static void addStructureToDimensions(ServerLevel serverLevel) {
        // Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
        // Credits to TelepathicGrunt for this.
        ResourceLocation chunkGenResourceLocation = Registry.CHUNK_GENERATOR.getKey(((ChunkGeneratorAccessor) serverLevel.getChunkSource().getGenerator()).invokeCodec());
        if (chunkGenResourceLocation != null && chunkGenResourceLocation.getNamespace().equals("terraforged")) {
            return;
        }

        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
        StructureSettings structureSettings = chunkGenerator.getSettings();

        // Need temp map as some mods use custom chunk generators with immutable maps in themselves.
        Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureSettings.structureConfig());

        // Don't spawn in superflats
        if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
            tempMap.keySet().remove(DESERT_TEMPLE.get());
            return;
        }

        if (BetterDesertTemples.whitelistedDimensions.contains(serverLevel.dimension().location().toString())) {
            tempMap.putIfAbsent(DESERT_TEMPLE.get(), DESERT_TEMPLE_CONFIG);
        } else {
            tempMap.remove(DESERT_TEMPLE.get());
        }

        ((StructureSettingsAccessor) structureSettings).setStructureConfig(tempMap);
    }

    private static <T extends FeatureConfiguration> RegistryObject<StructureFeature<T>> register(String id, Supplier<StructureFeature<T>> structureFeatureSupplier) {
        return DEFERRED_REGISTRY.register(id, structureFeatureSupplier);
    }
}
