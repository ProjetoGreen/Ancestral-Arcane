package com.ancestralarcane.registry;

import com.ancestralarcane.AncestralArcaneMod;
import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

public class AncestralArcaneCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, AncestralArcaneMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DNTR_TAB = CREATIVE_MODE_TABS
            .register("ancestral_arcane_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.ancestral_arcane"))
                    .icon(() -> new ItemStack(AncestralArcaneItems.RUNE.get()))
                    .displayItems((parameters, output) -> {

                        // 1. Crude Runes (T1-T5)
                        for (int i = 1; i <= 5; i++) {
                            Item item = getCrudeRuneItem(i);
                            ItemStack stack = new ItemStack(item);
                            CompoundTag data = new CompoundTag();
                            CompoundTag rData = new CompoundTag();
                            rData.putInt("crude", 1);
                            rData.putInt("empty", 1);
                            rData.putInt("tier", i);
                            data.put("rune", rData);
                            CustomDataUtil.setAncestralArcaneData(stack, data);
                            output.accept(stack);
                        }

                        // 2. Normal Runes (Tears 1-5, Empty)
                        for (int i = 1; i <= 5; i++) {
                            Item runeItem = switch (i) {
                                case 1 -> AncestralArcaneItems.RUNE_T1.get();
                                case 2 -> AncestralArcaneItems.RUNE_T2.get();
                                case 3 -> AncestralArcaneItems.RUNE_T3.get();
                                case 4 -> AncestralArcaneItems.RUNE_T4.get();
                                case 5 -> AncestralArcaneItems.RUNE_T5.get();
                                default -> AncestralArcaneItems.RUNE_T1.get();
                            };
                            ItemStack stack = new ItemStack(runeItem);
                            CompoundTag data = new CompoundTag();
                            CompoundTag rData = new CompoundTag();
                            rData.putInt("crude", 0);
                            rData.putInt("empty", 1);
                            rData.putInt("tier", i);
                            data.put("rune", rData);
                            CustomDataUtil.setAncestralArcaneData(stack, data);
                            output.accept(stack);
                        }

                        // 3. Examples of specialized runes
                        addSpecialRunes(output);

                        // 4. Scrolls
                        addScrolls(output);

                        // 5. Empty Grimoires (T1-T5)
                        for (int i = 1; i <= 5; i++) {
                            Item grimItem = getEmptyGrimoireItem(i);
                            ItemStack stack = new ItemStack(grimItem);
                            CompoundTag data = new CompoundTag();
                            CompoundTag gData = new CompoundTag();
                            gData.putInt("tier", i);
                            int uses = (i == 5 ? 15 : i == 4 ? 12 : i == 3 ? 9 : i == 2 ? 6 : 3);
                            gData.putInt("uses", uses);
                            data.put("grimoire", gData);
                            CustomDataUtil.setAncestralArcaneData(stack, data);
                            output.accept(stack);
                        }

                        // 6. Spell Grimoires (Specialized)
                        addSpellGrimoires(output);

                        // 7. Forgotten Magic Book
                        output.accept(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get());

                        // 8. Wands
                        addWands(output);

                        // 9. Catalysts & Others
                        addOthers(output);

                    }).build());

    private static Item getCrudeRuneItem(int tier) {
        return switch (tier) {
            case 2 -> AncestralArcaneItems.RUNE_CRUDE_T2.get();
            case 3 -> AncestralArcaneItems.RUNE_CRUDE_T3.get();
            case 4 -> AncestralArcaneItems.RUNE_CRUDE_T4.get();
            case 5 -> AncestralArcaneItems.RUNE_CRUDE_T5.get();
            default -> AncestralArcaneItems.RUNE_CRUDE_T1.get();
        };
    }

    private static Item getEmptyGrimoireItem(int tier) {
        return switch (tier) {
            case 2 -> AncestralArcaneItems.GRIMOIRE_T2.get();
            case 3 -> AncestralArcaneItems.GRIMOIRE_T3.get();
            case 4 -> AncestralArcaneItems.GRIMOIRE_T4.get();
            case 5 -> AncestralArcaneItems.GRIMOIRE_T5.get();
            default -> AncestralArcaneItems.GRIMOIRE_T1.get();
        };
    }

    private static void addSpecialRunes(CreativeModeTab.Output output) {
        // Example Upgraded
        ItemStack upgRune = new ItemStack(AncestralArcaneItems.RUNE.get());
        CompoundTag upgData = new CompoundTag();
        CompoundTag uRune = new CompoundTag();
        uRune.putInt("empty", 1);
        uRune.putInt("tier", 1);
        CompoundTag uTag = new CompoundTag();
        uTag.putString("type", "blaze");
        uTag.putInt("level", 1);
        uRune.put("upgrade", uTag);
        upgData.put("rune", uRune);
        CustomDataUtil.setAncestralArcaneData(upgRune, upgData);
        output.accept(upgRune);

        // Example Inscribed
        ItemStack inscribedRune = new ItemStack(AncestralArcaneItems.RUNE.get());
        CompoundTag inscData = new CompoundTag();
        CompoundTag inscRune = new CompoundTag();
        inscRune.putInt("empty", 0);
        inscRune.putInt("lvl", 1);
        inscRune.putInt("tier", 1);
        inscRune.putString("spell", "fire");
        inscData.put("rune", inscRune);
        CustomDataUtil.setAncestralArcaneData(inscribedRune, inscData);
        output.accept(inscribedRune);
    }

    private static void addScrolls(CreativeModeTab.Output output) {
        output.accept(AncestralArcaneItems.SCROLL_FIRE.get());
        output.accept(AncestralArcaneItems.SCROLL_FIRE_FRIEND.get());
        output.accept(AncestralArcaneItems.SCROLL_STORM.get());
        output.accept(AncestralArcaneItems.SCROLL_FROST.get());
        output.accept(AncestralArcaneItems.SCROLL_FROST_WALKER.get());
        output.accept(AncestralArcaneItems.SCROLL_HEAL.get());
        output.accept(AncestralArcaneItems.SCROLL_MEND.get());
        output.accept(AncestralArcaneItems.SCROLL_STABILIZE.get());
        output.accept(AncestralArcaneItems.SCROLL_CLEANSE.get());
        output.accept(AncestralArcaneItems.SCROLL_BREATHE.get());
        output.accept(AncestralArcaneItems.SCROLL_FERTILIZE.get());
        output.accept(AncestralArcaneItems.SCROLL_LIGHT.get());
        output.accept(AncestralArcaneItems.SCROLL_BREAKER.get());
        output.accept(AncestralArcaneItems.SCROLL_WARD.get());
        output.accept(AncestralArcaneItems.SCROLL_STONEBIND.get());
        output.accept(AncestralArcaneItems.SCROLL_REACH.get());
        output.accept(AncestralArcaneItems.SCROLL_SILENCE.get());
    }

    private static void addSpellGrimoires(CreativeModeTab.Output output) {
        String[] spells = {"fire", "fire_friend", "storm", "frost", "frost_walker", "heal", "mend", "stabilize", "cleanse", "breathe", "fertilize", "light", "breaker", "ward", "stonebind", "reach", "silence"};
        for (String s : spells) {
            Item item = getGrimoireForSpell(s);
            ItemStack stack = new ItemStack(item);
            CompoundTag data = new CompoundTag();
            CompoundTag gData = new CompoundTag();
            gData.putInt("tier", 1);
            gData.putInt("uses", 3);
            gData.putString("spell", s);
            data.put("grimoire", gData);
            CustomDataUtil.setAncestralArcaneData(stack, data);
            output.accept(stack);
        }
    }

    private static Item getGrimoireForSpell(String spell) {
        return switch (spell) {
            case "fire_friend" -> AncestralArcaneItems.GRIMOIRE_FIRE_FRIEND.get();
            case "storm" -> AncestralArcaneItems.GRIMOIRE_STORM.get();
            case "frost" -> AncestralArcaneItems.GRIMOIRE_FROST.get();
            case "frost_walker" -> AncestralArcaneItems.GRIMOIRE_FROST_WALKER.get();
            case "heal" -> AncestralArcaneItems.GRIMOIRE_HEAL.get();
            case "mend" -> AncestralArcaneItems.GRIMOIRE_MEND.get();
            case "stabilize" -> AncestralArcaneItems.GRIMOIRE_STABILIZE.get();
            case "cleanse" -> AncestralArcaneItems.GRIMOIRE_CLEANSE.get();
            case "breathe" -> AncestralArcaneItems.GRIMOIRE_BREATHE.get();
            case "fertilize" -> AncestralArcaneItems.GRIMOIRE_FERTILIZE.get();
            case "light" -> AncestralArcaneItems.GRIMOIRE_LIGHT.get();
            case "breaker" -> AncestralArcaneItems.GRIMOIRE_BREAKER.get();
            case "ward" -> AncestralArcaneItems.GRIMOIRE_WARD.get();
            case "stonebind" -> AncestralArcaneItems.GRIMOIRE_STONEBIND.get();
            case "reach" -> AncestralArcaneItems.GRIMOIRE_REACH.get();
            case "silence" -> AncestralArcaneItems.GRIMOIRE_SILENCE.get();
            default -> AncestralArcaneItems.GRIMOIRE_FIRE.get();
        };
    }

    private static void addWands(CreativeModeTab.Output output) {
        Item[] wands = {
                AncestralArcaneItems.FLINT_WAND.get(),
                AncestralArcaneItems.COPPER_WAND.get(),
                AncestralArcaneItems.IRON_WAND.get(),
                AncestralArcaneItems.GOLDEN_WAND.get(),
                AncestralArcaneItems.DIAMOND_WAND.get(),
                AncestralArcaneItems.EMERALD_WAND.get(),
                AncestralArcaneItems.NETHERITE_WAND.get(),
                AncestralArcaneItems.FLINT_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.COPPER_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.IRON_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.GOLDEN_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.DIAMOND_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.EMERALD_WAND_LEATHER_GRIP.get(),
                AncestralArcaneItems.NETHERITE_WAND_LEATHER_GRIP.get()
        };
        for (Item wand : wands) {
            output.accept(wand);
            // If netherite, also add a socketed version
            if (Objects.requireNonNull(Objects.requireNonNull(wand.getDescriptionId())).contains("netherite")) {
                ItemStack socketed = new ItemStack(wand);
                CompoundTag data = new CompoundTag();
                data.putBoolean("socketed_grimoire", true);
                CustomDataUtil.setAncestralArcaneData(socketed, data);
                output.accept(socketed);
            }
        }
    }

    private static void addOthers(CreativeModeTab.Output output) {
        output.accept(AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get());
        output.accept(AncestralArcaneItems.FLINT_CATALYST.get());
        output.accept(AncestralArcaneItems.COPPER_CATALYST.get());
        output.accept(AncestralArcaneItems.IRON_CATALYST.get());
        output.accept(AncestralArcaneItems.GOLD_CATALYST.get());
        output.accept(AncestralArcaneItems.DIAMOND_CATALYST.get());
        output.accept(AncestralArcaneItems.EMERALD_CATALYST.get());
        output.accept(AncestralArcaneItems.NETHERITE_CATALYST.get());
        output.accept(AncestralArcaneItems.ARCANE_SMITHING_TABLE.get());
        output.accept(AncestralArcaneItems.HOME_ANCHOR.get());
    }
}
