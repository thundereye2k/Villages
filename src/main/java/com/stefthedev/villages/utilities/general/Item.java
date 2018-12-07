package com.stefthedev.villages.utilities.general;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Item {

    private String name;
    private Material material;
    private short durability;
    private String[] lore;

    public Item(String name, Material material, short durability, String[] lore) {
        this.name = name;
        this.material = material;
        this.durability = durability;
        this.lore = lore;
    }

    public ItemStack build(Map<Enchantment, Integer> enchantments)  {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Chat.color(name));
        if (enchantments != null) {
            enchantments.forEach(itemStack::addEnchantment);
        }
        itemMeta.setLore(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()));
        itemStack.setDurability(durability);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}