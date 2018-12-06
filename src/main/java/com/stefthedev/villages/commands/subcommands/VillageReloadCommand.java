package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Message;
import org.bukkit.entity.Player;

public class VillageReloadCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageReloadCommand(Main plugin) {
        super("reload", "reload", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        try {
            villageManager.register();
            plugin.reloadConfig();
            plugin.getConfigManager().getSet().forEach(Config::reload);
            villageManager.unregister();
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_RELOAD.toString());
        } catch (Exception e) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_RELOAD_ERROR.toString());
        }
    }
}