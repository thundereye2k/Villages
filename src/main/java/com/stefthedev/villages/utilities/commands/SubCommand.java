package com.stefthedev.villages.utilities.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    private String name, usage;
    private int length;

    public SubCommand(String name, String usage, int length) {
        this.name = name;
        this.usage = usage;
        this.length = length;
    }

    public abstract void onCommand(Player player, String[] args);

    String getName() {
        return name;
    }

    String getUsage() {
        return usage;
    }

    int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return name;
    }
}
