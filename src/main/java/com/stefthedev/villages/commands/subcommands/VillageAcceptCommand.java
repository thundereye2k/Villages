package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
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
            village.getMembers().forEach(uuid -> {
                Player member = plugin.getServer().getPlayer(uuid);
                if(member != null) {
                    member.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_TARGET_JOIN.toString()
                            .replace("{0}", member.getName())
                    );
                }
            });
            villageManager.getInvite().remove(player.getUniqueId());
            village.getMembers().add(player.getUniqueId());
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_JOIN.toString()
                    .replace("{0}", village.getName())
            );
        }
    }
}
