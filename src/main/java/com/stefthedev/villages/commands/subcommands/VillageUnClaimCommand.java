package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;

public class VillageUnClaimCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageUnClaimCommand(Main plugin) {
        super("unclaim", 1);
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM_OWNER.toString());
                return;
            }
            village = villageManager.isClaimed(player.getLocation().getChunk());
            if(village == villageManager.getVillage(player)) {
                village.getChunks().remove(player.getLocation().getChunk());
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM.toString());
            } else if(village != null) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM_OTHER.toString()
                        .replace("{0}", village.getName())
                );
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM_FALSE.toString());
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
