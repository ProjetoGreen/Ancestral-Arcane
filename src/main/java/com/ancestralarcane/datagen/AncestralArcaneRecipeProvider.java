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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE.get())
                .pattern("CGC")
                .pattern("G G")
                .pattern("CGC")
                .define('C', Items.CLAY_BALL)
                .define('G', Items.GLOWSTONE_DUST)
                .unlockedBy("has_clay", inv(Items.CLAY_BALL))
                .save(output, rl("crude_rune_t1"));

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
    }
}
