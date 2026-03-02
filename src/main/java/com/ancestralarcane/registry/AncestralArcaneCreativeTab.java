package com.ancestralarcane.registry;

import com.ancestralarcane.AncestralArcaneMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AncestralArcaneCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, AncestralArcaneMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DNTR_TAB = CREATIVE_MODE_TABS
            .register("ancestral_arcane_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dntr"))
                    .icon(() -> new ItemStack(AncestralArcaneItems.RUNE.get()))
                    .displayItems((parameters, output) -> {
                        // 1. Empty Rune
                        output.accept(AncestralArcaneItems.RUNE.get());

                        // 2. Crude Rune
                        ItemStack crudeRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                        net.minecraft.nbt.CompoundTag cdData = new net.minecraft.nbt.CompoundTag();
                        net.minecraft.nbt.CompoundTag cdRune = new net.minecraft.nbt.CompoundTag();
                        cdRune.putInt("crude", 1);
                        cdData.put("rune", cdRune);
                        com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(crudeRune, cdData);
                        output.accept(crudeRune);

                        // 3. Generic Inscribed Rune
                        ItemStack inscribedRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                        net.minecraft.nbt.CompoundTag inscData = new net.minecraft.nbt.CompoundTag();
                        net.minecraft.nbt.CompoundTag inscRune = new net.minecraft.nbt.CompoundTag();
                        inscRune.putInt("empty", 0);
                        inscRune.putInt("lvl", 1);
                        inscRune.putString("spell", "flame");
                        inscData.put("rune", inscRune);
                        com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(inscribedRune, inscData);
                        output.accept(inscribedRune);

                        // 4. Upgraded Rune (empty but upgraded)
                        ItemStack upgRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                        net.minecraft.nbt.CompoundTag upgData = new net.minecraft.nbt.CompoundTag();
                        net.minecraft.nbt.CompoundTag uRune = new net.minecraft.nbt.CompoundTag();
                        uRune.putInt("empty", 1);
                        net.minecraft.nbt.CompoundTag uTag = new net.minecraft.nbt.CompoundTag();
                        uTag.putString("type", "blaze");
                        uTag.putInt("level", 1);
                        uRune.put("upgrade", uTag);
                        upgData.put("rune", uRune);
                        com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(upgRune, upgData);
                        output.accept(upgRune);

                        // 5. Upgraded + Inscribed Rune
                        ItemStack uiRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                        net.minecraft.nbt.CompoundTag uiData = new net.minecraft.nbt.CompoundTag();
                        net.minecraft.nbt.CompoundTag uiR = new net.minecraft.nbt.CompoundTag();
                        uiR.putInt("empty", 0);
                        uiR.putInt("lvl", 1);
                        uiR.putString("spell", "flame");
                        net.minecraft.nbt.CompoundTag uiTag = new net.minecraft.nbt.CompoundTag();
                        uiTag.putString("type", "blaze");
                        uiTag.putInt("level", 1);
                        uiR.put("upgrade", uiTag);
                        uiData.put("rune", uiR);
                        com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(uiRune, uiData);
                        output.accept(uiRune);

                        output.accept(AncestralArcaneItems.ARCANE_RESIDUE.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_FLAME.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_CHANNELING.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_MENDING.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_RESPIRATION.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_SILK_TOUCH.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_EFFICIENCY.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_FORTUNE.get());
                        output.accept(AncestralArcaneItems.INTENT_INK_LOYALTY.get());
                        output.accept(AncestralArcaneItems.SCROLL.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T1.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T2.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T3.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T4.get());
                        output.accept(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get());
                        output.accept(AncestralArcaneItems.END_SIGIL.get());
                        output.accept(AncestralArcaneItems.FLINT_WAND.get());
                        output.accept(AncestralArcaneItems.COPPER_WAND.get());
                        output.accept(AncestralArcaneItems.IRON_WAND.get());
                        output.accept(AncestralArcaneItems.GOLDEN_WAND.get());
                        output.accept(AncestralArcaneItems.DIAMOND_WAND.get());
                        output.accept(AncestralArcaneItems.EMERALD_WAND.get());
                        output.accept(AncestralArcaneItems.NETHERITE_WAND.get());
                        output.accept(AncestralArcaneItems.ARCANE_SMITHING_TABLE.get());
                        output.accept(AncestralArcaneItems.HOME_ANCHOR.get());
                    }).build());

    public static void addCreativeItems(BuildCreativeModeTabContentsEvent event) {
    }
}
