package org.neocities.zincoctober.toolracklib;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.PackOutput;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.data.recipes.RecipeCategory;

public class TRLRecipeProvider extends RecipeProvider {

    protected TRLRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.ACACIA_BOAT)

        .pattern("X X")
        .pattern(" # ")
        .pattern(" # ")

        .define('X', Items.IRON_INGOT)
        .define('#', Items.STICK)

        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))

        .save(output);
    }
}
