package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;

public class VillageClaimCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageClaimCommand(Main plugin) {
        super("claim", "claim", 1);
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(player.getWorld() != villageManager.getWorld()) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_WORLD.toString()
                        .replace("{0}", villageManager.getWorld().getName())
                );
                return;
            }
            if(village.getChunks().size() >= villageManager.getSize()) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_LIMIT.toString());
                return;
            }
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_OWNER.toString());
                return;
            }
            village = villageManager.isClaimed(player.getLocation().getChunk());
            if(village == villageManager.getVillage(player)) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_SELF.toString());
            } else if(village == null) {
                village = villageManager.getVillage(player);
                village.getChunks().add(player.getLocation().getChunk());
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_SUCCESS.toString()
                        .replace("{0}", player.getLocation().getChunk().getX() + "," + player.getLocation().getChunk().getZ())
                );
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_OTHER.toString()
                        .replace("{0}", village.getName())
                );
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
