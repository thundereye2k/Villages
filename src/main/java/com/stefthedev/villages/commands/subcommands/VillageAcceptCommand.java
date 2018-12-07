package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.entity.Player;

public class VillageAcceptCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageAcceptCommand(Main plugin) {
        super("accept", "accept", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getInvite().get(player.getUniqueId());
        if(village != null) {
            villageManager.getInvite().remove(player.getUniqueId());

            village.getMembers().forEach(uuid -> {
                Player member = plugin.getServer().getPlayer(uuid);
                if(member != null) {
                    member.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_TARGET_JOIN.toString()
                            .replace("{0}", member.getName())
                    );
                }
            });
            village.getMembers().add(player.getUniqueId());
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_JOIN.toString()
                    .replace("{0}", village.toString())
            );
            return;
        }

        village = villageManager.getDisband().get(player.getUniqueId());
        if(village != null) {
            plugin.getServer().broadcastMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_BROADCAST.toString()
                    .replace("{0}", village.toString())
            );
            villageManager.remove(village);
            villageManager.getDisband().remove(player.getUniqueId());
            return;
        }

        player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_NULL.toString());
    }
}
