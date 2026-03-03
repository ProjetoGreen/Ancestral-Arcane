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
                        () -> new com.ancestralarcane.item.RuneItem(new Item.Properties().stacksTo(1)));
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
        public static final DeferredItem<EmptyGrimoireItem> GRIMOIRE_T1 = ITEMS.register("grimoire_t1",
                        () -> new EmptyGrimoireItem(new Item.Properties().stacksTo(1), 1));
        public static final DeferredItem<EmptyGrimoireItem> GRIMOIRE_T2 = ITEMS.register("grimoire_t2",
                        () -> new EmptyGrimoireItem(new Item.Properties().stacksTo(1), 2));
        public static final DeferredItem<EmptyGrimoireItem> GRIMOIRE_T3 = ITEMS.register("grimoire_t3",
                        () -> new EmptyGrimoireItem(new Item.Properties().stacksTo(1), 3));
        public static final DeferredItem<EmptyGrimoireItem> GRIMOIRE_T4 = ITEMS.register("grimoire_t4",
                        () -> new EmptyGrimoireItem(new Item.Properties().stacksTo(1), 4));
        public static final DeferredItem<EmptyGrimoireItem> GRIMOIRE_T5 = ITEMS.register("grimoire_t5",
                        () -> new EmptyGrimoireItem(new Item.Properties().stacksTo(1), 5));

        // 17 Spell Grimoires
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_FIRE = ITEMS.register("grimoire_fire",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "fire"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_FIRE_FRIEND = ITEMS.register(
                        "grimoire_fire_friend",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "fire_friend"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_STORM = ITEMS.register("grimoire_storm",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "storm"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_FROST = ITEMS.register("grimoire_frost",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "frost"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_FROST_WALKER = ITEMS.register(
                        "grimoire_frost_walker",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "frost_walker"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_HEAL = ITEMS.register("grimoire_heal",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "heal"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_MEND = ITEMS.register("grimoire_mend",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "mend"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_STABILIZE = ITEMS.register("grimoire_stabilize",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "stabilize"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_CLEANSE = ITEMS.register("grimoire_cleanse",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "cleanse"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_BREATHE = ITEMS.register("grimoire_breathe",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "breathe"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_FERTILIZE = ITEMS.register("grimoire_fertilize",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "fertilize"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_LIGHT = ITEMS.register("grimoire_light",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "light"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_BREAKER = ITEMS.register("grimoire_breaker",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "breaker"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_WARD = ITEMS.register("grimoire_ward",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "ward"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_STONEBIND = ITEMS.register("grimoire_stonebind",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "stonebind"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_REACH = ITEMS.register("grimoire_reach",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "reach"));
        public static final DeferredItem<GrimoireSpellItem> GRIMOIRE_SILENCE = ITEMS.register("grimoire_silence",
                        () -> new GrimoireSpellItem(new Item.Properties().stacksTo(1), "silence"));

        public static final DeferredItem<Item> FORGOTTEN_MAGICBOOK = ITEMS.register("forgotten_magicbook",
                        () -> new Item(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<WandItem> COPPER_WAND = ITEMS.register("copper_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> DIAMOND_WAND = ITEMS.register("diamond_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> EMERALD_WAND = ITEMS.register("emerald_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> FLINT_WAND = ITEMS.register("flint_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> GOLDEN_WAND = ITEMS.register("golden_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> IRON_WAND = ITEMS.register("iron_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> NETHERITE_WAND = ITEMS.register("netherite_wand",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<WandItem> COPPER_WAND_LEATHER_GRIP = ITEMS.register(
                        "copper_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> DIAMOND_WAND_LEATHER_GRIP = ITEMS.register(
                        "diamond_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> EMERALD_WAND_LEATHER_GRIP = ITEMS.register(
                        "emerald_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> FLINT_WAND_LEATHER_GRIP = ITEMS.register(
                        "flint_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> GOLDEN_WAND_LEATHER_GRIP = ITEMS.register(
                        "golden_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> IRON_WAND_LEATHER_GRIP = ITEMS.register(
                        "iron_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));
        public static final DeferredItem<WandItem> NETHERITE_WAND_LEATHER_GRIP = ITEMS.register(
                        "netherite_wand_leather_grip",
                        () -> new WandItem(new Item.Properties().stacksTo(1)));

        // BlockItems
        public static final DeferredItem<BlockItem> ARCANE_SMITHING_TABLE = ITEMS.register("arcane_smithing_table",
                        () -> new BlockItem(AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get(), new Item.Properties()));
        public static final DeferredItem<BlockItem> HOME_ANCHOR = ITEMS.register("home_anchor",
                        () -> new BlockItem(AncestralArcaneBlocks.HOME_ANCHOR.get(), new Item.Properties()));
}
