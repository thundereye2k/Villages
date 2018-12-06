package com.stefthedev.villages.managers;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Manager;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageClaim;
import com.stefthedev.villages.villages.VillageFlag;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.List;

public class VillageManager extends Manager<Village> {

    private Main plugin;
    private Config config;

    private final Map<UUID, Village> invite, disband;

    private World world;
    private int size;

    public VillageManager(Main plugin, Config config) {
        super("Village");
        this.plugin = plugin;
        this.config = config;

        this.invite = new HashMap<>();
        this.disband = new HashMap<>();
    }

    @Override
    public void register() {
        this.world = plugin.getServer().getWorld(plugin.getConfig().getString("World"));
        this.size = plugin.getConfig().getInt("Claims");

        if(!plugin.getServer().getWorlds().contains(world)) {
            plugin.getLogger().warning(ChatColor.YELLOW + "The world does not exist that is configured in your config.yml");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        FileConfiguration configuration = config.getFileConfiguration();
        ConfigurationSection configSection = configuration.getConfigurationSection("");
        if (configSection != null) {
            if (configSection.getKeys(false).isEmpty()) {
                plugin.getLogger().warning(ChatColor.YELLOW + "No villages were loaded as none were found.");
                return;
            }
            try {
                configSection.getKeys(false).forEach(s -> {
                    Village village = new Village(s,
                            UUID.fromString(configuration.getString(s + ".owner")),
                            configuration.getInt(s + ".level"),
                            (Location) configuration.get(s + ".location"));
                    setup(village, configuration.getStringList(s + ".claims"), configuration.getStringList(s + ".members"));
                    add(village);
                });
                plugin.getLogger().info(ChatColor.GREEN + "Villages loaded correctly.");
            } catch (Exception e) {
                plugin.getLogger().severe(ChatColor.RED + "An error occurred, unable to load villages.");
            }
        } else {
            plugin.getLogger().warning(ChatColor.YELLOW + "No villages were loaded as none were found.");
        }
    }

    @Override
    public void unregister() {
        FileConfiguration configuration = config.getFileConfiguration();
        ConfigurationSection configSection = configuration.getConfigurationSection("");
        configSection.getKeys(false).forEach(s -> configuration.set(s, null));
        getSet().forEach(village -> {
            configuration.set(village.toString() + ".level", village.getLevel());
            configuration.set(village.toString() + ".owner", village.getOwner().toString());
            configuration.set(village.toString() + ".members", village.getMembers().toArray());
            configuration.set(village.toString() + ".claims", getClaims(village));
            configuration.set(village.toString() + ".location", village.getLocation());
        });
        config.save();

        disband.clear();
        invite.clear();
        clear();
    }

    public boolean exists(String name) {
        for(Village village : getSet()) {
            if(village.toString().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Village isClaimed(Chunk chunk) {
        for (Village village : getSet()) {
            if (village.isClaimed(chunk.getX(), chunk.getZ())) {
                return village;
            }
        }
        return null;
    }

    private List<String> getClaims(Village village) {
        List<String> claims = new ArrayList<>();
        for (VillageClaim villageClaim : village.getChunks()) {
            claims.add(villageClaim.toString());
        }
        return claims;
    }

    public boolean allowClaim(Player player) {
        Village village = getVillage(player);
        for(Chunk chunk : getSurroundingChunks(player)) {
            for(VillageClaim villageClaim : village.getChunks()) {
                if(villageClaim.getX() == chunk.getX() && villageClaim.getZ() == chunk.getZ()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Chunk> getSurroundingChunks(Player player) {
        int[] offset = {-1,0,1};
        World world = player.getWorld();
        int baseX = player.getLocation().getChunk().getX();
        int baseZ = player.getLocation().getChunk().getZ();

        Set<Chunk> chunks = new HashSet<>();

        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunks.add(chunk);
            }
        }
        return chunks;
    }

    public Village getVillage(Player player) {
        for (Village village : getSet()) {
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
                village.addClaim(new VillageClaim(
                        Integer.parseInt(chunk[0]),
                        Integer.parseInt(chunk[1])
                ));
            });
            members.forEach(s -> village.getMembers().add(UUID.fromString(s)));
        } catch (Exception e) {
            plugin.getLogger().severe(ChatColor.RED + "An error occurred while loading: " + village.toString());
            e.printStackTrace();
        }
    }

    public boolean check(Block block, Player player, VillageFlag villageFlag) {
        Village village = isClaimed(block.getChunk());
        if (village == null) {
            return false;
        } else {
            if (player == null) return false;
            if (village.getMembers().contains(player.getUniqueId()) || village.getOwner().equals(player.getUniqueId())) return false;
            if(villageFlag == VillageFlag.INTERACT) return false;

            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_DENY.toString()
                    .replace("{0}", villageFlag.toString())
                    .replace("{1}", village.toString())
            );
            return true;
        }
    }

    public Map<UUID, Village> getInvite() {
        return invite;
    }

    public Map<UUID, Village> getDisband() {
        return disband;
    }

    public World getWorld() {
        return world;
    }

    public int getSize() {
        return size;
    }
}