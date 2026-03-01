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

public class WitchLootModifier extends LootModifier {

    public static final MapCodec<WitchLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            inst -> codecStart(inst).apply(inst, WitchLootModifier::new));

    public WitchLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
            LootContext context) {
        float f = context.getRandom().nextFloat();
        if (f <= 0.12f) { // 12% grimoire
            float tf = context.getRandom().nextFloat(); // t1 70, t2 20, t3 9, t4 1 => 100
            int t = 1;
            if (tf > 0.70f)
                t = 2;
            if (tf > 0.90f)
                t = 3;
            if (tf > 0.99f)
                t = 4;

            ItemStack grim = new ItemStack(
                    t == 1 ? AncestralArcaneItems.GRIMOIRE_T1.get()
                            : t == 2 ? AncestralArcaneItems.GRIMOIRE_T2.get()
                                    : t == 3 ? AncestralArcaneItems.GRIMOIRE_T3.get() : AncestralArcaneItems.GRIMOIRE_T4.get());
            CompoundTag gd = new CompoundTag();
            gd.putString("kind", "grimoire");
            gd.putInt("tier", t);
            gd.putInt("uses", 10);
            gd.putInt("clones", 5);
            CustomDataUtil.setAncestralArcaneData(grim, gd);
            generatedLoot.add(grim);

        } else if (f <= 0.18f) { // 6% rune ativa ancient
            float tf = context.getRandom().nextFloat();
            int lvl = 1;
            if (tf > 0.60f)
                lvl = 2;
            if (tf > 0.90f)
                lvl = 3;

            int tier = (lvl == 1) ? 2 : (lvl == 2) ? 2 : 3;

            float sf = context.getRandom().nextFloat(); // fire 35, storm 25, heal 25, breathe 15
            String spell = "fire";
            if (sf > 0.35f)
                spell = "storm";
            if (sf > 0.60f)
                spell = "heal";
            if (sf > 0.85f)
                spell = "breathe";

            ItemStack rune = new ItemStack(AncestralArcaneItems.RUNE.get());
            CompoundTag rd = new CompoundTag();
            rd.putString("kind", "rune");
            rd.putInt("tier", tier);
            rd.putInt("crude", 0);
            rd.putInt("empty", 0);
            rd.putInt("crafted", 0);
            rd.putString("spell", spell);
            rd.putInt("lvl", lvl);
            rd.putInt("charges", lvl * 10);
            rd.putInt("dirty", 0);
            CustomDataUtil.setAncestralArcaneData(rune, rd);
            generatedLoot.add(rune);

        } else if (f <= 0.38f) { // 20% residue
            generatedLoot.add(new ItemStack(AncestralArcaneItems.ARCANE_RESIDUE.get()));
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
