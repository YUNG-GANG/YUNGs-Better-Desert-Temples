package com.yungnickyoung.minecraft.betterdeserttemples.util;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.Vec3;

public class PharaohUtil {
    private static final String PHARAOH_HEAD_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM1MGMwNDk5YTY4YmNkOWM3NWIyNWMxOTIzMTQzOWIxMDhkMDI3NTlmNDM1ZTMzZTRhZWU5ZWQxZGQyNDFhMiJ9fX0=";

    public static boolean isPharaoh(Object object) {
        if (!(object instanceof Husk husk)) {
            return false;
        }

        for (ItemStack armorItem : husk.getArmorSlots()) {
            if (armorItem.is(Items.PLAYER_HEAD)) {
                ResolvableProfile profile = armorItem.get(DataComponents.PROFILE);
                if (profile == null) continue;

                return profile.properties().values().stream()
                        .filter(property -> property.name().equals("textures"))
                        .anyMatch(property -> property.value().equals(PHARAOH_HEAD_TEXTURE));
            }
        }

        return false;
    }

    public static boolean isPharaoh(CompoundTag mobNbt, RegistryAccess registryAccess) {
        if (!mobNbt.getString("id").equals("minecraft:husk")) return false;

        ListTag armorItems = mobNbt.getList("ArmorItems", Tag.TAG_COMPOUND);
        if (armorItems.size() != 4) return false;

        CompoundTag helmetTag = armorItems.getCompound(3);
        ItemStack helmetItemStack = ItemStack.parseOptional(registryAccess, helmetTag);
        if (!helmetItemStack.is(Items.PLAYER_HEAD)) return false;

        ResolvableProfile profile = helmetItemStack.get(DataComponents.PROFILE);
        return profile != null && profile.properties().values().stream()
                .filter(property -> property.name().equals("textures"))
                .anyMatch(property -> property.value().equals(PHARAOH_HEAD_TEXTURE));
    }

    public static void attachSpawnPos(CompoundTag mobNbt, Vec3 pos) {
        ListTag spawnPos = new ListTag();
        spawnPos.add(DoubleTag.valueOf(pos.x()));
        spawnPos.add(DoubleTag.valueOf(pos.y()));
        spawnPos.add(DoubleTag.valueOf(pos.z()));
        mobNbt.put("bdtOriginalSpawnPos", spawnPos);
    }
}
