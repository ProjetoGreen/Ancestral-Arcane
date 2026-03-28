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

        // === 1. BLOCKS & FRAGMENTS ===

        // Fragment of All Knowledge
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get())
                .pattern("GLG")
                .pattern("LBL")
                .pattern("ARA")
                .define('G', Items.GLOWSTONE_DUST)
                .define('L', Items.LAPIS_LAZULI)
                .define('B', AncestralArcaneItems.GRIMOIRE_T4.get())
                .define('A', Items.AMETHYST_SHARD)
                .define('R', AncestralArcaneItems.RUNE.get())
                .unlockedBy("has_grimoire_t4", inv(AncestralArcaneItems.GRIMOIRE_T4.get()))
                .save(output, rl("fragment_of_all_knowledge"));

        // Arcane Smithing Table
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AncestralArcaneItems.ARCANE_SMITHING_TABLE.get())
                .pattern("RFR")
                .pattern("WWW")
                .pattern("WPW")
                .define('R', AncestralArcaneItems.RUNE.get())
                .define('F', AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get())
                .define('W', net.minecraft.tags.ItemTags.PLANKS)
                .define('P', Items.ENDER_PEARL)
                .unlockedBy("has_fragment", inv(AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get()))
                .save(output, rl("arcane_smithing_table"));

        // Home Anchor
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AncestralArcaneItems.HOME_ANCHOR.get())
                .pattern(" R ")
                .pattern("RER")
                .pattern(" R ")
                .define('R', AncestralArcaneItems.RUNE.get())
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_p_rune", inv(AncestralArcaneItems.RUNE.get()))
                .save(output, rl("home_anchor"));


        // === 2. CRUDE RUNES ===

        // T1: Clay-Amethyst-Clay / Lapis-Empty-Lapis / Clay-Amethyst-Clay
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T1.get())
                .pattern("CAC")
                .pattern("L L")
                .pattern("CAC")
                .define('C', Items.CLAY_BALL)
                .define('A', Items.AMETHYST_SHARD)
                .define('L', Items.LAPIS_LAZULI)
                .unlockedBy("has_clay", inv(Items.CLAY_BALL))
                .save(output, rl("crude_rune_t1"));

        // T2: Q G Q / G C G / Q empty Q (using C = Crude T1 as in Wiki)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T2.get())
                .pattern("QGQ")
                .pattern("GCG")
                .pattern("Q Q")
                .define('Q', Items.QUARTZ)
                .define('G', Items.GLOWSTONE_DUST)
                .define('C', AncestralArcaneItems.RUNE_CRUDE_T1.get())
                .unlockedBy("has_crude_t1", inv(AncestralArcaneItems.RUNE_CRUDE_T1.get()))
                .save(output, rl("crude_rune_t2"));

        // T3: A E A / E C E / A empty A (C = T2)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T3.get())
                .pattern("AEA")
                .pattern("ECE")
                .pattern("A A")
                .define('A', Items.AMETHYST_SHARD)
                .define('E', Items.ECHO_SHARD)
                .define('C', AncestralArcaneItems.RUNE_CRUDE_T2.get())
                .unlockedBy("has_crude_t2", inv(AncestralArcaneItems.RUNE_CRUDE_T2.get()))
                .save(output, rl("crude_rune_t3"));

        // T4: M P M / P C P / M empty M (M=Emerald, P=Ender Pearl, C=T3)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T4.get())
                .pattern("MPM")
                .pattern("PCP")
                .pattern("M M")
                .define('M', Items.EMERALD)
                .define('P', Items.ENDER_PEARL)
                .define('C', AncestralArcaneItems.RUNE_CRUDE_T3.get())
                .unlockedBy("has_crude_t3", inv(AncestralArcaneItems.RUNE_CRUDE_T3.get()))
                .save(output, rl("crude_rune_t4"));

        // T5: Y D Y / D C D / Y empty Y (Y=Eye of Ender, D=Dragon Breath, C=T4)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.RUNE_CRUDE_T5.get())
                .pattern("YDY")
                .pattern("DCD")
                .pattern("Y Y")
                .define('Y', Items.ENDER_EYE)
                .define('D', Items.DRAGON_BREATH)
                .define('C', AncestralArcaneItems.RUNE_CRUDE_T4.get())
                .unlockedBy("has_crude_t4", inv(AncestralArcaneItems.RUNE_CRUDE_T4.get()))
                .save(output, rl("crude_rune_t5"));


        // === 3. SCROLLS (Generic Layout) ===
        // Pattern: P M F / D I D / _ M _ (P=Paper, M=Material, F=Feather, D=Dye, I=Ink Sac)

        registerScroll(output, AncestralArcaneItems.SCROLL_FIRE.get(), Items.BLAZE_POWDER, Items.RED_DYE, "fire");
        registerScroll(output, AncestralArcaneItems.SCROLL_FIRE_FRIEND.get(), Items.BLAZE_POWDER, Items.ORANGE_DYE, Items.GHAST_TEAR, "fire_friend");
        registerScroll(output, AncestralArcaneItems.SCROLL_STORM.get(), Items.QUARTZ, Items.BLUE_DYE, "storm");
        registerScroll(output, AncestralArcaneItems.SCROLL_FROST.get(), Items.AMETHYST_SHARD, Items.LIGHT_BLUE_DYE, Items.PRISMARINE_SHARD, "frost");
        registerScroll(output, AncestralArcaneItems.SCROLL_FROST_WALKER.get(), Items.PRISMARINE_SHARD, Items.CYAN_DYE, Items.AMETHYST_SHARD, "frost_walker");
        registerScroll(output, AncestralArcaneItems.SCROLL_HEAL.get(), Items.GHAST_TEAR, Items.PINK_DYE, "heal");
        registerScroll(output, AncestralArcaneItems.SCROLL_BREAKER.get(), Items.FLINT, Items.GRAY_DYE, Items.QUARTZ, "breaker");
        registerScroll(output, AncestralArcaneItems.SCROLL_BREATHE.get(), Items.PRISMARINE_SHARD, Items.WHITE_DYE, Items.HEART_OF_THE_SEA, "breathe");
        registerScroll(output, AncestralArcaneItems.SCROLL_CLEANSE.get(), Items.GLOW_INK_SAC, Items.LIME_DYE, Items.GHAST_TEAR, "cleanse");
        registerScroll(output, AncestralArcaneItems.SCROLL_FERTILIZE.get(), Items.BONE_MEAL, Items.GREEN_DYE, "fertilize");
        registerScroll(output, AncestralArcaneItems.SCROLL_LIGHT.get(), Items.GLOWSTONE_DUST, Items.YELLOW_DYE, Items.GLOW_INK_SAC, "light");
        registerScroll(output, AncestralArcaneItems.SCROLL_MEND.get(), Items.AMETHYST_SHARD, Items.MAGENTA_DYE, Items.IRON_INGOT, "mend");
        registerScroll(output, AncestralArcaneItems.SCROLL_REACH.get(), Items.ENDER_PEARL, Items.PURPLE_DYE, "reach");
        registerScroll(output, AncestralArcaneItems.SCROLL_SILENCE.get(), Items.GUNPOWDER, Items.BLACK_DYE, "silence");
        registerScroll(output, AncestralArcaneItems.SCROLL_STABILIZE.get(), Items.AMETHYST_SHARD, Items.BROWN_DYE, Items.QUARTZ, "stabilize");
        registerScroll(output, AncestralArcaneItems.SCROLL_STONEBIND.get(), Items.FLINT, Items.LIGHT_GRAY_DYE, Items.QUARTZ, "stonebind");
        registerScroll(output, AncestralArcaneItems.SCROLL_WARD.get(), Items.AMETHYST_SHARD, Items.CYAN_DYE, Items.QUARTZ, "ward");


        // === 4. GRIMOIRES ===

        // T1: P F P / L B L / P I P (Paper-Feather-Paper / Leather-Book-Leather / Paper-Ink-Paper)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AncestralArcaneItems.GRIMOIRE_T1.get())
                .pattern("PFP")
                .pattern("LBL")
                .pattern("PIP")
                .define('P', Items.PAPER)
                .define('F', Items.FEATHER)
                .define('L', Items.LEATHER)
                .define('B', Items.BOOK)
                .define('I', Items.INK_SAC)
                .unlockedBy("has_book", inv(Items.BOOK))
                .save(output, rl("grimoire_t1"));

        // Pattern for T2-T5: P M P / X G X / P M P (M = Material, G = Prev Grimoire, X = Side material)
        // T2: Paper-Glow-Paper / Lapis-T1-Lapis / Paper-Glow-Paper
        registerGrimoireUpgrade(output, AncestralArcaneItems.GRIMOIRE_T2.get(), AncestralArcaneItems.GRIMOIRE_T1.get(), Items.PAPER, Items.GLOWSTONE_DUST, Items.LAPIS_LAZULI, "t2");
        
        // T3: Quartz-Amethyst-Quartz / GlowInk-T2-GlowInk / Quartz-Amethyst-Quartz
        registerGrimoireUpgrade(output, AncestralArcaneItems.GRIMOIRE_T3.get(), AncestralArcaneItems.GRIMOIRE_T2.get(), Items.QUARTZ, Items.AMETHYST_SHARD, Items.GLOW_INK_SAC, "t3");

        // T4: Emerald-Echo-Emerald / WritableBook-T3-WritableBook / Emerald-Echo-Emerald
        registerGrimoireUpgrade(output, AncestralArcaneItems.GRIMOIRE_T4.get(), AncestralArcaneItems.GRIMOIRE_T3.get(), Items.EMERALD, Items.ECHO_SHARD, Items.WRITABLE_BOOK, "t4");

        // T5: Eye-DragonBreath-Eye / WrittenBook-T4-WrittenBook / Eye-DragonBreath-Eye
        registerGrimoireUpgrade(output, AncestralArcaneItems.GRIMOIRE_T5.get(), AncestralArcaneItems.GRIMOIRE_T4.get(), Items.ENDER_EYE, Items.DRAGON_BREATH, Items.WRITTEN_BOOK, "t5");


        // === 5. CATALYSTS (New System) ===
        // Pattern: A X A / B A C / K S S (A = Main Material, X = Empty, B = Aux, C = Aux, K = Aux, S = Rune/Aux)
        // Correcting according to Wiki Pattern: A X A / B A C / K L S (A=Catalyst Material, L=Rune, Others=Aux)

        registerCatalyst(output, AncestralArcaneItems.FLINT_CATALYST.get(), Items.FLINT, Items.BOOK, Items.BONE, Items.BONE, AncestralArcaneItems.RUNE_T1.get(), Items.BONE, "flint");
        registerCatalyst(output, AncestralArcaneItems.COPPER_CATALYST.get(), Items.COPPER_INGOT, Items.AMETHYST_SHARD, Items.BOOK, Items.BONE, AncestralArcaneItems.RUNE_T2.get(), Items.BONE, "copper");
        registerCatalyst(output, AncestralArcaneItems.IRON_CATALYST.get(), Items.IRON_INGOT, Items.AMETHYST_SHARD, Items.BOOK, Items.ECHO_SHARD, AncestralArcaneItems.RUNE_T3.get(), Items.BLAZE_ROD, "iron");
        registerCatalyst(output, AncestralArcaneItems.GOLD_CATALYST.get(), Items.GOLD_INGOT, Items.AMETHYST_SHARD, Items.BOOK, Items.ECHO_SHARD, AncestralArcaneItems.RUNE_T4.get(), Items.BLAZE_ROD, "gold");
        registerCatalyst(output, AncestralArcaneItems.DIAMOND_CATALYST.get(), Items.DIAMOND, Items.AMETHYST_SHARD, Items.ENDER_EYE, Items.DRAGON_BREATH, AncestralArcaneItems.RUNE_T5.get(), Items.BLAZE_ROD, "diamond");
        registerCatalyst(output, AncestralArcaneItems.EMERALD_CATALYST.get(), Items.EMERALD, Items.GHAST_TEAR, Items.ENDER_EYE, Items.DRAGON_BREATH, AncestralArcaneItems.RUNE_T5.get(), Items.BLAZE_ROD, "emerald");
        registerCatalyst(output, AncestralArcaneItems.NETHERITE_CATALYST.get(), Items.NETHERITE_INGOT, Items.ECHO_SHARD, Items.ENDER_EYE, Items.DRAGON_BREATH, AncestralArcaneItems.RUNE_T5.get(), Items.BLAZE_ROD, "netherite");


        // === 6. WANDS (Using Catalysts) ===
        // Pattern: _ _ C / _ S _ / S _ _ (C = Catalyst, S = Stick)
        
        registerWand(output, AncestralArcaneItems.FLINT_WAND.get(), AncestralArcaneItems.FLINT_CATALYST.get(), "flint");
        registerWand(output, AncestralArcaneItems.COPPER_WAND.get(), AncestralArcaneItems.COPPER_CATALYST.get(), "copper");
        registerWand(output, AncestralArcaneItems.IRON_WAND.get(), AncestralArcaneItems.IRON_CATALYST.get(), "iron");
        registerWand(output, AncestralArcaneItems.GOLDEN_WAND.get(), AncestralArcaneItems.GOLD_CATALYST.get(), "golden");
        registerWand(output, AncestralArcaneItems.DIAMOND_WAND.get(), AncestralArcaneItems.DIAMOND_CATALYST.get(), "diamond");
        registerWand(output, AncestralArcaneItems.EMERALD_WAND.get(), AncestralArcaneItems.EMERALD_CATALYST.get(), "emerald");
        registerWand(output, AncestralArcaneItems.NETHERITE_WAND.get(), AncestralArcaneItems.NETHERITE_CATALYST.get(), "netherite");

        // Leather Grip wands are now ONLY in Smithing Table. Removing Crafting recipes.
    }

    private void registerScroll(RecipeOutput output, ItemLike result, ItemLike mat, ItemLike dye, String name) {
        registerScroll(output, result, mat, dye, mat, name);
    }

    private void registerScroll(RecipeOutput output, ItemLike result, ItemLike mat1, ItemLike dye, ItemLike mat2, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("PMF")
                .pattern("DID")
                .pattern(" X ")
                .define('P', Items.PAPER)
                .define('M', mat1)
                .define('F', Items.FEATHER)
                .define('D', dye)
                .define('I', Items.INK_SAC)
                .define('X', mat2)
                .unlockedBy("has_paper", inv(Items.PAPER))
                .save(output, rl("scroll_" + name));
    }

    private void registerGrimoireUpgrade(RecipeOutput output, ItemLike result, ItemLike prev, ItemLike matTop, ItemLike matCenterTop, ItemLike side, String tag) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("MCM")
                .pattern("SGS")
                .pattern("MCM")
                .define('M', matTop)
                .define('C', matCenterTop)
                .define('S', side)
                .define('G', prev)
                .unlockedBy("has_previous_grimoire", inv(prev))
                .save(output, rl("grimoire_" + tag));
    }

    private void registerCatalyst(RecipeOutput output, ItemLike result, ItemLike main, ItemLike auxB, ItemLike auxC, ItemLike auxK, ItemLike rune, ItemLike auxS, String tag) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("A A")
                .pattern("BAC")
                .pattern("KLS")
                .define('A', main)
                .define('B', auxB)
                .define('C', auxC)
                .define('K', auxK)
                .define('L', rune)
                .define('S', auxS)
                .unlockedBy("has_material", inv(main))
                .save(output, rl(tag + "_catalyst"));
    }

    private void registerWand(RecipeOutput output, ItemLike result, ItemLike catalyst, String tag) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('C', catalyst)
                .define('S', Items.STICK)
                .unlockedBy("has_catalyst", inv(catalyst))
                .save(output, rl(tag + "_wand"));
    }
}
