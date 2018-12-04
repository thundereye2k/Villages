package com.stefthedev.villages.villages;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Village {

    private final String name;
    private final UUID owner;
    private final int level;

    private Location location;

    private final Set<UUID> members;
    private final Set<Chunk> chunks;

    public Village(String name, UUID owner, int level, Location location) {
        this.name = name;
        this.owner = owner;
        this.level = level;
        this.location = location;
        this.members = new HashSet<>();
        this.chunks = new HashSet<>();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    int getLevel() {
        return level;
    }

    public Location getLocation() {
        return location;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Set<Chunk> getChunks() {
        return chunks;
    }
}
