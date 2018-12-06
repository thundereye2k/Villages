package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.entity.Player;

public class VillageDenyCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageDenyCommand(Main plugin) {
        super("deny", "deny", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getInvite().get(player.getUniqueId());
        if(village != null) {
            Player target = plugin.getServer().getPlayer(village.getOwner());
            if(target != null) {
                target.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DENY_TARGET.toString()
                        .replace("{0}", target.getName())
                );
            }
            villageManager.getInvite().remove(player.getUniqueId());
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DENY_PLAYER.toString()
                    .replace("{0}", village.toString())
            );
            return;
        }

        village = villageManager.getDisband().get(player.getUniqueId());
        if(village != null) {
            plugin.getServer().broadcastMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_DENY.toString()
                    .replace("{0}", village.toString())
            );
            villageManager.getDisband().remove(player.getUniqueId());
            return;
        }

        player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_NULL.toString());
    }
}
