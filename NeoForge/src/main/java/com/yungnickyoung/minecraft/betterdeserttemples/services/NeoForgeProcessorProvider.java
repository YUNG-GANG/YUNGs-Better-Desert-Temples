package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ArmorStandProcessor;
import com.yungnickyoung.minecraft.betterdeserttemples.world.processor.ItemFrameProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public class NeoForgeProcessorProvider implements IProcessorProvider {
    @Override
    public Codec<StructureProcessor> armorStandProcessorCodec() {
        return ArmorStandProcessor.CODEC;
    }

    @Override
    public Codec<StructureProcessor> itemFrameProcessorCodec() {
        return ItemFrameProcessor.CODEC;
    }
}
