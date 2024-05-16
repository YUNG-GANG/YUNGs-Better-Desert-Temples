package com.yungnickyoung.minecraft.betterdeserttemples.services;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public interface IProcessorProvider {
    Codec<StructureProcessor> armorStandProcessorCodec();

    Codec<StructureProcessor> itemFrameProcessorCodec();

    Codec<StructureProcessor> pharaohProcessorCodec();
}
