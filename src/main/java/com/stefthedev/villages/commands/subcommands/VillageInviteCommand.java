package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VillageInviteCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageInviteCommand(Main plugin) {
        super("invite", 2);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        } else {
            Player target = plugin.getServer().getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_OFFLINE.toString()
                        .replace("{0}", args[1])
                );
            } else {
                if(village.getMembers().contains(target.getUniqueId())) {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_TRUE.toString()
                            .replace("{0}", target.getName())
                    );
                    return;
                }

                if(village.getOwner().equals(target.getUniqueId())) {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_OWNER.toString());
                    return;
                }

                if(!villageManager.getInvite().containsKey(target.getUniqueId())) {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_SELF.toString()
                            .replace("{0}", target.getName())
                    );
                    target.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_OTHER.toString()
                            .replace("{0}", player.getName())
                            .replace("{1}", village.getName())
                    );

                    TextComponent accept = new TextComponent(ChatColor.GREEN + "[Accept]" + " ");
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village accept"));

                    TextComponent deny = new TextComponent(ChatColor.RED + "[Deny]");
                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village deny"));

                    target.spigot().sendMessage(accept, deny);

                    villageManager.getInvite().put(target.getUniqueId(), village);
                } else {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_INVITE_TRUE.toString()
                            .replace("{0}", target.getName())
                    );
                }
            }
        }
    }
}
