package com.yungnickyoung.minecraft.betterdeserttemples.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class PharaohUtil {
    public static boolean isPharaoh(Object object) {
        if (!(object instanceof Husk husk)) {
            return false;
        }

        for (ItemStack armorItem : husk.getArmorSlots()) {
            if (armorItem.is(Items.PLAYER_HEAD)) {
                if (!armorItem.hasTag()) continue;
                CompoundTag compoundTag = armorItem.getTag();

                if (compoundTag != null && compoundTag.contains("SkullOwner", 10)) {
                    CompoundTag skullOwner = compoundTag.getCompound("SkullOwner");

                    if (skullOwner.contains("Properties", 10)) {
                        CompoundTag properties = skullOwner.getCompound("Properties");

                        if (properties.contains("textures", 9)) {
                            ListTag textures = properties.getList("textures", 10);
                            if (textures.size() == 1) {
                                CompoundTag texture1 = (CompoundTag) textures.get(0);

                                if (texture1.getString("Value").equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM1MGMwNDk5YTY4YmNkOWM3NWIyNWMxOTIzMTQzOWIxMDhkMDI3NTlmNDM1ZTMzZTRhZWU5ZWQxZGQyNDFhMiJ9fX0=")) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean isPharaoh(CompoundTag mobNbt) {
        if (!mobNbt.getString("id").equals("minecraft:husk")) return false;

        ListTag armorItems = mobNbt.getList("ArmorItems", Tag.TAG_COMPOUND);
        if (armorItems.size() != 4) return false;

        CompoundTag helmet = armorItems.getCompound(3);
        String helmetId = helmet.getString("id");
        if (!helmetId.equals("minecraft:player_head")) return false;

        CompoundTag tag = helmet.getCompound("tag");
        if (!tag.contains("SkullOwner", Tag.TAG_COMPOUND)) return false;

        CompoundTag skullOwner = tag.getCompound("SkullOwner");
        if (!skullOwner.contains("Properties", Tag.TAG_COMPOUND)) return false;

        CompoundTag properties = skullOwner.getCompound("Properties");
        if (!properties.contains("textures", Tag.TAG_LIST)) return false;

        ListTag textures = properties.getList("textures", Tag.TAG_COMPOUND);
        if (textures.size() != 1) return false;

        CompoundTag texture1 = textures.getCompound(0);
        return texture1.getString("Value").equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM1MGMwNDk5YTY4YmNkOWM3NWIyNWMxOTIzMTQzOWIxMDhkMDI3NTlmNDM1ZTMzZTRhZWU5ZWQxZGQyNDFhMiJ9fX0=");
    }

    public static void attachSpawnPos(CompoundTag mobNbt, Vec3 pos) {
        ListTag spawnPos = new ListTag();
        spawnPos.add(DoubleTag.valueOf(pos.x()));
        spawnPos.add(DoubleTag.valueOf(pos.y()));
        spawnPos.add(DoubleTag.valueOf(pos.z()));
        mobNbt.put("bdtOriginalSpawnPos", spawnPos);
    }
}
