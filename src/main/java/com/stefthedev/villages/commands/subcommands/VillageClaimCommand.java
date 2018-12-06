package com.stefthedev.villages.commands.subcommands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.hooks.WorldGuardHook;
import com.stefthedev.villages.managers.HookManager;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import org.bukkit.entity.Player;


public class VillageClaimCommand extends SubCommand {

    private VillageManager villageManager;
    private HookManager hookManager;

    public VillageClaimCommand(Main plugin) {
        super("claim", "claim",1);
        this.villageManager = plugin.getVillageManager();
        this.hookManager = plugin.getHookManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        WorldGuardHook worldGuardHook = (WorldGuardHook) hookManager.getObject("WorldGuard");

        if(worldGuardHook.isEnabled() && worldGuardHook.conflict(player)) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_CONFLICT.toString());
            return;
        }

        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(player.getWorld() != villageManager.getWorld()) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_WORLD.toString()
                        .replace("{0}", villageManager.getWorld().getName())
                );
                return;
            }
            if(village.getChunks().size() >= villageManager.getSize()) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_LIMIT.toString());
                return;
            }
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_OWNER.toString());
                return;
            }
            village = villageManager.isClaimed(player.getLocation().getChunk());
            if(village == villageManager.getVillage(player)) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_SELF.toString());
            } else if(village == null) {
                village = villageManager.getVillage(player);
                if(!villageManager.allowClaim(player)) {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_CLOSE.toString());
                    return;
                }
                village.addChunk(player.getLocation().getChunk());
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_SUCCESS.toString()
                        .replace("{0}", player.getLocation().getChunk().getX() + "," + player.getLocation().getChunk().getZ())
                );
            } else {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_CLAIM_OTHER.toString()
                        .replace("{0}", village.toString())
                );
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
