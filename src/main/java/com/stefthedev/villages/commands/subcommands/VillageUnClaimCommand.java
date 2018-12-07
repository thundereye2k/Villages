package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.villages.Village;
import com.stefthedev.villages.utilities.villages.VillageClaim;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class VillageUnClaimCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageUnClaimCommand(Main plugin) {
        super("unclaim", "unclaim", 1);
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
            Chunk chunk = player.getLocation().getChunk();
            village = villageManager.isClaimed(player.getLocation().getChunk());
            if(village == villageManager.getVillage(player)) {
                village.getChunks().remove(new VillageClaim(chunk.getX(), chunk.getZ()));
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM.toString());
            } else if(village != null) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM_OTHER.toString()
                        .replace("{0}", village.toString())
                );
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_UNCLAIM_FALSE.toString());
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
