package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ArmorStandProcessor;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ItemFrameProcessor;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.PharaohProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public class NeoForgeProcessorProvider implements IProcessorProvider {
    @Override
    public MapCodec<StructureProcessor> armorStandProcessorCodec() {
        return ArmorStandProcessor.CODEC;
    }

    @Override
    public MapCodec<StructureProcessor> itemFrameProcessorCodec() {
        return ItemFrameProcessor.CODEC;
    }

    @Override
    public MapCodec<StructureProcessor> pharaohProcessorCodec() {
        return PharaohProcessor.CODEC;
    }
}
