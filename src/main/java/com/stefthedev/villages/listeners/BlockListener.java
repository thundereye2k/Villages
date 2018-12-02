package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageFlag;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
        Village village = villageManager.isClaimed(event.getBlock().getChunk());
        if(village != null) {
            Player player = event.getPlayer();
            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "break blocks")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Village village = villageManager.isClaimed(event.getBlock().getChunk());
        if (village != null) {
            Player player = event.getPlayer();
            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "place blocks")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        Village village = villageManager.isClaimed(event.getBlock().getChunk());
        if (village != null) {
            if (event.getPlayer() == null) return;
            Player player = event.getPlayer();

            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "ignite blocks")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onFertilize(BlockFertilizeEvent event) {
        Village village = villageManager.isClaimed(event.getBlock().getChunk());
        if (village != null) {
            if (event.getPlayer() == null) return;
            Player player = event.getPlayer();

            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return;
            }

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", "farm")
                    .replace("{1}", village.getName())
            );
            event.setCancelled(true);
        }

    }

    private boolean check(Block block, Player player, VillageFlag villageFlag) {
        Village village = villageManager.isClaimed(block.getChunk());
        if (village != null) {
            if (player == null) return true;

            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) {
                return true;
            }

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", villageFlag.toString())
                    .replace("{1}", village.getName())
            );
        }
        return false;
    }
}
