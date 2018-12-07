package com.stefthedev.villages.utilities.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {

    private int slot;
    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> inventoryClickEvent;

    public MenuItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> inventoryClickEvent) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }
}
