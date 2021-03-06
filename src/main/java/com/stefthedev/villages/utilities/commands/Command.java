package com.stefthedev.villages.utilities.commands;

import com.stefthedev.villages.utilities.general.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        if (strings.length == 0) {
            onCommand((Player) commandSender, strings);
        } else {
            if(StringUtils.isNumeric(strings[0])) {
                onCommand((Player) commandSender, strings);
                return true;
            }
            for(SubCommand subCommand : subCommands) {
                if (!commandSender.hasPermission((permission + "." + subCommand.toString()).toLowerCase())) {
                    commandSender.sendMessage(Message.PREFIX.toString() + Message.PERMISSION.toString());
                    break;
                }
                if (subCommand.getName().equalsIgnoreCase(strings[0])) {
                    if (strings.length < subCommand.getLength()) {
                        commandSender.sendMessage(Message.PREFIX.toString() + Message.USAGE.toString()
                                .replace("{0}", "/" + name + subCommand.getUsage())
                        );
                        break;
                    }
                    subCommand.onCommand((Player) commandSender, strings);
                    break;
                }
            }
        }
        return true;
    }

    protected SubCommand[] getSubCommands() {
        return subCommands;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getName() {
        return name;
    }
}
