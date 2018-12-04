package com.stefthedev.villages;

import com.stefthedev.villages.commands.Command;
import com.stefthedev.villages.commands.commands.VillageCommand;
import com.stefthedev.villages.listeners.BlockListener;
import com.stefthedev.villages.listeners.PlayerListener;
import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Main extends JavaPlugin {

    private VillageManager villageManager;
    private Config messages, villages;

    public void onEnable() {

        saveDefaultConfig();

        messages = new Config(this, "messages");
        villages = new Config(this, "villages");

        registerConfigs(messages, villages);

        villageManager = new VillageManager(this);
        villageManager.setup();

        if(!getServer().getWorlds().contains(villageManager.getWorld())) {
            getLogger().warning(ChatColor.YELLOW + "The world does not exist that is configured in your config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        villageManager.deserialize();

        registerMessages(messages.getFileConfiguration());

        registerCommands(
                new VillageCommand(this)
        );

        registerListeners(
                new BlockListener(this),
                new PlayerListener(this)
        );
    }

    private void registerCommands(Command... commands){
        Arrays.asList(commands).forEach(command -> getCommand(command.getName()).setExecutor(command));
    }

    private void registerConfigs(Config... configs) {
        Arrays.asList(configs).forEach(Config::setup);
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public void registerMessages(FileConfiguration configuration) {
        Message.setConfiguration(configuration);
        for(Message message: Message.values()) {
            if (configuration.getString(message.getPath()) == null) {
                configuration.set(message.getPath(), message.getDef());
            }
        }
        messages.save();
    }

    public void onDisable() {
        villageManager.getInvite().clear();
        villageManager.getDisband().clear();
        villageManager.serialize();
    }

    public Config getMessages() {
        return messages;
    }

    public Config getVillages() {
        return villages;
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }
}
