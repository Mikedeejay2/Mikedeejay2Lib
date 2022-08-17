package com.mikedeejay2.mikedeejay2lib.util.recipe;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class RecipeUtil {
    // Internal lists that store all crafting inputs and outputs if using preload

    /**
     * Internal list for storing all blast furnace inputs
     */
    private static final HashSet<Material> blastingInputs = new HashSet<>();

    /**
     * Internal list for storing all campfire inputs
     */
    private static final HashSet<Material> campfireInputs = new HashSet<>();

    /**
     * Internal list for storing all furnace inputs
     */
    private static final HashSet<Material> furnaceInputs = new HashSet<>();

    /**
     * Internal list for storing all cooking inputs
     */
    private static final HashSet<Material> cookingInputs = new HashSet<>();

    /**
     * Internal list for storing all merchant inputs
     */
    private static final HashSet<Material> merchantInputs = new HashSet<>();

    /**
     * Internal list for storing all shaped recipe inputs
     */
    private static final HashSet<Material> shapedInputs = new HashSet<>();

    /**
     * Internal list for storing all shapeless recipe inputs
     */
    private static final HashSet<Material> shapelessInputs = new HashSet<>();

    /**
     * Internal list for storing all smithing table inputs
     */
    private static final HashSet<Material> smithingInputs = new HashSet<>();

    /**
     * Internal list for storing all smoker inputs
     */
    private static final HashSet<Material> smokingInputs = new HashSet<>();

    /**
     * Internal list for storing all stone cutter inputs
     */
    private static final HashSet<Material> stonecuttingInputs = new HashSet<>();

    /**
     * Internal list for storing all blast furnace results
     */
    private static final HashSet<Material> blastingResults = new HashSet<>();

    /**
     * Internal list for storing all campfire results
     */
    private static final HashSet<Material> campfireResults = new HashSet<>();

    /**
     * Internal list for storing all furnace results
     */
    private static final HashSet<Material> furnaceResults = new HashSet<>();

    /**
     * Internal list for storing all cooking results
     */
    private static final HashSet<Material> cookingResults = new HashSet<>();

    /**
     * Internal list for storing all merchant results
     */
    private static final HashSet<Material> merchantResults = new HashSet<>();

    /**
     * Internal list for storing all shaped recipe results
     */
    private static final HashSet<Material> shapedResults = new HashSet<>();

    /**
     * Internal list for storing all shapeless recipe results
     */
    private static final HashSet<Material> shapelessResults = new HashSet<>();

    /**
     * Internal list for storing all smithing table results
     */
    private static final HashSet<Material> smithingResults = new HashSet<>();

    /**
     * Internal list for storing all smoker results
     */
    private static final HashSet<Material> smokingResults = new HashSet<>();

    /**
     * Internal list for storing all stone cutter results
     */
    private static final HashSet<Material> stonecuttingResults = new HashSet<>();

    /**
     * Whether RecipeUtil has been preloaded or not
     */
    private static boolean preloaded = false;

    /**
     * Get whether RecipeUtil has been preloaded or not.
     * <p>
     * Preloading is the act of prematurely organizing all crafting recipes for much more efficient
     * access later on. The downside to preloading is that any recipes added after the preload won't
     * be listed through RecipeUtil until preload is called again.
     * <p>
     * Preload takes an average of 6ms, but runs asynchronously.
     *
     * @return Whether RecipeUtil is preloaded or not
     */
    public static boolean isPreloaded() {
        return preloaded;
    }

    /**
     * Preload this <code>RecipeUtil</code>.
     * <p>
     * Preloading is the act of prematurely organizing all crafting recipes for much more efficient
     * access later on. The downside to preloading is that any recipes added after the preload won't
     * be listed through RecipeUtil until preload is called again.
     * <p>
     * Preload takes an average of 6ms, but runs asynchronously.
     *
     * @param plugin A reference to a <code>BukkitPlugin</code>
     * @param delay  The delay (in ticks) between running this method and the preload occurring.
     *               A delay may want to be specified to wait for other plugins to register their
     *               recipes.
     */
    public static void preload(BukkitPlugin plugin, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int version = MinecraftVersion.getVersionShort();
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
                while(iterator.hasNext()) {
                    Recipe recipe = iterator.next();
                    if(version >= 14 && recipe instanceof BlastingRecipe) {
                        BlastingRecipe castRecipe = (BlastingRecipe) recipe;
                        blastingInputs.add(castRecipe.getInput().getType());
                        blastingResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    } else if(version >= 14 && recipe instanceof CampfireRecipe) {
                        CampfireRecipe castRecipe = (CampfireRecipe) recipe;
                        campfireInputs.add(castRecipe.getInput().getType());
                        campfireResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    } else if(recipe instanceof FurnaceRecipe) {
                        FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
                        furnaceInputs.add(castRecipe.getInput().getType());
                        furnaceResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    } else if(version >= 14 && recipe instanceof SmokingRecipe) {
                        SmokingRecipe castRecipe = (SmokingRecipe) recipe;
                        smokingInputs.add(castRecipe.getInput().getType());
                        smokingResults.add(castRecipe.getResult().getType());
                        cookingInputs.add(castRecipe.getInput().getType());
                        cookingResults.add(castRecipe.getResult().getType());
                    } else if(recipe instanceof MerchantRecipe) {
                        MerchantRecipe castRecipe = (MerchantRecipe) recipe;
                        castRecipe.getIngredients().forEach(ingredient -> {
                            if(ingredient != null) merchantInputs.add(ingredient.getType());
                        });
                        merchantResults.add(castRecipe.getResult().getType());
                    } else if(recipe instanceof ShapedRecipe) {
                        ShapedRecipe castRecipe = (ShapedRecipe) recipe;
                        castRecipe.getIngredientMap().values().forEach(ingredient -> {
                            if(ingredient != null) shapedInputs.add(ingredient.getType());
                        });
                        shapedResults.add(castRecipe.getResult().getType());
                    } else if(recipe instanceof ShapelessRecipe) {
                        ShapelessRecipe castRecipe = (ShapelessRecipe) recipe;
                        castRecipe.getIngredientList().forEach(ingredient -> {
                            if(ingredient != null) shapelessInputs.add(ingredient.getType());
                        });
                        shapelessResults.add(castRecipe.getResult().getType());
                    } else if(version >= 16 && recipe instanceof SmithingRecipe) {
                        SmithingRecipe castRecipe = (SmithingRecipe) recipe;
                        smithingInputs.add(castRecipe.getBase().getItemStack().getType());
                        smithingResults.add(castRecipe.getResult().getType());
                    } else if(version >= 14 && recipe instanceof StonecuttingRecipe) {
                        StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
                        stonecuttingInputs.add(castRecipe.getInput().getType());
                        stonecuttingResults.add(castRecipe.getResult().getType());
                    }
                }
                preloaded = true;
            }
        }.runTaskLaterAsynchronously(plugin, delay);
    }

    /**
     * Get whether the specified Material is an input for a blasting recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isBlastingInput(Material material) {
        if(preloaded) {
            return blastingInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof BlastingRecipe)) continue;
            BlastingRecipe castRecipe = (BlastingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a campfire recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isCampfireInput(Material material) {
        if(preloaded) {
            return campfireInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CampfireRecipe)) continue;
            CampfireRecipe castRecipe = (CampfireRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a furnace recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isFurnaceInput(Material material) {
        if(preloaded) {
            return furnaceInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof FurnaceRecipe)) continue;
            FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a cooking recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isCookingInput(Material material) {
        if(preloaded) {
            return cookingInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CookingRecipe)) continue;
            CookingRecipe castRecipe = (CookingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a merchant recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isMerchantInput(Material material) {
        if(preloaded) {
            return merchantInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof MerchantRecipe)) continue;
            MerchantRecipe  castRecipe  = (MerchantRecipe) recipe;
            List<ItemStack> ingredients = castRecipe.getIngredients();
            for(ItemStack ingredient : ingredients) {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a shaped recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isShapedInput(Material material) {
        if(preloaded) {
            return shapedInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapedRecipe)) continue;
            ShapedRecipe castRecipe  = (ShapedRecipe) recipe;
            Map<Character, ItemStack> ingredients = castRecipe.getIngredientMap();
            for(ItemStack ingredient : ingredients.values()) {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a shapeless recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isShapelessInput(Material material) {
        if(preloaded) {
            return shapelessInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapelessRecipe)) continue;
            ShapelessRecipe castRecipe  = (ShapelessRecipe) recipe;
            List<ItemStack> ingredients = castRecipe.getIngredientList();
            for(ItemStack ingredient : ingredients) {
                if(ingredient.getType() == material) return true;
            }
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a smithing recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isSmithingInput(Material material) {
        if(preloaded) {
            return smithingInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmithingRecipe)) continue;
            SmithingRecipe castRecipe = (SmithingRecipe) recipe;
            if(castRecipe.getBase().getItemStack().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a smoking recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isSmokingInput(Material material) {
        if(preloaded) {
            return smokingInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmokingRecipe)) continue;
            SmokingRecipe castRecipe = (SmokingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is an input for a stone cutting recipe
     *
     * @param material The material to search for
     * @return Whether the material is an input or not
     */
    public static boolean isStoneCuttingInput(Material material) {
        if(preloaded) {
            return stonecuttingInputs.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof StonecuttingRecipe)) continue;
            StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
            if(castRecipe.getInput().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a blasting recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isBlastingResult(Material material) {
        if(preloaded) {
            return blastingResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof BlastingRecipe)) continue;
            BlastingRecipe castRecipe = (BlastingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a campfire recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isCampfireResult(Material material) {
        if(preloaded) {
            return campfireResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CampfireRecipe)) continue;
            CampfireRecipe castRecipe = (CampfireRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a furnace recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isFurnaceResult(Material material) {
        if(preloaded) {
            return furnaceResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof FurnaceRecipe)) continue;
            FurnaceRecipe castRecipe = (FurnaceRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a cooking recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isCookingResult(Material material) {
        if(preloaded) {
            return cookingResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof CookingRecipe)) continue;
            CookingRecipe castRecipe = (CookingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a merchant recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isMerchantResult(Material material) {
        if(preloaded) {
            return merchantResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof MerchantRecipe)) continue;
            MerchantRecipe  castRecipe  = (MerchantRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a shaped recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isShapedResult(Material material) {
        if(preloaded) {
            return shapedResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapedRecipe)) continue;
            ShapedRecipe castRecipe  = (ShapedRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a shapeless recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isShapelessResult(Material material) {
        if(preloaded) {
            return shapelessResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof ShapelessRecipe)) continue;
            ShapelessRecipe castRecipe  = (ShapelessRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a smithing recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isSmithingResult(Material material) {
        if(preloaded) {
            return smithingResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmithingRecipe)) continue;
            SmithingRecipe castRecipe = (SmithingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a smoking recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isSmokingResult(Material material) {
        if(preloaded) {
            return smokingResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof SmokingRecipe)) continue;
            SmokingRecipe castRecipe = (SmokingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get whether the specified Material is a result for a stone cutting recipe
     *
     * @param material The material to search for
     * @return Whether the material is a result or not
     */
    public static boolean isStoneCuttingResult(Material material) {
        if(preloaded) {
            return stonecuttingResults.contains(material);
        }
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while(iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if(!(recipe instanceof StonecuttingRecipe)) continue;
            StonecuttingRecipe castRecipe = (StonecuttingRecipe) recipe;
            if(castRecipe.getResult().getType() == material) return true;
        }
        return false;
    }

    /**
     * Get a list of all inputs of a blast furnace
     *
     * @return All inputs of a blast furnace
     */
    public static Set<Material> getBlastingInputs() {
        return (Set<Material>) blastingInputs.clone();
    }

    /**
     * Get a list of all inputs of a campfire
     *
     * @return All inputs of a campfire
     */
    public static Set<Material> getCampfireInputs() {
        return (Set<Material>) campfireInputs.clone();
    }

    /**
     * Get a list of all inputs of a furnace
     *
     * @return All inputs of a furnace
     */
    public static Set<Material> getFurnaceInputs() {
        return (Set<Material>) furnaceInputs.clone();
    }

    /**
     * Get a list of all inputs of any cooking object
     *
     * @return All inputs of any cooking object
     */
    public static Set<Material> getCookingInputs() {
        return (Set<Material>) cookingInputs.clone();
    }

    /**
     * Get a list of all inputs of a merchant
     *
     * @return All inputs of a merchant
     */
    public static Set<Material> getMerchantInputs() {
        return (Set<Material>) merchantInputs.clone();
    }

    /**
     * Get a list of all inputs of a shaped crafting recipe
     *
     * @return All inputs of a shaped crafting recipe
     */
    public static Set<Material> getShapedInputs() {
        return (Set<Material>) shapedInputs.clone();
    }

    /**
     * Get a list of all inputs of a shapeless crafting recipe
     *
     * @return All inputs of a blast furnace
     */
    public static Set<Material> getShapelessInputs() {
        return (Set<Material>) shapelessInputs.clone();
    }

    /**
     * Get a list of all inputs of a smithing table
     *
     * @return All inputs of a smithing table
     */
    public static Set<Material> getSmithingInputs() {
        return (Set<Material>) smithingInputs.clone();
    }

    /**
     * Get a list of all inputs of a smoker
     *
     * @return All inputs of a smoker
     */
    public static Set<Material> getSmokingInputs() {
        return (Set<Material>) smokingInputs.clone();
    }

    /**
     * Get a list of all inputs of a stonecutter
     *
     * @return All inputs of a blast stonecutter
     */
    public static Set<Material> getStonecuttingInputs() {
        return (Set<Material>) stonecuttingInputs.clone();
    }

    /**
     * Get a list of all results of a blast furnace
     *
     * @return All results of a blast furnace
     */
    public static Set<Material> getBlastingResults() {
        return (Set<Material>) blastingResults.clone();
    }

    /**
     * Get a list of all results of a campfire
     *
     * @return All results of a campfire
     */
    public static Set<Material> getCampfireResults() {
        return (Set<Material>) campfireResults.clone();
    }

    /**
     * Get a list of all results of a furnace
     *
     * @return All results of a furnace
     */
    public static Set<Material> getFurnaceResults() {
        return (Set<Material>) furnaceResults.clone();
    }

    /**
     * Get a list of all results of any cooking object
     *
     * @return All results of any cooking object
     */
    public static Set<Material> getCookingResults() {
        return (Set<Material>) cookingResults.clone();
    }

    /**
     * Get a list of all results of a merchant
     *
     * @return All results of a merchant
     */
    public static Set<Material> getMerchantResults() {
        return (Set<Material>) merchantResults.clone();
    }

    /**
     * Get a list of all results of a shaped crafting recipe
     *
     * @return All results of a shaped crafting recipe
     */
    public static Set<Material> getShapedResults() {
        return (Set<Material>) shapedResults.clone();
    }

    /**
     * Get a list of all results of a shapeless crafting recipe
     *
     * @return All results of a shapeless crafting recipe
     */
    public static Set<Material> getShapelessResults() {
        return (Set<Material>) shapelessResults.clone();
    }

    /**
     * Get a list of all results of a smithing table
     *
     * @return All results of a smithing table
     */
    public static Set<Material> getSmithingResults() {
        return (Set<Material>) smithingResults.clone();
    }

    /**
     * Get a list of all results of a smoker
     *
     * @return All results of a smoker
     */
    public static Set<Material> getSmokingResults() {
        return (Set<Material>) smokingResults.clone();
    }

    /**
     * Get a list of all results of a stonecutter
     *
     * @return All results of a blast stonecutter
     */
    public static Set<Material> getStonecuttingResults() {
        return (Set<Material>) stonecuttingResults.clone();
    }
}
