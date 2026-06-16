package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateCache;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Attaches a {@link TempleStateCache} to each instance of {@link ServerLevel}.
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements ITempleStateCacheProvider {
    @Unique
    private TempleStateCache templeStateCache;

    protected ServerLevelMixin(WritableLevelData $$0, ResourceKey<Level> $$1, RegistryAccess $$2, Holder<DimensionType> $$3, boolean $$4, boolean $$5, long $$6, int $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void betterdeserttemples_attachTempleStateManager(final MinecraftServer server,
                                                              final Executor executor,
                                                              final LevelStorageSource.LevelStorageAccess levelStorage,
                                                              final ServerLevelData levelData,
                                                              final ResourceKey<Level> dimension,
                                                              final LevelStem levelStem,
                                                              final boolean isDebug,
                                                              final long biomeZoomSeed,
                                                              final List<CustomSpawner> customSpawners,
                                                              final boolean tickTime,
                                                              final CallbackInfo ci) {
        Path dimensionPath = levelStorage.getDimensionPath(this.dimension());
        this.templeStateCache = new TempleStateCache(dimensionPath);
    }

    @Unique
    @Override
    public TempleStateCache getTempleStateCache() {
        return templeStateCache;
    }
}
