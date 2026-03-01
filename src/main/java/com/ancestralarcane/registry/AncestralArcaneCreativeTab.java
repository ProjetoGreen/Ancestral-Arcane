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
                        output.accept(AncestralArcaneItems.RUNE.get());
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
