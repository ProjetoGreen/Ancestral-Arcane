package com.ancestralarcane.registry;

import com.ancestralarcane.AncestralArcaneMod;
import com.ancestralarcane.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public class AncestralArcaneItems {
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AncestralArcaneMod.MODID);

        public static final DeferredItem<Item> RUNE = ITEMS.register("rune",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> ARCANE_RESIDUE = ITEMS.register("arcane_residue",
                        () -> new Item(new Item.Properties()));

        // Intent Inks
        public static final DeferredItem<Item> INTENT_INK_FLAME = ITEMS.register("intent_ink_flame",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_CHANNELING = ITEMS.register("intent_ink_channeling",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_MENDING = ITEMS.register("intent_ink_mending",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_RESPIRATION = ITEMS.register("intent_ink_respiration",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_SILK_TOUCH = ITEMS.register("intent_ink_silk_touch",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_EFFICIENCY = ITEMS.register("intent_ink_efficiency",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_FORTUNE = ITEMS.register("intent_ink_fortune",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> INTENT_INK_LOYALTY = ITEMS.register("intent_ink_loyalty",
                        () -> new Item(new Item.Properties()));

        public static final DeferredItem<Item> SCROLL = ITEMS.register("scroll", () -> new Item(new Item.Properties()));

        public static final DeferredItem<Item> GRIMOIRE_T1 = ITEMS.register("grimoire_t1",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T2 = ITEMS.register("grimoire_t2",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T3 = ITEMS.register("grimoire_t3",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T4 = ITEMS.register("grimoire_t4",
                        () -> new Item(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<Item> FORGOTTEN_MAGICBOOK = ITEMS.register("forgotten_magicbook",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> END_SIGIL = ITEMS.register("end_sigil",
                        () -> new Item(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<ArcaneSpearFocusItem> ARCANE_SPEAR_FOCUS = ITEMS.register("arcane_spear_focus",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));

        // BlockItems
        public static final DeferredItem<BlockItem> ARCANE_SMITHING_TABLE = ITEMS.register("arcane_smithing_table",
                        () -> new BlockItem(AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get(), new Item.Properties()));
        public static final DeferredItem<BlockItem> HOME_ANCHOR = ITEMS.register("home_anchor",
                        () -> new BlockItem(AncestralArcaneBlocks.HOME_ANCHOR.get(), new Item.Properties()));
}
