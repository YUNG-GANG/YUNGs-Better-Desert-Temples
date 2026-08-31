package com.yungnickyoung.minecraft.betterdeserttemples.world.processor.entity;

import com.yungnickyoung.minecraft.betterdeserttemples.util.PharaohUtil;
import com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import static com.yungnickyoung.minecraft.yungsapi.api.StructureEntityProcessorBuilder.*;

public final class PharoahStructureEntityProcessor {
    public static StructureProcessorType<StructureProcessor> create() {
        return build(guard(
                onlyIf(PharoahStructureEntityProcessor::isPharoah),
                of(PharoahStructureEntityProcessor::attachSpawnPos)
        ));
    }

    private static boolean isPharoah(final ValueInput vi) {
        if (!is(EntityType.HUSK).test(vi)) {
            return false;
        }

        return vi.read("equipment", EntityEquipment.CODEC)
                // ensure all four armour slots are full
                .filter(eq -> Arrays.stream(EquipmentSlot.values())
                        .filter(s -> s.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
                        .map(eq::get)
                        .noneMatch(ItemStack::isEmpty))
                // and that head is pharoah head
                .map(eq -> eq.get(EquipmentSlot.HEAD))
                .filter(is -> is.is(Items.PLAYER_HEAD))
                .filter(PharaohUtil::isPharoahHead)
                .isPresent();
    }

    private static Consumer<ValueOutput> attachSpawnPos(
            LevelReader levelReader,
            BlockPos blockPos,
            StructureTemplate.StructureEntityInfo localInfo,
            StructureTemplate.StructureEntityInfo globalInfo,
            StructurePlaceSettings structurePlaceSettings) {
        return valueOutput -> {
            valueOutput.store("bdtOriginalSpawnPos", Vec3.CODEC, globalInfo.pos);
        };
    }
}
