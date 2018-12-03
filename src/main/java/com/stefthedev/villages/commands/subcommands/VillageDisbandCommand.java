package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VillageDisbandCommand extends SubCommand {

    private VillageManager villageManager;
    private Plugin plugin;

    public VillageDisbandCommand(Main plugin) {
        super("disband", "disband", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_OWNER.toString());
            } else {
                plugin.getServer().broadcastMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_BROADCAST.toString()
                        .replace("{0}", village.getName())
                );
                villageManager.remove(village);
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
