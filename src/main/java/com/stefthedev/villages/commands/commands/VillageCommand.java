package com.stefthedev.villages.commands.commands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.Command;
import com.stefthedev.villages.commands.subcommands.*;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;


public class VillageCommand extends Command {

    private VillageManager villageManager;

    public VillageCommand(Main plugin) {
        super("village", "village",
                new VillageAcceptCommand(plugin),
                new VillageClaimCommand(plugin),
                new VillageCreateCommand(plugin),
                new VillageDenyCommand(plugin),
                new VillageDisbandCommand(plugin),
                new VillageInviteCommand(plugin),
                new VillageReloadCommand(plugin),
                new VillageUnClaimCommand(plugin)
        );
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        villageManager.getHelp().forEach(s -> player.sendMessage(Chat.color(s)));
    }
}
