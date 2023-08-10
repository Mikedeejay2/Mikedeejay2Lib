package com.mikedeejay2.mikedeejay2lib.item.crafting;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipeBuilder {
    private final BukkitPlugin plugin;
    private final NamespacedKey key;
    private ItemBuilder result;

    private final List<RecipeChoice> shapelessIngredients;
    private String[] rows;
    private final Map<Character, RecipeChoice> shapedIngredients;

    private CraftingRecipe recipe;
    private boolean shaped = false;
    private boolean changed;

    private CraftingRecipeBuilder(BukkitPlugin plugin, String recipeKey) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, recipeKey);
        this.changed = true;
        this.shapelessIngredients = new ArrayList<>();
        this.shapedIngredients = new HashMap<>();
    }

    public CraftingRecipeBuilder setResult(ItemStack result) {
        this.result = ItemBuilder.of(result);
        this.changed = true;
        return this;
    }

    public CraftingRecipeBuilder setResult(ItemBuilder result) {
        this.result = ItemBuilder.of(result);
        this.changed = true;
        return this;
    }

    public CraftingRecipeBuilder setShape(String row1, String row2, String row3) {
        this.rows = new String[] {row1, row2, row3};
        this.changed = true;
        this.shaped = true;
        return this;
    }

    public CraftingRecipeBuilder setIngredient(char key, Material ingredient) {
        return setIngredient(key, new RecipeChoice.MaterialChoice(ingredient));
    }

    public CraftingRecipeBuilder setIngredient(char key, Material... ingredients) {
        return setIngredient(key, new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder setIngredientM(char key, List<Material> ingredients) {
        return setIngredient(key, new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder setIngredient(char key, ItemStack ingredient) {
        return setIngredient(key, new RecipeChoice.ExactChoice(ingredient));
    }

    public CraftingRecipeBuilder setIngredient(char key, ItemStack... ingredients) {
        return setIngredient(key, new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder setIngredientI(char key, List<ItemStack> ingredients) {
        return setIngredient(key, new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder setIngredient(char key, ItemBuilder ingredient) {
        return setIngredient(key, new RecipeChoice.ExactChoice(ingredient.get()));
    }

    public CraftingRecipeBuilder setIngredient(char key, ItemBuilder... ingredients) {
        return setIngredient(key, new RecipeChoice.ExactChoice(
            Arrays.stream(ingredients)
                .map(ItemBuilder::get)
                .collect(Collectors.toList())
        ));
    }

    public CraftingRecipeBuilder setIngredientIB(char key, List<ItemBuilder> ingredients) {
        return setIngredient(key, new RecipeChoice.ExactChoice(
            ingredients.stream()
                .map(ItemBuilder::get)
                .collect(Collectors.toList())
        ));
    }

    public CraftingRecipeBuilder setIngredient(char key, RecipeChoice ingredient) {
        shapedIngredients.put(key, ingredient);
        this.changed = true;
        return this;
    }

    public CraftingRecipeBuilder addIngredient(Material ingredient) {
        return addIngredient(new RecipeChoice.MaterialChoice(ingredient));
    }

    public CraftingRecipeBuilder addIngredient(Material... ingredients) {
        return addIngredient(new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredientM(List<Material> ingredients) {
        return addIngredient(new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredient(int count, Material ingredient) {
        return addIngredient(count, new RecipeChoice.MaterialChoice(ingredient));
    }

    public CraftingRecipeBuilder addIngredient(int count, Material... ingredients) {
        return addIngredient(count, new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredientM(int count, List<Material> ingredients) {
        return addIngredient(count, new RecipeChoice.MaterialChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredient(ItemStack ingredient) {
        return addIngredient(new RecipeChoice.ExactChoice(ingredient));
    }

    public CraftingRecipeBuilder addIngredient(ItemStack... ingredients) {
        return addIngredient(new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredientI(List<ItemStack> ingredients) {
        return addIngredient(new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredient(int count, ItemStack ingredient) {
        return addIngredient(count, new RecipeChoice.ExactChoice(ingredient));
    }

    public CraftingRecipeBuilder addIngredient(int count, ItemStack... ingredients) {
        return addIngredient(count, new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredientI(int count, List<ItemStack> ingredients) {
        return addIngredient(count, new RecipeChoice.ExactChoice(ingredients));
    }

    public CraftingRecipeBuilder addIngredient(ItemBuilder ingredient) {
        return addIngredient(new RecipeChoice.ExactChoice(ingredient.get()));
    }

    public CraftingRecipeBuilder addIngredient(ItemBuilder... ingredients) {
        return addIngredient(new RecipeChoice.ExactChoice(builderArrayToStack(ingredients)));
    }

    public CraftingRecipeBuilder addIngredientIB(List<ItemBuilder> ingredients) {
        return addIngredient(new RecipeChoice.ExactChoice(builderListToStack(ingredients)));
    }

    public CraftingRecipeBuilder addIngredient(int count, ItemBuilder ingredient) {
        return addIngredient(count, new RecipeChoice.ExactChoice(ingredient.get()));
    }

    public CraftingRecipeBuilder addIngredient(int count, ItemBuilder... ingredients) {
        return addIngredient(count, new RecipeChoice.ExactChoice(builderArrayToStack(ingredients)));
    }

    public CraftingRecipeBuilder addIngredientIB(int count, List<ItemBuilder> ingredients) {
        return addIngredient(count, new RecipeChoice.ExactChoice(builderListToStack(ingredients)));
    }

    public CraftingRecipeBuilder addIngredient(int count, RecipeChoice ingredient) {
        while(count-- > 0) {
            addIngredient(ingredient);
        }
        return this;
    }

    public CraftingRecipeBuilder addIngredient(RecipeChoice ingredient) {
        shapelessIngredients.add(ingredient);
        this.changed = true;
        return this;
    }

    public CraftingRecipe addRecipe() {
        build();
        Bukkit.addRecipe(recipe);
        return recipe;
    }

    public CraftingRecipe get() {
        build();
        return recipe;
    }

    private CraftingRecipe build() {
        if(!changed) return recipe;
        if(shaped) {
            ShapedRecipe shapedRecipe = new ShapedRecipe(key, result.get());
            this.recipe = shapedRecipe;
            shapedRecipe.shape(rows);
            for(Character character : shapedIngredients.keySet()) {
                RecipeChoice choice = shapedIngredients.get(character);
                shapedRecipe.setIngredient(character, choice);
            }
        } else {
            ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key, result.get());
            this.recipe = shapelessRecipe;
            for(RecipeChoice choice : shapelessIngredients) {
                shapelessRecipe.addIngredient(choice);
            }
        }
        this.changed = false;
        return this.recipe;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public static CraftingRecipeBuilder of(BukkitPlugin plugin, String recipeKey) {
        return new CraftingRecipeBuilder(plugin, recipeKey);
    }

    private static ItemStack[] builderArrayToStack(ItemBuilder... itemBuilders) {
        ItemStack[] itemStacks = new ItemStack[itemBuilders.length];
        for(int i = 0; i < itemBuilders.length; ++i) {
            itemStacks[i] = itemBuilders[i].get();
        }
        return itemStacks;
    }

    private static List<ItemStack> builderListToStack(List<ItemBuilder> itemBuilders) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for(ItemBuilder builder : itemBuilders) {
            itemStacks.add(builder.get());
        }
        return itemStacks;
    }
}
