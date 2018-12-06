package com.stefthedev.villages.villages;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Village {

    private final String name;
    private final UUID owner;
    private final int level;

    private Location location;

    private final Set<UUID> members;
    private final Set<VillageClaim> claims;

    public Village(String name, UUID owner, int level, Location location) {
        this.name = name;
        this.owner = owner;
        this.level = level;
        this.location = location;
        this.members = new HashSet<>();
        this.claims = new HashSet<>();
    }

    public void addChunk(Chunk chunk) {
        claims.add(new VillageClaim(chunk.getX(), chunk.getZ()));
    }

    public void addClaim(VillageClaim villageClaim) {
        claims.add(villageClaim);
    }

    public boolean isClaimed(int x, int z) {
        for(VillageClaim villageClaim : claims) {
            if(villageClaim.getZ() == z && villageClaim.getX() == x) {
                return true;
            }
        }
        return false;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getLevel() {
        return level;
    }

    public Location getLocation() {
        return location;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Set<VillageClaim> getChunks() {
        return Collections.unmodifiableSet(claims);
    }
}
