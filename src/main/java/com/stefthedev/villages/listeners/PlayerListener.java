package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.villages.VillageFlag;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private VillageManager villageManager;

    public PlayerListener(Main plugin) {
        this.villageManager = plugin.getVillageManager();
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if(villageManager.check(event.getPlayer().getLocation().getBlock(), event.getPlayer(), VillageFlag.BUCKET_EMPTY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if(villageManager.check(event.getPlayer().getLocation().getBlock(), event.getPlayer(), VillageFlag.BUCKET_FILL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(villageManager.check(event.getPlayer().getLocation().getBlock(), event.getPlayer(), VillageFlag.INTERACT)) {
            if(event.getAction() == Action.PHYSICAL) {
                event.setCancelled(true);
            }
        }
    }
}
