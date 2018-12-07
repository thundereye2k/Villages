package com.stefthedev.villages.utilities.menus;

import com.stefthedev.villages.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Menu implements InventoryHolder, Listener {

    private Inventory inventory;
    private List<MenuItem> itemList;

    public Menu(Main plugin, String name, int size) {
        this.inventory = Bukkit.createInventory( this, size, name);
        this.itemList = new ArrayList<>();
        plugin.registerListeners(this);
    }

    public void open(Player player) {
        player.closeInventory();
        player.openInventory(inventory);
    }

    protected void addItems(MenuItem... menuItems) {
        if(Arrays.asList(menuItems).isEmpty()) return;
        Arrays.asList(menuItems).forEach(menuItem -> {
            inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
            itemList.add(menuItem);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(getInventory() == null) return;
        if(event.getClickedInventory().equals(getInventory())) {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            if(itemList.isEmpty()) return;
            for(MenuItem menuItem : itemList) {
                if(name == null) return;
                if (menuItem.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
                    if(menuItem.getInventoryClickEvent() == null) return;
                    menuItem.getInventoryClickEvent().accept(event);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(getInventory() == null) return;
        if(event.getInventory().equals(getInventory())) {
            HandlerList.unregisterAll(this);
        }
    }

    public abstract Menu build();

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}