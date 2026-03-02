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
                    .title(Component.translatable("itemGroup.ancestral_arcane"))
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

                        // Spells & Base Items
                        output.accept(AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get());

                        // Render Tier 1-5 Runes (Crude and Normal)
                        for (int t = 1; t <= 5; t++) {
                            ItemStack crude = new ItemStack(AncestralArcaneItems.RUNE.get());
                            net.minecraft.nbt.CompoundTag cDt = new net.minecraft.nbt.CompoundTag();
                            net.minecraft.nbt.CompoundTag cBody = new net.minecraft.nbt.CompoundTag();
                            cBody.putInt("tier", t);
                            cBody.putInt("crude", 1);
                            cBody.putInt("empty", 1);
                            cDt.put("rune", cBody);
                            com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(crude, cDt);
                            output.accept(crude);

                            ItemStack cons = new ItemStack(AncestralArcaneItems.RUNE.get());
                            net.minecraft.nbt.CompoundTag rDt = new net.minecraft.nbt.CompoundTag();
                            net.minecraft.nbt.CompoundTag rBody = new net.minecraft.nbt.CompoundTag();
                            rBody.putInt("tier", t);
                            rBody.putInt("crude", 0);
                            rBody.putInt("empty", 1);
                            rDt.put("rune", rBody);
                            com.ancestralarcane.data.CustomDataUtil.setAncestralArcaneData(cons, rDt);
                            output.accept(cons);
                        }
                        output.accept(AncestralArcaneItems.SCROLL_FIRE.get());
                        output.accept(AncestralArcaneItems.SCROLL_FIRE_FRIEND.get());
                        output.accept(AncestralArcaneItems.SCROLL_STORM.get());
                        output.accept(AncestralArcaneItems.SCROLL_FROST.get());
                        output.accept(AncestralArcaneItems.SCROLL_FROST_WALKER.get());
                        output.accept(AncestralArcaneItems.SCROLL_HEAL.get());
                        output.accept(AncestralArcaneItems.SCROLL_MEND.get());
                        output.accept(AncestralArcaneItems.SCROLL_STABILIZE.get());
                        output.accept(AncestralArcaneItems.SCROLL_CLEANSE.get());
                        output.accept(AncestralArcaneItems.SCROLL_BREATHE.get());
                        output.accept(AncestralArcaneItems.SCROLL_FERTILIZE.get());
                        output.accept(AncestralArcaneItems.SCROLL_LIGHT.get());
                        output.accept(AncestralArcaneItems.SCROLL_BREAKER.get());
                        output.accept(AncestralArcaneItems.SCROLL_WARD.get());
                        output.accept(AncestralArcaneItems.SCROLL_STONEBIND.get());
                        output.accept(AncestralArcaneItems.SCROLL_REACH.get());
                        output.accept(AncestralArcaneItems.SCROLL_SILENCE.get());

                        output.accept(AncestralArcaneItems.GRIMOIRE_T1.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T2.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T3.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T4.get());
                        output.accept(AncestralArcaneItems.GRIMOIRE_T5.get());

                        output.accept(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get());
                        net.minecraft.world.item.Item[] wands = {
                                AncestralArcaneItems.FLINT_WAND.get(),
                                AncestralArcaneItems.COPPER_WAND.get(),
                                AncestralArcaneItems.IRON_WAND.get(),
                                AncestralArcaneItems.GOLDEN_WAND.get(),
                                AncestralArcaneItems.DIAMOND_WAND.get(),
                                AncestralArcaneItems.EMERALD_WAND.get(),
                                AncestralArcaneItems.NETHERITE_WAND.get(),
                                AncestralArcaneItems.FLINT_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.COPPER_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.IRON_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.GOLDEN_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.DIAMOND_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.EMERALD_WAND_LEATHER_GRIP.get(),
                                AncestralArcaneItems.NETHERITE_WAND_LEATHER_GRIP.get()
                        };
                        for (net.minecraft.world.item.Item wand : wands) {
                            output.accept(wand);
                        }
                        output.accept(AncestralArcaneItems.ARCANE_SMITHING_TABLE.get());
                        output.accept(AncestralArcaneItems.HOME_ANCHOR.get());
                    }).build());

    public static void addCreativeItems(BuildCreativeModeTabContentsEvent event) {
    }
}
