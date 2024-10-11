package com.doubleos.gw.util;

import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CustomRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private final IRecipe originalRecipe;
    private final ItemStack customOutput;

    private final NonNullList<Ingredient> ingredients;

    public CustomRecipe(IRecipe originalRecipe, ItemStack customOutput, NonNullList<Ingredient> ingredients) {
        this.originalRecipe = originalRecipe;
        this.customOutput = customOutput;
        this.ingredients = ingredients != null ? ingredients : originalRecipe.getIngredients();
        this.setRegistryName(originalRecipe.getRegistryName());

    }
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        NonNullList<ItemStack> inputs = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        // Copy inventory contents
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            inputs.set(i, inv.getStackInSlot(i).copy());
        }

        // Check if all ingredients match
        for (Ingredient ingredient : ingredients) {
            boolean matched = false;
            for (int i = 0; i < inputs.size(); i++) {
                if (ingredient.apply(inputs.get(i))) {
                    inputs.set(i, ItemStack.EMPTY); // Clear the matched stack to avoid reuse
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }

        // Ensure there are no extra items in the crafting grid
        for (ItemStack stack : inputs) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return customOutput.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return originalRecipe.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return customOutput;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return originalRecipe.getRemainingItems(inv);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}
