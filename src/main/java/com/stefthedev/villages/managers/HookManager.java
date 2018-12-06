package com.stefthedev.villages.managers;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.Hook;
import com.stefthedev.villages.utilities.Manager;

import java.util.Arrays;

public class HookManager extends Manager<Hook> {

    private Main plugin;

    public HookManager(Main plugin, Hook... hooks) {
        super("Hook");
        this.plugin = plugin;
        Arrays.asList(hooks).forEach(this::add);
    }

    @Override
    public void register() {
        getSet().forEach(hook -> {
            if(plugin.getServer().getPluginManager().getPlugin(hook.toString()) != null) {
                hook.setEnabled(true);
            } else {
                plugin.getLogger().warning(hook.toString() + " not found, disabling some plugin functionality.");
            }
        });
    }

    @Override
    public void unregister() {
        clear();
    }
}
