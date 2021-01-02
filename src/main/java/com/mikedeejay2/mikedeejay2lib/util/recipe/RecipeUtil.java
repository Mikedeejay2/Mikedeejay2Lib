package com.mikedeejay2.mikedeejay2lib.util.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecipeUtil
{
    public static boolean isBlastingInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof BlastingRecipe)) continue;
            BlastingRecipe castRecipe = (BlastingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    public static boolean isCampfireInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CampfireRecipe)) continue;
            CampfireRecipe castRecipe = (CampfireRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    public static boolean isFurnaceInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof FurnaceRecipe)) continue;
            FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    public static boolean isCookingInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CookingRecipe)) continue;
            CookingRecipe castRecipe = (CookingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    public static boolean isMerchantInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof MerchantRecipe)) continue;
            MerchantRecipe  castRecipe  = (MerchantRecipe) recipe;
            List<ItemStack> ingredients = castRecipe.getIngredients();
            for(ItemStack ingredient : ingredients)
            {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    public static boolean isShapedInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapedRecipe)) continue;
            ShapedRecipe castRecipe  = (ShapedRecipe) recipe;
            Map<Character, ItemStack> ingredients = castRecipe.getIngredientMap();
            for(ItemStack ingredient : ingredients.values())
            {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    public static boolean isShapelessInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapelessRecipe)) continue;
            ShapelessRecipe castRecipe  = (ShapelessRecipe) recipe;
            List<ItemStack> ingredients = castRecipe.getIngredientList();
            for(ItemStack ingredient : ingredients)
            {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    public static boolean isSmithingInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmithingRecipe)) continue;
            SmithingRecipe castRecipe = (SmithingRecipe) recipe;
            if(castRecipe.getBase().getItemStack().getType() == material) return true;
        }
        return false;
    }

    public static boolean isSmokingInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmokingRecipe)) continue;
            SmokingRecipe castRecipe = (SmokingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    public static boolean isStoneCuttingInput(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof StonecuttingRecipe)) continue;
            StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }












































    public static boolean isBlastingResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof BlastingRecipe)) continue;
            BlastingRecipe castRecipe = (BlastingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isCampfireResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CampfireRecipe)) continue;
            CampfireRecipe castRecipe = (CampfireRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isFurnaceResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof FurnaceRecipe)) continue;
            FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isCookingResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CookingRecipe)) continue;
            CookingRecipe castRecipe = (CookingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isMerchantResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof MerchantRecipe)) continue;
            MerchantRecipe  castRecipe  = (MerchantRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isShapedResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapedRecipe)) continue;
            ShapedRecipe castRecipe  = (ShapedRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isShapelessResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapelessRecipe)) continue;
            ShapelessRecipe castRecipe  = (ShapelessRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isSmithingRecipe(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmithingRecipe)) continue;
            SmithingRecipe castRecipe = (SmithingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isSmokingResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmokingRecipe)) continue;
            SmokingRecipe castRecipe = (SmokingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    public static boolean isStoneCuttingResult(Material material)
    {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext())
        {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof StonecuttingRecipe)) continue;
            StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }
}
