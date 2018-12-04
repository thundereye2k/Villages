package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;

public class VillageHomeCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageHomeCommand(Main plugin) {
        super("home", "home", "Teleport to your village home", 1);
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
            return;
        }
        if(village.getLocation() == null) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION_FALSE.toString());
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LOCATION.toString());
            player.teleport(village.getLocation());
        }
    }
}