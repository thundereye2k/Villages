package com.stefthedev.villages.commands;

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
                if (commandSender.hasPermission((permission + "." + subCommand.getName()).toLowerCase())) {
                    if (subCommand.getName().equalsIgnoreCase(strings[0])) {
                        if (strings.length < subCommand.getLength()) {
                            commandSender.sendMessage("/" + name + " " + subCommand.getName() + " requires at least " + (subCommand.getLength() + 1) + " arguments.");
                        } else {
                            subCommand.onCommand((Player) commandSender, strings);
                        }
                    }
                } else {
                    commandSender.sendMessage("You do not have permissions for this command.");
                }
            });
        } else {
            onCommand((Player) commandSender, strings);
        }
        return false;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getName() {
        return name;
    }
}
