package com.ancestralarcane.loot;

import com.ancestralarcane.AncestralArcaneMod;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AncestralArcaneLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister
            .create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AncestralArcaneMod.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<WitchLootModifier>> WITCH_DROPS = GLOBAL_LOOT_MODIFIER_SERIALIZERS
            .register("witch_drops", () -> WitchLootModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ChestLootModifier>> CHEST_DROPS = GLOBAL_LOOT_MODIFIER_SERIALIZERS
            .register("chest_drops", () -> ChestLootModifier.CODEC);
}
