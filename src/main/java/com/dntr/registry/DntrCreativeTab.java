package com.dntr.registry;

import com.dntr.DntrMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DntrCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, DntrMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DNTR_TAB = CREATIVE_MODE_TABS
            .register("dntr_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dntr"))
                    .icon(() -> new ItemStack(DntrItems.RUNE.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(DntrItems.RUNE.get());
                        output.accept(DntrItems.ARCANE_RESIDUE.get());
                        output.accept(DntrItems.INTENT_INK_FIRE.get());
                        output.accept(DntrItems.INTENT_INK_STORM.get());
                        output.accept(DntrItems.INTENT_INK_HEAL.get());
                        output.accept(DntrItems.INTENT_INK_BREATHE.get());
                        output.accept(DntrItems.INTENT_INK_HEARTSTONE.get());
                        output.accept(DntrItems.INTENT_INK_BREAKER.get());
                        output.accept(DntrItems.INTENT_INK_FERTILIZE.get());
                        output.accept(DntrItems.INTENT_INK_WOLVES.get());
                        output.accept(DntrItems.SCROLL.get());
                        output.accept(DntrItems.GRIMOIRE_T1.get());
                        output.accept(DntrItems.GRIMOIRE_T2.get());
                        output.accept(DntrItems.GRIMOIRE_T3.get());
                        output.accept(DntrItems.GRIMOIRE_T4.get());
                        output.accept(DntrItems.FORGOTTEN_MAGICBOOK.get());
                        output.accept(DntrItems.END_SIGIL.get());
                        output.accept(DntrItems.ARCANE_SPEAR_FOCUS.get());
                        output.accept(DntrItems.ARCANE_SMITHING_TABLE.get());
                        output.accept(DntrItems.HOME_ANCHOR.get());
                    }).build());

    public static void addCreativeItems(BuildCreativeModeTabContentsEvent event) {
    }
}
