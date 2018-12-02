package com.stefthedev.villages.commands.commands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.Command;
import com.stefthedev.villages.commands.subcommands.*;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Message;
import org.bukkit.entity.Player;


public class VillageCommand extends Command {

    public VillageCommand(Main plugin) {
        super("village", "village",
                new VillageAcceptCommand(plugin),
                new VillageClaimCommand(plugin),
                new VillageCreateCommand(plugin),
                new VillageDenyCommand(plugin),
                new VillageDisbandCommand(plugin),
                new VillageInviteCommand(plugin),
                new VillageUnClaimCommand(plugin)
        );
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(Message.PREFIX.toString() + Chat.color("This version is still in alpha use&b /village create [name] &7to get started."));
    }
}
