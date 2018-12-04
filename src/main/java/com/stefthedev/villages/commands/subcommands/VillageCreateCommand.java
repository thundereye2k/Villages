package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
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
            village = new Village(args[1], player.getUniqueId(), 0);
            village.getChunks().add(player.getLocation().getChunk());
            villageManager.add(village);
            plugin.getServer().broadcastMessage(Message.PREFIX.toString() + Message.VILLAGE_CREATE_ALL.toString()
                    .replace("{0}", player.getName())
                    .replace("{1}", village.getName())
            );
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_TRUE.toString());
        }
    }
}
