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
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

public class AncestralArcaneGiveTestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ancestral_arcane_give_test")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();

                    ItemStack table = new ItemStack(AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get());
                    CompoundTag beData = new CompoundTag();
                    beData.putInt("tableLevel", 5);
                    table.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(beData));
                    player.getInventory().add(table);

                    ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
                    CompoundTag rd = new CompoundTag();
                    rd.putString("kind", "rune");
                    rd.putInt("tier", 3);
                    rd.putInt("crude", 0);
                    rd.putInt("empty", 1);
                    rd.putInt("crafted", 1);
                    CustomDataUtil.setAncestralArcaneData(rune, rd);
                    player.getInventory().add(rune);

                    ItemStack grim = new ItemStack(AncestralArcaneItems.GRIMOIRE_T3.get());
                    CompoundTag gd = new CompoundTag();
                    gd.putString("kind", "grimoire");
                    gd.putInt("tier", 3);
                    gd.putInt("uses", 10);
                    gd.putInt("clones", 5);
                    CustomDataUtil.setAncestralArcaneData(grim, gd);
                    player.getInventory().add(grim);

                    player.getInventory().add(new ItemStack(AncestralArcaneItems.INTENT_INK_FLAME.get(), 10));
                    player.getInventory().add(new ItemStack(AncestralArcaneItems.INTENT_INK_CHANNELING.get(), 10));
                    player.getInventory().add(new ItemStack(AncestralArcaneItems.INTENT_INK_MENDING.get(), 10));
                    player.getInventory().add(new ItemStack(AncestralArcaneItems.INTENT_INK_RESPIRATION.get(), 10));

                    ItemStack spear = new ItemStack(AncestralArcaneItems.FLINT_WAND.get());
                    CompoundTag sd = new CompoundTag();
                    sd.putString("kind", "focus_spear");
                    sd.putString("catalyst", "iron");
                    sd.putInt("cast_time_base", 5);

                    CompoundTag actRune = new CompoundTag();
                    actRune.putString("kind", "rune");
                    actRune.putInt("tier", 3);
                    actRune.putInt("crude", 0);
                    actRune.putInt("empty", 0);
                    actRune.putInt("crafted", 1);
                    actRune.putString("spell", "flame");
                    actRune.putInt("lvl", 3);
                    actRune.putInt("charges", 30);
                    actRune.putInt("dirty", 0);

                    sd.put("rune", actRune);
                    CustomDataUtil.setAncestralArcaneData(spear, sd);
                    player.getInventory().add(spear);

                    player.getInventory().add(new ItemStack(Items.GLOWSTONE_DUST, 32));
                    player.getInventory().add(new ItemStack(Items.ECHO_SHARD, 8));
                    player.getInventory().add(new ItemStack(Items.BONE_MEAL, 32));
                    player.getInventory().add(new ItemStack(Items.BONE, 16));
                    player.getInventory().add(new ItemStack(Items.IRON_INGOT, 8));
                    player.getInventory().add(new ItemStack(Items.EMERALD, 64));
                    player.getInventory().add(new ItemStack(Items.CHORUS_FLOWER, 1));

                    context.getSource().sendSuccess(() -> Component.literal("Test resources given."), true);
                    return 1;
                }));
    }
}
