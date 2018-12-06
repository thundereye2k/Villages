package com.stefthedev.villages.utilities;

import org.bukkit.plugin.Plugin;

public class Hook {

    private final Plugin plugin;
    private final String name;

    private boolean enabled;

    public Hook(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
