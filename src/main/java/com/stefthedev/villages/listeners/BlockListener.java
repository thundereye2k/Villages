package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.villages.VillageFlag;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private VillageManager villageManager;

    public BlockListener(Main plugin) {
        this.villageManager = plugin.getVillageManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.PLACE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.IGNITE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFertilize(BlockFertilizeEvent event) {
        if(villageManager.check(event.getBlock(), event.getPlayer(), VillageFlag.FARM)) {
            event.setCancelled(true);
        }
    }
}
