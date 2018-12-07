package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.entity.Player;

public class VillageSetHomeCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageSetHomeCommand(Main plugin) {
        super("sethome", "sethome", 1);
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
            return;
        }

        if(village.getOwner().equals(player.getUniqueId())) {
            Village targetVillage = villageManager.isClaimed(player.getLocation().getChunk());

            if(targetVillage == null) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION_WILDERNESS.toString());
                return;
            }

            if(targetVillage == village) {
                village.setLocation(player.getLocation());
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION_SET.toString());
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION_OTHER.toString());
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION_OWNER.toString());
        }
    }
}
