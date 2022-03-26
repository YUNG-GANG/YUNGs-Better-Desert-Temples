package com.yungnickyoung.minecraft.betterdeserttemples.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.yungnickyoung.minecraft.betterdeserttemples.BetterDesertTemplesCommon;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Overrides behavior of /locate desert_pyramid.
 */
@Mixin(LocateCommand.class)
public class LocateVanillaPyramidCommandMixin {
    private static final SimpleCommandExceptionType OLD_PYRAMID_EXCEPTION =
        new SimpleCommandExceptionType(new TextComponent("Use /locate betterdeserttemples:desert_temple instead!"));

    @Inject(method = "locate", at = @At(value = "HEAD"), cancellable = true)
    private static void overrideLocateVanillaPyramid(CommandSourceStack cmdSource,
                                                     ResourceOrTagLocationArgument.Result<ConfiguredStructureFeature<?, ?>> result,
                                                     CallbackInfoReturnable<Integer> ci) throws CommandSyntaxException {
        Optional<ResourceKey<ConfiguredStructureFeature<?, ?>>> optional = result.unwrap().left();
        if (BetterDesertTemplesCommon.CONFIG.general.disableVanillaPyramids && optional.isPresent() && optional.get().location().equals(new ResourceLocation("desert_pyramid"))) {
            throw OLD_PYRAMID_EXCEPTION.create();
        }
    }
}
