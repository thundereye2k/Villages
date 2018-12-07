package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.villages.VillageFlag;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
public class BlockListener implements Listener {

    private VillageManager villageManager;

    public BlockListener(Main plugin) {
        this.villageManager = plugin.getVillageManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.BLOCK_BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.BLOCK_PLACE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.BLOCK_IGNITE)) {
            event.setCancelled(true);
        }
    }
}
