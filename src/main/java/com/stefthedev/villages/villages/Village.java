package com.stefthedev.villages.villages;

import org.bukkit.Chunk;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Village {

    private String name;
    private UUID owner;
    private int level;

    private final Set<UUID> members;
    private final Set<Chunk> chunks;

    public Village(String name, UUID owner, int level) {
        this.name = name;
        this.owner = owner;
        this.level = level;
        this.members = new HashSet<>();
        this.chunks = new HashSet<>();
    }

    public Set<Chunk> getChunks() {
        return chunks;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getLevel() {
        return level;
    }
}
