package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.yungnickyoung.minecraft.betterdeserttemples.world.state.ITempleStateCacheProvider;
import com.yungnickyoung.minecraft.betterdeserttemples.world.state.TempleStateCache;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
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
import java.util.function.Supplier;

/**
 * Attaches a {@link TempleStateCache} to each instance of {@link ServerLevel}.
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements ITempleStateCacheProvider {
    @Unique
    private TempleStateCache templeStateCache;

    protected ServerLevelMixin(WritableLevelData $$0, ResourceKey<Level> $$1, RegistryAccess $$2, Holder<DimensionType> $$3, Supplier<ProfilerFiller> $$4, boolean $$5, boolean $$6, long $$7, int $$8) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void betterdeserttemples_attachTempleStateManager(MinecraftServer minecraftServer,
                                                              Executor executor,
                                                              LevelStorageSource.LevelStorageAccess levelStorageAccess,
                                                              ServerLevelData serverLevelData,
                                                              ResourceKey<Level> resourceKey,
                                                              LevelStem levelStem,
                                                              ChunkProgressListener chunkProgressListener,
                                                              boolean bl,
                                                              long l,
                                                              List<CustomSpawner> list,
                                                              boolean bl2,
                                                              RandomSequences $$11,
                                                              CallbackInfo ci) {
        Path dimensionPath = levelStorageAccess.getDimensionPath(this.dimension());
        this.templeStateCache = new TempleStateCache(dimensionPath);
    }

    @Unique
    @Override
    public TempleStateCache getTempleStateCache() {
        return templeStateCache;
    }
}
