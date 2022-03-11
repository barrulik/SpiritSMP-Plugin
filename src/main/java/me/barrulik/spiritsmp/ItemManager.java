package me.barrulik.spiritsmp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack transformer;

    public static void init(){
        createRecipes();
    }

    private static void createRecipes(){
        ItemStack transformerItem = new ItemStack(Material.CLOCK, 1);
        ItemMeta transformerMeta = transformerItem.getItemMeta();
        transformerMeta.setDisplayName("Transformer");
        List<String> transformerLore = new ArrayList<>();
        transformerLore.add("Makes you a Human if you are Spirit");
        transformerLore.add("Makes you a Spirit if you are Human");
        transformerMeta.setLore(transformerLore);
        transformerItem.setItemMeta(transformerMeta);
        transformer = transformerItem;


        ShapedRecipe transformer = new ShapedRecipe(NamespacedKey.minecraft("transformer"), transformerItem);
        transformer.shape("GOG"
                , "ODO"
                , "GOG");

        transformer.setIngredient('D', Material.DIAMOND_BLOCK);
        transformer.setIngredient('O', Material.OBSIDIAN);
        transformer.setIngredient('G', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(transformer);
    }
}