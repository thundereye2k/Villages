package com.stefthedev.villages.hooks;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.stefthedev.villages.utilities.Hook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardHook extends Hook {

    public WorldGuardHook() {
        super("WorldGuard");
    }

    public boolean conflict(Player player) {
        WorldGuardPlatform worldGuard = WorldGuard.getInstance().getPlatform();
        RegionContainer regionContainer = worldGuard.getRegionContainer();
        World world = worldGuard.getWorldByName(player.getWorld().getName());
        RegionManager regionManager = regionContainer.get(world);
        Location location = player.getLocation();

        if(regionManager == null) return false;

        ApplicableRegionSet protectedRegions = regionManager.getApplicableRegions(
                BlockVector3.at(location.getX(), location.getY(), location.getZ())
        );

        for(ProtectedRegion protectedRegion : protectedRegions) {
            if(!protectedRegion.getOwners().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }
}
