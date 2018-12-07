package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class VillageDisbandCommand extends SubCommand {

    private VillageManager villageManager;

    public VillageDisbandCommand(Main plugin) {
        super("disband", "disband", 1);
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_OWNER.toString());
            } else {
                if(!villageManager.getDisband().containsKey(player.getUniqueId())) {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND.toString());

                    TextComponent accept = new TextComponent(Chat.color(Message.ACCEPT.toString()) + " ");
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village accept"));

                    TextComponent deny = new TextComponent(Chat.color(Message.DENY.toString()) + " ");
                    deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village deny"));

                    player.spigot().sendMessage(accept, deny);
                    villageManager.getDisband().put(player.getUniqueId(), village);
                } else {
                    player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_DISBAND_STATUS.toString());
                }
            }
        } else {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_PLAYER_FALSE.toString());
        }
    }
}
