package com.stefthedev.villages;

import com.stefthedev.villages.commands.Command;
import com.stefthedev.villages.commands.commands.VillageCommand;
import com.stefthedev.villages.managers.ConfigManager;
import com.stefthedev.villages.managers.HookManager;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.listeners.BlockListener;
import com.stefthedev.villages.listeners.PlayerListener;
import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Manager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    private final Set<Manager> managersSet = new HashSet<>();
    private HookManager hookManager;
    private ConfigManager configManager;
    private VillageManager villageManager;

    public void onEnable() {
        saveDefaultConfig();

        hookManager = new HookManager(this);

        configManager = new ConfigManager(
                new Config(this, "messages"),
                new Config(this, "villages")
        );

        villageManager = new VillageManager(this, configManager.getObject("villages"));

        registerManagers(hookManager, configManager, villageManager);

        registerCommands(new VillageCommand(this));
        registerListeners(new BlockListener(this), new PlayerListener(this));
    }

    public void onDisable() {
        unregisterManagers();
    }

    private void registerManagers(Manager... managers) {
        managersSet.addAll(Arrays.asList(managers));
        managersSet.forEach(Manager::register);
    }

    private void unregisterManagers() {
        managersSet.forEach(Manager::unregister);
    }

    private void registerCommands(Command... commands){
        Arrays.asList(commands).forEach(command -> getCommand(command.getName()).setExecutor(command));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }
}