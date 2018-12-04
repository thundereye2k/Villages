package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import org.bukkit.entity.Player;

public class VillageReloadCommand extends SubCommand {

    private Main plugin;

    public VillageReloadCommand(Main plugin) {
        super("reload", "reload", "Reload the village configuration.", 1);
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        try {
            plugin.getVillageManager().serialize();
            plugin.reloadConfig();
            plugin.getVillages().reload();
            plugin.getMessages().reload();
            plugin.getVillageManager().setup();
            plugin.getVillageManager().deserialize();
            plugin.registerMessages(plugin.getMessages().getFileConfiguration());
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_RELOAD.toString());
        } catch (Exception e) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_RELOAD_ERROR.toString());
        }
    }
}