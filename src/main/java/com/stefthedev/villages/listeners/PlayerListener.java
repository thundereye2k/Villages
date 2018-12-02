package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;
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
        Village village = villageManager.isClaimed(event.getPlayer().getLocation().getChunk());
        if (village != null) {
            Player player = event.getPlayer();

            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "empty buckets")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Village village = villageManager.isClaimed(event.getPlayer().getLocation().getChunk());
        if (village != null) {
            Player player = event.getPlayer();

            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "fill buckets")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerInteractEvent event) {
        Village village = villageManager.isClaimed(event.getPlayer().getLocation().getChunk());
        if (village != null) {
            Player player = event.getPlayer();

            if (event.getAction() == Action.PHYSICAL) {
                if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                    return;
                }
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                        .replace("{0}", "interact")
                        .replace("{1}", village.getName())
                );
                event.setCancelled(true);
            }

        }
    }
}
