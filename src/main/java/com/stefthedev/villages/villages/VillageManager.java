package com.stefthedev.villages.villages;

import com.stefthedev.villages.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class VillageManager {

    private Main plugin;
    private FileConfiguration config;

    private final Set<Village> villageSet;
    private final Map<UUID, Village> invite;

    public VillageManager(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.villageSet = new HashSet<>();
        this.invite = new HashMap<>();
    }

    public void deserialize() {
        ConfigurationSection configSection = config.getConfigurationSection("");
        if (configSection != null) {
            if (configSection.getKeys(false).isEmpty()) {
                plugin.getLogger().warning(ChatColor.YELLOW + "No villages were loaded as none were found.");
                return;
            }
            try {
                configSection.getKeys(false).forEach(s -> {
                    Village village = new Village(s,
                            UUID.fromString(config.getString(s + ".owner")),
                            config.getInt(s + ".level")
                    );
                    setup(village, config.getStringList(s + ".claims"), config.getStringList(s + ".members"));

                    villageSet.add(village);
                });
                plugin.getLogger().info(ChatColor.GREEN + "Villages loaded correctly.");
            } catch (Exception e) {
                plugin.getLogger().severe(ChatColor.RED + "An error occurred, unable to load villages.");
            }
        } else {
            plugin.getLogger().warning(ChatColor.YELLOW + "No villages were loaded as none were found.");
        }
    }

    public void serialize() {
        villageSet.forEach(village -> {
            config.set(village.getName() + ".level", village.getLevel());
            config.set(village.getName() + ".owner", village.getOwner().toString());
            config.set(village.getName() + ".members", getMembers(village));
            config.set(village.getName() + ".claims", getChunks(village));
        });
        plugin.saveConfig();
    }

    public void add(Village village) {
        villageSet.add(village);
    }

    public void remove(Village village) {
        villageSet.remove(village);
    }

    public Village isClaimed(Chunk chunk) {
        for (Village village : villageSet) {
            if (village.getChunks().contains(chunk)) {
                return village;
            }
        }
        return null;
    }

    public Map<UUID, Village> getInvite() {
        return invite;
    }

    private List<UUID> getMembers(Village village) {
        return new ArrayList<>(village.getMembers());
    }

    private List<String> getChunks(Village village) {
        List<String> chunks = new ArrayList<>();
        for (Chunk chunk : village.getChunks()) {
            chunks.add(chunk.getX() + ":" + chunk.getZ());
        }
        return chunks;
    }

    public Village getVillage(Player player) {
        for (Village village : villageSet) {
            if (village.getMembers().contains(player.getUniqueId())) {
                return village;
            }
            if (village.getOwner().equals(player.getUniqueId())) {
                return village;
            }
        }
        return null;
    }

    private void setup(Village village, List<String> chunks, List<String> members) {
        try {
            chunks.forEach(s -> {
                String[] chunk = s.split(":");
                village.getChunks().add(Bukkit.getWorld("world").getChunkAt(
                        Integer.parseInt(chunk[0]),
                        Integer.parseInt(chunk[1])
                ));
            });
            members.forEach(s -> village.getMembers().add(UUID.fromString(s)));
        } catch (Exception e) {
            plugin.getLogger().severe(ChatColor.RED + "An error occurred while loading: " + village.getName());
            e.printStackTrace();
        }
    }
}
