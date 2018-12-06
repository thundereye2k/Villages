package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VillageCreateCommand extends SubCommand {

    private VillageManager villageManager;
    private Plugin plugin;

    public VillageCreateCommand(Main plugin) {
        super("create", "create [name]", 2);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            if(villageManager.exists(args[1])) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CREATE_EXISTS.toString()
                        .replace("{0}", args[1])
                );
                return;
            }
            village = villageManager.isClaimed(player.getLocation().getChunk());
            if(village == null) {
                village = new Village(args[1], player.getUniqueId(), 0, player.getLocation());
                village.addChunk(player.getLocation().getChunk());
                villageManager.add(village);
                plugin.getServer().broadcastMessage(Message.PREFIX.toString() + Message.VILLAGE_CREATE_ALL.toString()
                        .replace("{0}", player.getName())
                        .replace("{1}", village.toString())
                );
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_OTHER.toString()
                    .replace("{0}", village.toString())
                );
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_TRUE.toString());
        }
    }
}
