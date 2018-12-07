package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.villages.Village;
import org.bukkit.entity.Player;

public class VillageLeaveCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageLeaveCommand(Main plugin) {
        super("leave", "leave", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player.getPlayer());

        if(village.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LEAVE_OWNER.toString());
            return;
        }

        if(village.getMembers().contains(player.getUniqueId())) {
            villageManager.getInvite().remove(player.getUniqueId());
            village.getMembers().forEach(uuid -> {
                Player member = plugin.getServer().getPlayer(uuid);
                if(member != null) {
                    member.sendMessage(Message.PREFIX.toString() +  Message.VILLAGE_LEAVE_MEMBERS.toString().replace("{0}", player.getName()));
                }
            });
            village.getMembers().remove(player.getUniqueId());
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_LEAVE.toString());
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
