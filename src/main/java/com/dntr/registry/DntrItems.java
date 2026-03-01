package com.dntr.registry;

import com.dntr.DntrMod;
import com.dntr.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public class DntrItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DntrMod.MODID);

    public static final DeferredItem<Item> RUNE = ITEMS.register("rune",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ARCANE_RESIDUE = ITEMS.register("arcane_residue",
            () -> new Item(new Item.Properties()));

    // Intent Inks
    public static final DeferredItem<Item> INTENT_INK_FIRE = ITEMS.register("intent_ink_fire",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_STORM = ITEMS.register("intent_ink_storm",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_HEAL = ITEMS.register("intent_ink_heal",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_BREATHE = ITEMS.register("intent_ink_breathe",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_HEARTSTONE = ITEMS.register("intent_ink_heartstone",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_BREAKER = ITEMS.register("intent_ink_breaker",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_FERTILIZE = ITEMS.register("intent_ink_fertilize",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INTENT_INK_WOLVES = ITEMS.register("intent_ink_wolves",
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
            () -> new BlockItem(DntrBlocks.ARCANE_SMITHING_TABLE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> HOME_ANCHOR = ITEMS.register("home_anchor",
            () -> new BlockItem(DntrBlocks.HOME_ANCHOR.get(), new Item.Properties()));
}
