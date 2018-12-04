package com.stefthedev.villages.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    private String name, usage, description;
    private int length;

    public SubCommand(String name, String usage, String description, int length) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.length = length;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getName() {
        return name;
    }

    String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    int getLength() {
        return length;
    }

}
