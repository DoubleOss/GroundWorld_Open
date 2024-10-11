package com.doubleos.gw.handler;


import com.doubleos.gw.automine.table.AutoMineItemTable;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.blockInterface.IObjModel;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.item.ItemHelmet;
import com.doubleos.gw.util.CustomRecipe;
import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber
public class RegistryHandler
{



    @SubscribeEvent
    public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event)
    {
        GroundItemStack.initItem();

        IForgeRegistryModifiable<IRecipe> modRegistry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();

        Set<IRecipe> toRemove = new HashSet<>();
        Set<IRecipe> toAdd = new HashSet<>();

        for (IRecipe recipe : modRegistry) {
            if (recipe.getRegistryName().toString().contains("minecraft:"))
            {
                if (!recipe.getRegistryName().toString().contains("iron_leggings") &&
                        !recipe.getRegistryName().toString().contains("iron_chestplate") &&
                        !recipe.getRegistryName().toString().contains("diamond_sword") &&
                        !recipe.getRegistryName().toString().contains("lapis_block") &&
                        !recipe.getRegistryName().toString().contains("redstone_block") &&
                        !recipe.getRegistryName().toString().contains("diamond_block") &&
                        !recipe.getRegistryName().toString().contains("iron_block") &&
                        !recipe.getRegistryName().toString().contains("emerald_block") &&
                        !recipe.getRegistryName().toString().contains("stick") &&
                        !recipe.getRegistryName().toString().contains("coal_block") &&
                        !recipe.getRegistryName().toString().contains("oak_planks")  )
                {
                    toRemove.add(recipe);
                }
            }
            else if (recipe.getRegistryName().toString().contains("groundworld:"))
            {
                ItemStack newOutput = GroundItemStack.hashItemToItemStack.getOrDefault(recipe.getRecipeOutput().getItem(), ItemStack.EMPTY);
                if (!newOutput.isEmpty())
                {
                    NonNullList<Ingredient> newIngredients = NonNullList.create();
                    for (Ingredient ingredient : recipe.getIngredients())
                    {
                        boolean check = false;
                        for (Map.Entry<Item, ItemStack> entry : GroundItemStack.hashItemToItemStack.entrySet())
                        {
                            if (ingredient.apply(new ItemStack(entry.getKey())))
                            {
                                check = true;
                                newIngredients.add(Ingredient.fromStacks(entry.getValue()));
                                break;
                            }
                        }
                        if (!check)
                            newIngredients.add(ingredient);
                    }
                    CustomRecipe customRecipe = new CustomRecipe(recipe, newOutput, newIngredients);
                    toAdd.add(customRecipe);
                }
            }

        }

        for (IRecipe recipe : toRemove) {
            modRegistry.remove(recipe.getRegistryName());
        }

        for (IRecipe recipe : toAdd) {
            modRegistry.register(recipe);
        }
    }
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        for (int i = 0; i < ModItems.ITEMS.size(); i++)
        {
            event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[i]));
        }

    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        for (int i = 0; i < ModBlocks.BLOCKS.size(); i++)
        {
            event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[i]));
        }

    }



    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item : ModItems.ITEMS)
        {

            if(item instanceof IHasModel)
            {
                ((IHasModel) item).registerModels();
                if (item instanceof IObjModel)
                {
                    ((IObjModel) item).initModel();
                }
                else
                {
                    ((IHasModel) item).registerModels();
                }

            }
        }
        for(Block block : ModBlocks.BLOCKS)
        {
            if(block instanceof IHasModel)
            {
                ((IHasModel) block).registerModels();
                if (block instanceof IObjModel)
                {
                    ((IObjModel) block).initModel();
                }
            }
        }



    }

}
