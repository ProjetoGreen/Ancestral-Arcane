package com.ancestralarcane.datagen;

import com.ancestralarcane.registry.AncestralArcaneItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class AncestralArcaneRecipeProvider extends VanillaRecipeProvider {

    public AncestralArcaneRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static Criterion<?> inv(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private static ResourceLocation rl(String name) {
        return ResourceLocation.fromNamespaceAndPath("ancestral_arcane", name);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider holderLookup) {
        // === CRUDE RUNE T1 ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T1.get())
                .pattern("CGC")
                .pattern("G G")
                .pattern("CGC")
                .define('C', Items.CLAY_BALL)
                .define('G', Items.GLOWSTONE_DUST)
                .unlockedBy("has_clay", inv(Items.CLAY_BALL))
                .save(output, rl("crude_rune_t1"));

        // === CRUDE RUNE T2 ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T2.get())
                .pattern(" Q ").pattern("QCQ").pattern(" Q ")
                .define('Q', Items.QUARTZ).define('C', AncestralArcaneItems.RUNE_CRUDE_T1.get())
                .unlockedBy("has_crude_rune_t1", inv(AncestralArcaneItems.RUNE_CRUDE_T1.get()))
                .save(output, rl("crude_rune_t2"));

        // === CRUDE RUNE T3 ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T3.get())
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .define('A', Items.AMETHYST_SHARD).define('C', AncestralArcaneItems.RUNE_CRUDE_T2.get())
                .unlockedBy("has_crude_rune_t2", inv(AncestralArcaneItems.RUNE_CRUDE_T2.get()))
                .save(output, rl("crude_rune_t3"));

        // === CRUDE RUNE T4 ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T4.get())
                .pattern(" E ").pattern("ECE").pattern(" E ")
                .define('E', Items.EMERALD).define('C', AncestralArcaneItems.RUNE_CRUDE_T3.get())
                .unlockedBy("has_crude_rune_t3", inv(AncestralArcaneItems.RUNE_CRUDE_T3.get()))
                .save(output, rl("crude_rune_t4"));

        // === CRUDE RUNE T5 ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T5.get())
                .pattern(" N ").pattern("NCN").pattern(" N ")
                .define('N', Items.NETHER_STAR).define('C', AncestralArcaneItems.RUNE_CRUDE_T4.get())
                .unlockedBy("has_crude_rune_t4", inv(AncestralArcaneItems.RUNE_CRUDE_T4.get()))
                .save(output, rl("crude_rune_t5"));

        // === SCROLLS ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_FIRE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.RED_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_fire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_FIRE_FRIEND.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.ORANGE_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_fire_friend"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_STORM.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.BLUE_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_storm"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_FROST.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.LIGHT_BLUE_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_frost"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_FROST_WALKER.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.CYAN_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_frost_walker"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_HEAL.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.PINK_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_heal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_BREAKER.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.GRAY_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_breaker"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_BREATHE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.WHITE_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_breathe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_CLEANSE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.LIME_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_cleanse"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_FERTILIZE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.GREEN_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_fertilize"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_LIGHT.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.YELLOW_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_light"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_MEND.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.MAGENTA_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_mend"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_REACH.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.PURPLE_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_reach"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_SILENCE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.BLACK_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_silence"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_STABILIZE.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.BROWN_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_stabilize"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_STONEBIND.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.LIGHT_GRAY_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_stonebind"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.SCROLL_WARD.get())
                .pattern("PIF").pattern(" X ")
                .define('P', Items.PAPER).define('I', Items.INK_SAC).define('F', Items.FEATHER)
                .define('X', Items.CYAN_DYE)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_ward"));

        // === GRIMOIRES ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T1.get())
                .pattern("LLL").pattern("LBL").pattern("LLL")
                .define('L', Items.LEATHER).define('B', Items.BOOK)
                .unlockedBy("has_book", inv(Items.BOOK))
                .save(output, rl("grimoire_t1"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T2.get())
                .pattern("GGG").pattern("GBG").pattern("GGG")
                .define('G', Items.GLOWSTONE_DUST).define('B', AncestralArcaneItems.GRIMOIRE_T1.get())
                .unlockedBy("has_grimoire_t1", inv(AncestralArcaneItems.GRIMOIRE_T1.get()))
                .save(output, rl("grimoire_t2"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T3.get())
                .pattern("QQQ").pattern("QBQ").pattern("QQQ")
                .define('Q', Items.QUARTZ).define('B', AncestralArcaneItems.GRIMOIRE_T2.get())
                .unlockedBy("has_grimoire_t2", inv(AncestralArcaneItems.GRIMOIRE_T2.get()))
                .save(output, rl("grimoire_t3"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T4.get())
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .define('A', Items.AMETHYST_SHARD).define('B', AncestralArcaneItems.GRIMOIRE_T3.get())
                .unlockedBy("has_grimoire_t3", inv(AncestralArcaneItems.GRIMOIRE_T3.get()))
                .save(output, rl("grimoire_t4"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T5.get())
                .pattern("EEE").pattern("EBE").pattern("EEE")
                .define('E', Items.ECHO_SHARD).define('B', AncestralArcaneItems.GRIMOIRE_T4.get())
                .unlockedBy("has_grimoire_t4", inv(AncestralArcaneItems.GRIMOIRE_T4.get()))
                .save(output, rl("grimoire_t5"));

        // === WANDS ===
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.FLINT_WAND.get())
                .pattern("  F").pattern(" S ").pattern("S  ")
                .define('F', Items.FLINT).define('S', Items.STICK)
                .unlockedBy("has_flint", inv(Items.FLINT))
                .save(output, rl("flint_wand"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.COPPER_WAND.get())
                .pattern("  C").pattern(" S ").pattern("S  ")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .unlockedBy("has_copper", inv(Items.COPPER_INGOT))
                .save(output, rl("copper_wand"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.IRON_WAND.get())
                .pattern("  I").pattern(" S ").pattern("S  ")
                .define('I', Items.IRON_INGOT).define('S', Items.STICK)
                .unlockedBy("has_iron", inv(Items.IRON_INGOT))
                .save(output, rl("iron_wand"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.GOLDEN_WAND.get())
                .pattern("  G").pattern(" S ").pattern("S  ")
                .define('G', Items.GOLD_INGOT).define('S', Items.STICK)
                .unlockedBy("has_gold", inv(Items.GOLD_INGOT))
                .save(output, rl("golden_wand"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.DIAMOND_WAND.get())
                .pattern("  D").pattern(" S ").pattern("S  ")
                .define('D', Items.DIAMOND).define('S', Items.STICK)
                .unlockedBy("has_diamond", inv(Items.DIAMOND))
                .save(output, rl("diamond_wand"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.EMERALD_WAND.get())
                .pattern("  E").pattern(" S ").pattern("S  ")
                .define('E', Items.EMERALD).define('S', Items.STICK)
                .unlockedBy("has_emerald", inv(Items.EMERALD))
                .save(output, rl("emerald_wand"));

        // Netherite Wand requires Smithing Table upgrades natively, but for now we
        // provide a shaped one:
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AncestralArcaneItems.NETHERITE_WAND.get())
                .pattern("  N").pattern(" S ").pattern("S  ")
                .define('N', Items.NETHERITE_INGOT).define('S', Items.STICK)
                .unlockedBy("has_netherite", inv(Items.NETHERITE_INGOT))
                .save(output, rl("netherite_wand"));

        // === LEATHER GRIP WANDS ===
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.FLINT_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.FLINT_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_flint_wand", inv(AncestralArcaneItems.FLINT_WAND.get()))
                .save(output, rl("flint_wand_leather_grip"));
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.COPPER_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.COPPER_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_copper_wand", inv(AncestralArcaneItems.COPPER_WAND.get()))
                .save(output, rl("copper_wand_leather_grip"));
                
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.IRON_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.IRON_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_iron_wand", inv(AncestralArcaneItems.IRON_WAND.get()))
                .save(output, rl("iron_wand_leather_grip"));
                
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.GOLDEN_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.GOLDEN_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_golden_wand", inv(AncestralArcaneItems.GOLDEN_WAND.get()))
                .save(output, rl("golden_wand_leather_grip"));
                
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.DIAMOND_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.DIAMOND_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_diamond_wand", inv(AncestralArcaneItems.DIAMOND_WAND.get()))
                .save(output, rl("diamond_wand_leather_grip"));
                
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.EMERALD_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.EMERALD_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_emerald_wand", inv(AncestralArcaneItems.EMERALD_WAND.get()))
                .save(output, rl("emerald_wand_leather_grip"));
                
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AncestralArcaneItems.NETHERITE_WAND_LEATHER_GRIP.get())
                .requires(AncestralArcaneItems.NETHERITE_WAND.get()).requires(Items.LEATHER)
                .unlockedBy("has_netherite_wand", inv(AncestralArcaneItems.NETHERITE_WAND.get()))
                .save(output, rl("netherite_wand_leather_grip"));

        // === BLOCKS ===
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AncestralArcaneItems.ARCANE_SMITHING_TABLE.get())
                .pattern("RR ").pattern("WW ").pattern("WW ")
                .define('R', AncestralArcaneItems.RUNE.get())
                .define('W', net.minecraft.tags.ItemTags.PLANKS)
                .unlockedBy("has_rune", inv(AncestralArcaneItems.RUNE.get()))
                .save(output, rl("arcane_smithing_table"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AncestralArcaneItems.HOME_ANCHOR.get())
                .pattern(" R ").pattern("RER").pattern(" R ")
                .define('R', AncestralArcaneItems.RUNE.get())
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_rune", inv(AncestralArcaneItems.RUNE.get()))
                .save(output, rl("home_anchor"));
    }
}
