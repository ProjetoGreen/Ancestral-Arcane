package com.dntr.commands;

import com.dntr.registry.DntrBlocks;
import com.dntr.registry.DntrItems;
import com.dntr.data.CustomDataUtil;
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

public class DntrGiveTestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dntr_give_test")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();

                    ItemStack table = new ItemStack(DntrBlocks.ARCANE_SMITHING_TABLE.get());
                    CompoundTag beData = new CompoundTag();
                    beData.putInt("tableLevel", 5);
                    table.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(beData));
                    player.getInventory().add(table);

                    ItemStack rune = new ItemStack(DntrItems.RUNE.get());
                    CompoundTag rd = new CompoundTag();
                    rd.putString("kind", "rune");
                    rd.putInt("tier", 3);
                    rd.putInt("crude", 0);
                    rd.putInt("empty", 1);
                    rd.putInt("crafted", 1);
                    CustomDataUtil.setDntrData(rune, rd);
                    player.getInventory().add(rune);

                    ItemStack grim = new ItemStack(DntrItems.GRIMOIRE_T3.get());
                    CompoundTag gd = new CompoundTag();
                    gd.putString("kind", "grimoire");
                    gd.putInt("tier", 3);
                    gd.putInt("uses", 10);
                    gd.putInt("clones", 5);
                    CustomDataUtil.setDntrData(grim, gd);
                    player.getInventory().add(grim);

                    player.getInventory().add(new ItemStack(DntrItems.INTENT_INK_FIRE.get(), 10));
                    player.getInventory().add(new ItemStack(DntrItems.INTENT_INK_STORM.get(), 10));
                    player.getInventory().add(new ItemStack(DntrItems.INTENT_INK_HEAL.get(), 10));
                    player.getInventory().add(new ItemStack(DntrItems.INTENT_INK_BREATHE.get(), 10));

                    ItemStack spear = new ItemStack(DntrItems.ARCANE_SPEAR_FOCUS.get());
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
                    actRune.putString("spell", "fire");
                    actRune.putInt("lvl", 3);
                    actRune.putInt("charges", 30);
                    actRune.putInt("dirty", 0);

                    sd.put("rune", actRune);
                    CustomDataUtil.setDntrData(spear, sd);
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
