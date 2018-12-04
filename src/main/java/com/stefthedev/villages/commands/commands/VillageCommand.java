package com.stefthedev.villages.commands.commands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.Command;
import com.stefthedev.villages.commands.subcommands.*;
import com.stefthedev.villages.utilities.Chat;
import org.bukkit.entity.Player;

public class VillageCommand extends Command {

    private Main plugin;

    public VillageCommand(Main plugin) {
        super("village", "village",
                new VillageAcceptCommand(plugin),
                new VillageClaimCommand(plugin),
                new VillageCreateCommand(plugin),
                new VillageDenyCommand(plugin),
                new VillageDisbandCommand(plugin),
                new VillageHomeCommand(plugin),
                new VillageInviteCommand(plugin),
                new VillageReloadCommand(plugin),
                new VillageSetHomeCommand(plugin),
                new VillageUnClaimCommand(plugin)
        );
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        int index = 1;
        if(args.length == 1) {
            if (args[0].matches("[0-9]+")) {
                index = Integer.parseInt(args[0]);
            }
        }

        if(index > plugin.getConfig().getConfigurationSection("Help").getKeys(false).size()) {
            index = 1;
        }

        plugin.getConfig().getStringList("Help." + index).forEach(s -> player.sendMessage(Chat.color(s)));
    }
}
