package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.commands.SubCommand;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class VillageInviteCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageInviteCommand(Main plugin) {
        super("invite", "invite [player]", "Invite a player to your village.", 2);
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

                    TextComponent accept = new TextComponent(Chat.color(Message.ACCEPT.toString()) + " ");
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village accept"));

                    TextComponent deny = new TextComponent(Chat.color(Message.DENY.toString()) + " ");
                    deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
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
