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

        public static final DeferredItem<Item> FRAGMENT_OF_ALL_KNOWLEDGE = ITEMS.register("fragment_of_all_knowledge",
                        () -> new Item(new Item.Properties()));

        // 17 Spells Scrolls
        public static final DeferredItem<Item> SCROLL_FIRE = ITEMS.register("scroll_fire",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_FIRE_FRIEND = ITEMS.register("scroll_fire_friend",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_STORM = ITEMS.register("scroll_storm",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_FROST = ITEMS.register("scroll_frost",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_FROST_WALKER = ITEMS.register("scroll_frost_walker",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_HEAL = ITEMS.register("scroll_heal",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_MEND = ITEMS.register("scroll_mend",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_STABILIZE = ITEMS.register("scroll_stabilize",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_CLEANSE = ITEMS.register("scroll_cleanse",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_BREATHE = ITEMS.register("scroll_breathe",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_FERTILIZE = ITEMS.register("scroll_fertilize",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_LIGHT = ITEMS.register("scroll_light",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_BREAKER = ITEMS.register("scroll_breaker",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_WARD = ITEMS.register("scroll_ward",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_STONEBIND = ITEMS.register("scroll_stonebind",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_REACH = ITEMS.register("scroll_reach",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SCROLL_SILENCE = ITEMS.register("scroll_silence",
                        () -> new Item(new Item.Properties()));

        // Grimoires
        public static final DeferredItem<Item> GRIMOIRE_T1 = ITEMS.register("grimoire_t1",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T2 = ITEMS.register("grimoire_t2",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T3 = ITEMS.register("grimoire_t3",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T4 = ITEMS.register("grimoire_t4",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> GRIMOIRE_T5 = ITEMS.register("grimoire_t5",
                        () -> new Item(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<Item> FORGOTTEN_MAGICBOOK = ITEMS.register("forgotten_magicbook",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<Item> END_SIGIL = ITEMS.register("end_sigil",
                        () -> new Item(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<ArcaneSpearFocusItem> COPPER_WAND = ITEMS.register("copper_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> DIAMOND_WAND = ITEMS.register("diamond_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> EMERALD_WAND = ITEMS.register("emerald_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> FLINT_WAND = ITEMS.register("flint_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> GOLDEN_WAND = ITEMS.register("golden_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> IRON_WAND = ITEMS.register("iron_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<ArcaneSpearFocusItem> NETHERITE_WAND = ITEMS.register("netherite_wand",
                        () -> new ArcaneSpearFocusItem(new Item.Properties().stacksTo(1)));

        // BlockItems
        public static final DeferredItem<BlockItem> ARCANE_SMITHING_TABLE = ITEMS.register("arcane_smithing_table",
                        () -> new BlockItem(AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get(), new Item.Properties()));
        public static final DeferredItem<BlockItem> HOME_ANCHOR = ITEMS.register("home_anchor",
                        () -> new BlockItem(AncestralArcaneBlocks.HOME_ANCHOR.get(), new Item.Properties()));
}
