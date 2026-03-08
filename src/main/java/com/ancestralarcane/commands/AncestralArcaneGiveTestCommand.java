package com.ancestralarcane.commands;

import com.ancestralarcane.registry.AncestralArcaneBlocks;
import com.ancestralarcane.registry.AncestralArcaneItems;
import com.ancestralarcane.data.CustomDataUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

public class AncestralArcaneGiveTestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ancestral_arcane_give_test")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();

                    // Function to easily give an item to the player
                    java.util.function.Consumer<ItemStack> give = (stack) -> {
                        if (!player.getInventory().add(stack)) {
                            player.drop(stack, false);
                        }
                    };

                    // Blocks
                    ItemStack table = new ItemStack(AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get());
                    CompoundTag beData = new CompoundTag();
                    beData.putString("id", "ancestral_arcane:arcane_smithing_table");
                    beData.putInt("tableLevel", 5);
                    table.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(beData));
                    give.accept(table);
                    give.accept(new ItemStack(AncestralArcaneBlocks.HOME_ANCHOR.get()));

                    // Miscellaneous Items
                    give.accept(new ItemStack(AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get(), 64));
                    give.accept(new ItemStack(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get()));

                    // Crude & Consecrated Runes (1 Random Tier)
                    int randomRuneTier = player.getRandom().nextInt(5) + 1;

                    // Crude
                    ItemStack crudeRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                    CompoundTag crd = new CompoundTag();
                    crd.putString("kind", "rune");
                    crd.putInt("tier", randomRuneTier);
                    crd.putInt("crude", 1);
                    crd.putInt("empty", 1);
                    crd.putInt("crafted", 1);

                    CompoundTag crudeDataWrapper = new CompoundTag();
                    crudeDataWrapper.put("rune", crd);
                    CustomDataUtil.setAncestralArcaneData(crudeRune, crudeDataWrapper);
                    give.accept(crudeRune);

                    // Consecrated
                    ItemStack consRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                    CompoundTag rd = new CompoundTag();
                    rd.putString("kind", "rune");
                    rd.putInt("tier", randomRuneTier);
                    rd.putInt("crude", 0);
                    rd.putInt("empty", 1);
                    rd.putInt("crafted", 1);

                    CompoundTag consDataWrapper = new CompoundTag();
                    consDataWrapper.put("rune", rd);
                    CustomDataUtil.setAncestralArcaneData(consRune, consDataWrapper);
                    give.accept(consRune);

                    // Grimoires (Empty Tiers 1-5)
                    for (int tier = 1; tier <= 5; tier++) {
                        ItemStack grimStack = switch (tier) {
                            case 1 -> new ItemStack(AncestralArcaneItems.GRIMOIRE_T1.get());
                            case 2 -> new ItemStack(AncestralArcaneItems.GRIMOIRE_T2.get());
                            case 3 -> new ItemStack(AncestralArcaneItems.GRIMOIRE_T3.get());
                            case 4 -> new ItemStack(AncestralArcaneItems.GRIMOIRE_T4.get());
                            default -> new ItemStack(AncestralArcaneItems.GRIMOIRE_T5.get());
                        };
                        CompoundTag grimData = new CompoundTag();
                        grimData.putInt("tier", tier);
                        grimData.putInt("uses", tier * 2);

                        CompoundTag grimWrapper = new CompoundTag();
                        grimWrapper.put("grimoire", grimData);
                        CustomDataUtil.setAncestralArcaneData(grimStack, grimWrapper);
                        give.accept(grimStack);
                    }

                    // Random Spell Selection
                    String[] spellList = { "fire", "fire_friend", "storm", "frost", "frost_walker", "heal", "mend",
                            "stabilize", "cleanse", "breathe", "fertilize", "light", "breaker", "ward", "stonebind",
                            "reach", "silence" };
                    Item[] scrolls = { AncestralArcaneItems.SCROLL_FIRE.get(),
                            AncestralArcaneItems.SCROLL_FIRE_FRIEND.get(), AncestralArcaneItems.SCROLL_STORM.get(),
                            AncestralArcaneItems.SCROLL_FROST.get(), AncestralArcaneItems.SCROLL_FROST_WALKER.get(),
                            AncestralArcaneItems.SCROLL_HEAL.get(), AncestralArcaneItems.SCROLL_MEND.get(),
                            AncestralArcaneItems.SCROLL_STABILIZE.get(), AncestralArcaneItems.SCROLL_CLEANSE.get(),
                            AncestralArcaneItems.SCROLL_BREATHE.get(), AncestralArcaneItems.SCROLL_FERTILIZE.get(),
                            AncestralArcaneItems.SCROLL_LIGHT.get(), AncestralArcaneItems.SCROLL_BREAKER.get(),
                            AncestralArcaneItems.SCROLL_WARD.get(), AncestralArcaneItems.SCROLL_STONEBIND.get(),
                            AncestralArcaneItems.SCROLL_REACH.get(), AncestralArcaneItems.SCROLL_SILENCE.get() };
                    Item[] grimoires = { AncestralArcaneItems.GRIMOIRE_FIRE.get(),
                            AncestralArcaneItems.GRIMOIRE_FIRE_FRIEND.get(), AncestralArcaneItems.GRIMOIRE_STORM.get(),
                            AncestralArcaneItems.GRIMOIRE_FROST.get(), AncestralArcaneItems.GRIMOIRE_FROST_WALKER.get(),
                            AncestralArcaneItems.GRIMOIRE_HEAL.get(), AncestralArcaneItems.GRIMOIRE_MEND.get(),
                            AncestralArcaneItems.GRIMOIRE_STABILIZE.get(), AncestralArcaneItems.GRIMOIRE_CLEANSE.get(),
                            AncestralArcaneItems.GRIMOIRE_BREATHE.get(), AncestralArcaneItems.GRIMOIRE_FERTILIZE.get(),
                            AncestralArcaneItems.GRIMOIRE_LIGHT.get(), AncestralArcaneItems.GRIMOIRE_BREAKER.get(),
                            AncestralArcaneItems.GRIMOIRE_WARD.get(), AncestralArcaneItems.GRIMOIRE_STONEBIND.get(),
                            AncestralArcaneItems.GRIMOIRE_REACH.get(), AncestralArcaneItems.GRIMOIRE_SILENCE.get() };

                    int randIdx = player.getRandom().nextInt(spellList.length);
                    String randomSpellName = spellList[randIdx];
                    give.accept(new ItemStack(scrolls[randIdx], 10));
                    give.accept(new ItemStack(grimoires[randIdx]));

                    // Single Fully Inscribed test Rune
                    ItemStack inscribed = new ItemStack(AncestralArcaneItems.RUNE.get());
                    CompoundTag rData = new CompoundTag();
                    rData.putString("kind", "rune");
                    rData.putInt("tier", 5);
                    rData.putInt("crude", 0);
                    rData.putInt("empty", 0);
                    rData.putInt("crafted", 1);
                    rData.putString("spell", randomSpellName);
                    rData.putInt("lvl", 5);
                    rData.putInt("charges", 50);
                    rData.putInt("dirty", 0);

                    CompoundTag mainData = new CompoundTag();
                    mainData.put("rune", rData);
                    CustomDataUtil.setAncestralArcaneData(inscribed, mainData);
                    give.accept(inscribed);

                    // 5 Example Wands
                    give.accept(new ItemStack(AncestralArcaneItems.COPPER_WAND.get()));
                    give.accept(new ItemStack(AncestralArcaneItems.IRON_WAND.get()));
                    give.accept(new ItemStack(AncestralArcaneItems.GOLDEN_WAND.get()));
                    give.accept(new ItemStack(AncestralArcaneItems.DIAMOND_WAND.get()));
                    give.accept(new ItemStack(AncestralArcaneItems.NETHERITE_WAND.get()));

                    // Test Wand with Rune inside
                    ItemStack wand = new ItemStack(AncestralArcaneItems.FLINT_WAND.get());
                    CompoundTag sd = new CompoundTag();
                    sd.putString("kind", "wand");
                    sd.putString("catalyst", "iron");
                    sd.putInt("cast_time_base", 5);

                    CompoundTag actRune = new CompoundTag();
                    actRune.putString("kind", "rune");
                    actRune.putInt("tier", 5);
                    actRune.putInt("crude", 0);
                    actRune.putInt("empty", 0);
                    actRune.putInt("crafted", 1);
                    actRune.putString("spell", "storm");
                    actRune.putInt("lvl", 10);
                    actRune.putInt("charges", 100);
                    actRune.putInt("dirty", 0);

                    sd.put("rune", actRune);
                    CustomDataUtil.setAncestralArcaneData(wand, sd);
                    give.accept(wand);

                    // Reagents and Vanilla Crafting Items
                    give.accept(new ItemStack(Items.PAPER, 64));
                    give.accept(new ItemStack(Items.INK_SAC, 64));
                    give.accept(new ItemStack(Items.FEATHER, 64));
                    give.accept(new ItemStack(Items.RED_DYE, 64));
                    give.accept(new ItemStack(Items.CLAY_BALL, 64));
                    give.accept(new ItemStack(Items.GLOWSTONE_DUST, 64));
                    give.accept(new ItemStack(Items.ECHO_SHARD, 64));
                    give.accept(new ItemStack(Items.BONE_MEAL, 64));
                    give.accept(new ItemStack(Items.BONE, 64));
                    give.accept(new ItemStack(Items.IRON_INGOT, 64));
                    give.accept(new ItemStack(Items.EMERALD, 64));
                    give.accept(new ItemStack(Items.CHORUS_FLOWER, 64));
                    give.accept(new ItemStack(Items.LEATHER, 64));

                    context.getSource().sendSuccess(() -> Component.literal("Mod items given."), true);
                    return 1;
                }));
    }
}
