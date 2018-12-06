package com.stefthedev.villages.managers;

import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Manager;
import com.stefthedev.villages.utilities.Message;

import java.util.Arrays;

public class ConfigManager extends Manager<Config> {

    public ConfigManager(Config... configs) {
        super("Config");
        Arrays.asList(configs).forEach(this::add);
        getSet().forEach(Config::setup);
    }

    @Override
    public void register() {
        Config config = getObject("messages");
        Message.setConfiguration(config.getFileConfiguration());
        for(Message message: Message.values()) {
            if (config.getFileConfiguration().getString(message.getPath()) == null) {
                config.getFileConfiguration().set(message.getPath(), message.getDef());
            }
        }
        config.save();
    }

    @Override
    public void unregister() {
        getSet().forEach(Config::save);
        clear();
    }
}
