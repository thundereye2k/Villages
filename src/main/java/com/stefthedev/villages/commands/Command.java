package com.stefthedev.villages.commands;

import com.stefthedev.villages.utilities.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class Command implements CommandExecutor {

    private String name, permission;
    private final SubCommand[] subCommands;

    public Command(String name, String permission, SubCommand... subCommands) {
        this.name = name;
        this.permission = permission;
        this.subCommands = subCommands;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (strings.length > 0) {
            Arrays.asList(subCommands).forEach(subCommand -> {
                if (!commandSender.hasPermission((permission + "." + subCommand.getName()).toLowerCase())) {
                    commandSender.sendMessage("You do not have permissions for this command.");
                    return;
                }
                if (subCommand.getName().equalsIgnoreCase(strings[0])) {
                    if (strings.length < subCommand.getLength()) {
                        commandSender.sendMessage(Message.PREFIX.toString() + Message.USAGE.toString()
                                .replace("{0}", "/" + name + subCommand.getUsage())
                        );
                        return;
                    }
                    subCommand.onCommand((Player) commandSender, strings);
                }
            });
            return true;
        }
        onCommand((Player) commandSender, strings);
        return false;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getName() {
        return name;
    }
}
