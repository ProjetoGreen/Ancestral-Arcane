package com.ancestralarcane.data;

import com.ancestralarcane.AncestralArcaneMod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;

public class CustomDataUtil {

    public static CompoundTag getAncestralArcaneData(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_DATA)) {
            return new CompoundTag();
        }
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData.contains(AncestralArcaneMod.MODID)) {
            return customData.copyTag().getCompound(AncestralArcaneMod.MODID).copy();
        }
        return new CompoundTag();
    }

    public static void setAncestralArcaneData(ItemStack stack, CompoundTag data) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag fullData = customData.copyTag();
        fullData.put(AncestralArcaneMod.MODID, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(fullData));
    }

    public static void clearAncestralArcaneData(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_DATA))
            return;
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag fullData = customData.copyTag();
        if (fullData.contains(AncestralArcaneMod.MODID)) {
            fullData.remove(AncestralArcaneMod.MODID);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(fullData));
        }
    }
}
