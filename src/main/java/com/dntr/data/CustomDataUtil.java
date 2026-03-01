package com.dntr.data;

import com.dntr.DntrMod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;

public class CustomDataUtil {

    public static CompoundTag getDntrData(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_DATA)) {
            return new CompoundTag();
        }
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData.contains(DntrMod.MODID)) {
            return customData.getUnsafe().getCompound(DntrMod.MODID);
        }
        return new CompoundTag();
    }

    public static void setDntrData(ItemStack stack, CompoundTag data) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag fullData = customData.copyTag();
        fullData.put(DntrMod.MODID, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(fullData));
    }

    public static void clearDntrData(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_DATA))
            return;
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag fullData = customData.copyTag();
        if (fullData.contains(DntrMod.MODID)) {
            fullData.remove(DntrMod.MODID);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(fullData));
        }
    }
}
