package com.ancestralarcane.trades;

import com.ancestralarcane.registry.AncestralArcaneItems;
import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.minecraft.world.entity.npc.VillagerProfession;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = com.ancestralarcane.AncestralArcaneMod.MODID)
public class VillagerTradeInjector {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            List<VillagerTrades.ItemListing> masterTrades = event.getTrades().get(5);
            if (masterTrades != null) {
                masterTrades.add(new MagicTradeListing());
            }
        }
    }

    private static class MagicTradeListing implements VillagerTrades.ItemListing {
        @Nullable
        @Override
        public MerchantOffer getOffer(@Nonnull Entity trader, @Nonnull RandomSource random) {
            float chance = random.nextFloat();
            if (chance < 0.02f) { // 2%
                ItemStack grim = new ItemStack(AncestralArcaneItems.GRIMOIRE_T4.get());
                CompoundTag gd = new CompoundTag();
                gd.putString("kind", "grimoire");
                gd.putInt("tier", 4);
                gd.putInt("uses", 10);
                gd.putInt("clones", 5);
                CustomDataUtil.setAncestralArcaneData(grim, gd);

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, 64),
                        Optional.of(new ItemCost(Items.BOOK, 1)),
                        grim, 1, 30, 0.05f);
            } else if (chance < 0.08f) { // 6%
                ItemStack grim = new ItemStack(AncestralArcaneItems.GRIMOIRE_T3.get());
                CompoundTag gd = new CompoundTag();
                gd.putString("kind", "grimoire");
                gd.putInt("tier", 3);
                gd.putInt("uses", 10);
                gd.putInt("clones", 5);
                CustomDataUtil.setAncestralArcaneData(grim, gd);

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, 48),
                        Optional.of(new ItemCost(Items.BOOK, 1)),
                        grim, 1, 20, 0.05f);
            } else if (chance < 0.20f) { // 12%
                ItemStack grim = new ItemStack(AncestralArcaneItems.GRIMOIRE_T2.get());
                CompoundTag gd = new CompoundTag();
                gd.putString("kind", "grimoire");
                gd.putInt("tier", 2);
                gd.putInt("uses", 10);
                gd.putInt("clones", 5);
                CustomDataUtil.setAncestralArcaneData(grim, gd);

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, 32),
                        Optional.of(new ItemCost(Items.BOOK, 1)),
                        grim, 1, 15, 0.05f);
            } else if (chance < 0.40f) { // 20%
                ItemStack grim = new ItemStack(AncestralArcaneItems.GRIMOIRE_T1.get());
                CompoundTag gd = new CompoundTag();
                gd.putString("kind", "grimoire");
                gd.putInt("tier", 1);
                gd.putInt("uses", 10);
                gd.putInt("clones", 5);
                CustomDataUtil.setAncestralArcaneData(grim, gd);

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, 16),
                        Optional.of(new ItemCost(Items.BOOK, 1)),
                        grim, 1, 10, 0.05f);
            }
            return null;
        }
    }
}
