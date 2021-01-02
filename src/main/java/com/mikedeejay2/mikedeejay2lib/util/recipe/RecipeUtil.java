package com.mikedeejay2.mikedeejay2lib.util.recipe;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class RecipeUtil
{
    private static Set<Material> blastingInputs = new HashSet<>();
    private static Set<Material> campfireInputs = new HashSet<>();
    private static Set<Material> furnaceInputs = new HashSet<>();
    private static Set<Material> cookingInputs = new HashSet<>();
    private static Set<Material> merchantInputs = new HashSet<>();
    private static Set<Material> shapedInputs = new HashSet<>();
    private static Set<Material> shapelessInputs = new HashSet<>();
    private static Set<Material> smithingInputs = new HashSet<>();
    private static Set<Material> smokingInputs = new HashSet<>();
    private static Set<Material> stonecuttingInputs = new HashSet<>();
    private static Set<Material> blastingResults = new HashSet<>();
    private static Set<Material> campfireResults = new HashSet<>();
    private static Set<Material> furnaceResults = new HashSet<>();
    private static Set<Material> cookingResults = new HashSet<>();
    private static Set<Material> merchantResults = new HashSet<>();
    private static Set<Material> shapedResults = new HashSet<>();
    private static Set<Material> shapelessResults = new HashSet<>();
    private static Set<Material> smithingResults = new HashSet<>();
    private static Set<Material> smokingResults = new HashSet<>();
    private static Set<Material> stonecuttingResults = new HashSet<>();
    private static boolean preloaded = false;

    public static boolean isPreloaded()
    {
        return preloaded;
    }

    public static void preload(PluginBase plugin, long delay)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                blastingInputs.clear();
                campfireInputs.clear();
                furnaceInputs.clear();
                cookingInputs.clear();
                merchantInputs.clear();
                shapedInputs.clear();
                shapelessInputs.clear();
                smithingInputs.clear();
                smokingInputs.clear();
                stonecuttingInputs.clear();
                blastingResults.clear();
                campfireResults.clear();
                furnaceResults.clear();
                cookingResults.clear();
                merchantResults.clear();
                shapedResults.clear();
                shapelessResults.clear();
                smithingResults.clear();
                smokingResults.clear();
                stonecuttingResults.clear();
                Iterator<Recipe> iterator = Bukkit.recipeIterator();
                while(iterator.hasNext())
                {
                    Recipe recipe = iterator.next();
                    if(recipe instanceof BlastingRecipe)
                    {
                        BlastingRecipe castRecipe = (BlastingRecipe) recipe;
                        blastingInputs.add(castRecipe.getInput().getType());
                        blastingResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof CampfireRecipe)
                    {
                        CampfireRecipe castRecipe = (CampfireRecipe) recipe;
                        campfireInputs.add(castRecipe.getInput().getType());
                        campfireResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof FurnaceRecipe)
                    {
                        FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
                        furnaceInputs.add(castRecipe.getInput().getType());
                        furnaceResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof SmokingRecipe)
                    {
                        SmokingRecipe castRecipe = (SmokingRecipe) recipe;
                        smokingInputs.add(castRecipe.getInput().getType());
                        smokingResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof MerchantRecipe)
                    {
                        MerchantRecipe castRecipe = (MerchantRecipe) recipe;
                        castRecipe.getIngredients().forEach(ingredient -> {
                            if(ingredient != null) merchantInputs.add(ingredient.getType());
                        });
                        merchantResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof ShapedRecipe)
                    {
                        ShapedRecipe castRecipe = (ShapedRecipe) recipe;
                        castRecipe.getIngredientMap().values().forEach(ingredient -> {
                            if(ingredient != null) shapedInputs.add(ingredient.getType());
                        });
                        shapedResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof ShapelessRecipe)
                    {
                        ShapelessRecipe castRecipe = (ShapelessRecipe) recipe;
                        castRecipe.getIngredientList().forEach(ingredient -> {
                            if(ingredient != null) shapelessInputs.add(ingredient.getType());
                        });
                        shapelessResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof SmithingRecipe)
                    {
                        SmithingRecipe castRecipe = (SmithingRecipe) recipe;
                        smithingInputs.add(castRecipe.getBase().getItemStack().getType());
                        smithingResults.add(castRecipe.getResult().getType());
                    }
                    else if(recipe instanceof StonecuttingRecipe)
                    {
                        StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
                        stonecuttingInputs.add(castRecipe.getInput().getType());
                        stonecuttingResults.add(castRecipe.getResult().getType());
                    }
                }
                preloaded = true;
            }
        }.runTaskLaterAsynchronously(plugin, delay);
    }

    public static boolean isBlastingInput(Material material)
    {
        if(preloaded)
        {
            return blastingInputs.contains(material);
        }
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
        if(preloaded)
        {
            return campfireInputs.contains(material);
        }
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
        if(preloaded)
        {
            return furnaceInputs.contains(material);
        }
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
        if(preloaded)
        {
            return cookingInputs.contains(material);
        }
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
        if(preloaded)
        {
            return merchantInputs.contains(material);
        }
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
        if(preloaded)
        {
            return shapedInputs.contains(material);
        }
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
        if(preloaded)
        {
            return shapelessInputs.contains(material);
        }
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
        if(preloaded)
        {
            return smithingInputs.contains(material);
        }
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
        if(preloaded)
        {
            return smokingInputs.contains(material);
        }
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
        if(preloaded)
        {
            return stonecuttingInputs.contains(material);
        }
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
        if(preloaded)
        {
            return blastingResults.contains(material);
        }
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
        if(preloaded)
        {
            return campfireResults.contains(material);
        }
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
        if(preloaded)
        {
            return furnaceResults.contains(material);
        }
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
        if(preloaded)
        {
            return cookingResults.contains(material);
        }
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
        if(preloaded)
        {
            return merchantResults.contains(material);
        }
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
        if(preloaded)
        {
            return shapedResults.contains(material);
        }
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
        if(preloaded)
        {
            return shapelessResults.contains(material);
        }
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

    public static boolean isSmithingResult(Material material)
    {
        if(preloaded)
        {
            return smithingResults.contains(material);
        }
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
        if(preloaded)
        {
            return smokingResults.contains(material);
        }
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
        if(preloaded)
        {
            return stonecuttingResults.contains(material);
        }
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
