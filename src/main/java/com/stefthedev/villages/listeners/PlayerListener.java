package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageFlag;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

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

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        Village from = villageManager.isClaimed(event.getFrom().getChunk());
        Village to = villageManager.isClaimed(event.getTo().getChunk());

        if(villageManager.isClaimed(event.getFrom().getChunk()) == villageManager.isClaimed(event.getTo().getChunk())) return;

        if(from == null && to != null) {
            event.getPlayer().sendTitle(Message.VILLAGE_TITLE_HEADER.toString().replace("{0}", to.toString()),
                    Message.VILLAGE_TITLE_FOOTER.toString().replace("{0}", to.toString()), 10, 30, 10
            );
            return;
        }

        if (to == null && from != null){
            event.getPlayer().sendTitle(Message.VILLAGE_TITLE_HEADER_WILDERNESS.toString().replace("{0}", "Wilderness"),
                    Message.VILLAGE_TITLE_FOOTER_WILDERNESS.toString().replace("{0}", "Wilderness"), 10, 30, 10
            );
        }

        if(to != null && to != from) {
            event.getPlayer().sendTitle(Message.VILLAGE_TITLE_HEADER.toString().replace("{0}", to.toString()),
                    Message.VILLAGE_TITLE_FOOTER.toString().replace("{0}", to.toString()), 10, 30, 10
            );
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //This is just to be safe.
        villageManager.getDisband().remove(event.getPlayer().getUniqueId());
        villageManager.getInvite().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //This is just to be safe.
        villageManager.getDisband().remove(event.getPlayer().getUniqueId());
        villageManager.getInvite().remove(event.getPlayer().getUniqueId());
    }
}
