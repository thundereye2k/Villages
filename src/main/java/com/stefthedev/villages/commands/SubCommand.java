package com.stefthedev.villages.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    private String name;
    private int length;

    public SubCommand(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public abstract void onCommand(Player player, String[] args);

    public String getName() {
        return name;
    }

    int getLength() {
        return length;
    }
}
