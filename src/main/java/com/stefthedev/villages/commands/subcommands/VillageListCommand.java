package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.menus.VillageListMenu;
import com.stefthedev.villages.utilities.commands.SubCommand;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Message;
import org.bukkit.entity.Player;

public class VillageListCommand extends SubCommand {

    private VillageManager villageManager;
    private Main plugin;

    public VillageListCommand(Main plugin) {
        super("list", "list", 1);
        this.plugin = plugin;
        this.villageManager = plugin.getVillageManager();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        int pages = (int) Math.ceil((double) villageManager.getSet().size() / 45);
        if(villageManager.getSet().size() <= 0) {
            player.sendMessage(Message.PREFIX.toString() + Message.VILLAGE_NULL.toString());
            return;
        }
        if(args.length == 1) {
            new VillageListMenu(player, plugin, 1).build().open(player);
        } else {
            int index = Integer.parseInt(args[1]);
            if (index <= pages) {
                new VillageListMenu(player, plugin, index).build().open(player);
            }
        }
    }
}
