package com.ancestralarcane.loot;

import com.ancestralarcane.registry.AncestralArcaneItems;
import com.ancestralarcane.data.CustomDataUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ChestLootModifier extends LootModifier {

    public static final MapCodec<ChestLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            inst -> codecStart(inst).apply(inst, ChestLootModifier::new));

    public ChestLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
            LootContext context) {
        String name = context.getQueriedLootTableId().toString();

        if (name.contains("end_city_treasure")) {
            generateEndCity(generatedLoot, context);
            return generatedLoot;
        }

        if (name.contains("chest")
                && (name.contains("dungeon") || name.contains("mineshaft") || name.contains("stronghold_library")
                        || name.contains("desert_pyramid") || name.contains("jungle_temple"))) {
            generateOverworld(generatedLoot, context);
        }

        return generatedLoot;
    }

    private void generateOverworld(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        float f = context.getRandom().nextFloat();
        if (f <= 0.10f) {
            generatedLoot.add(createGrim(1));
        } else if (f <= 0.14f) {
            generatedLoot.add(createGrim(2));
        } else if (f <= 0.15f) {
            generatedLoot.add(createGrim(3));
        } else if (f <= 0.18f) {
            ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
            CompoundTag rd = new CompoundTag();
            rd.putString("kind", "rune");
            rd.putInt("tier", 2);
            rd.putInt("crude", 0);
            rd.putInt("empty", 1);
            rd.putInt("crafted", 0);
            CustomDataUtil.setAncestralArcaneData(rune, rd);
            generatedLoot.add(rune);
        } else if (f <= 0.19f) {
            ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
            CompoundTag rd = new CompoundTag();
            rd.putString("kind", "rune");
            rd.putInt("tier", 2);
            rd.putInt("crude", 0);
            rd.putInt("empty", 0);
            rd.putInt("crafted", 0);
            rd.putString("spell", "flame");
            rd.putInt("lvl", 1);
            rd.putInt("charges", 10);
            rd.putInt("dirty", 0);
            CustomDataUtil.setAncestralArcaneData(rune, rd);
            generatedLoot.add(rune);
        }
    }

    private void generateEndCity(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        float f = context.getRandom().nextFloat();
        if (f <= 0.05f) {
            generatedLoot.add(createGrim(4));
        } else if (f <= 0.16f) {
            ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
            CompoundTag rd = new CompoundTag();
            rd.putString("kind", "rune");
            rd.putInt("tier", 5);
            rd.putInt("crude", 0);
            rd.putInt("empty", 1);
            rd.putInt("crafted", 0);
            CustomDataUtil.setAncestralArcaneData(rune, rd);
            generatedLoot.add(rune);
        } else if (f <= 0.19f) {
            ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
            CompoundTag rd = new CompoundTag();
            rd.putString("kind", "rune");
            rd.putInt("tier", 5);
            rd.putInt("crude", 0);
            rd.putInt("empty", 0);
            rd.putInt("crafted", 0);
            rd.putString("spell", "heartstone");
            rd.putInt("lvl", 4);
            rd.putInt("charges", 40);
            rd.putInt("dirty", 0);
            CustomDataUtil.setAncestralArcaneData(rune, rd);
            generatedLoot.add(rune);
        }
    }

    private ItemStack createGrim(int t) {
        ItemStack grim = new ItemStack(
                t == 1 ? AncestralArcaneItems.GRIMOIRE_T1.get()
                        : t == 2 ? AncestralArcaneItems.GRIMOIRE_T2.get()
                                : t == 3 ? AncestralArcaneItems.GRIMOIRE_T3.get()
                                        : AncestralArcaneItems.GRIMOIRE_T4.get());
        CompoundTag gd = new CompoundTag();
        gd.putString("kind", "grimoire");
        gd.putInt("tier", t);
        gd.putInt("uses", 10);
        gd.putInt("clones", 5);
        CustomDataUtil.setAncestralArcaneData(grim, gd);
        return grim;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
