package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Overrides behavior of /locate desert_pyramid.
 */
@Mixin(LocateCommand.class)
public abstract class LocateVanillaPyramidCommandMixin {
    @Unique
    private static final SimpleCommandExceptionType OLD_PYRAMID_EXCEPTION =
        new SimpleCommandExceptionType(Component.translatable("Use /locate structure betterdeserttemples:desert_temple instead!"));

    @Inject(method = "locateStructure", at = @At(value = "HEAD"))
    private static void betterdeserttemples_overrideLocateVanillaPyramid(CommandSourceStack cmdSource,
                                                     ResourceOrTagKeyArgument.Result<Structure> result,
                                                     CallbackInfoReturnable<Integer> ci) throws CommandSyntaxException {
        Optional<ResourceKey<Structure>> optional = result.unwrap().left();
        if (BetterDesertTemplesCommon.CONFIG.general.disableVanillaPyramids && optional.isPresent() && optional.get().location().equals(ResourceLocation.withDefaultNamespace("desert_pyramid"))) {
            throw OLD_PYRAMID_EXCEPTION.create();
        }
    }
}
