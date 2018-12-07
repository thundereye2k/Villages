package com.stefthedev.villages.menus;

import com.stefthedev.villages.Main;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.utilities.villages.Village;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VillageListMenu extends Menu {

    private final Player player;
    private VillageManager villageManager;
    private final int page;

    private Main plugin;

    public VillageListMenu(Player player, Main plugin, int page) {
        super(plugin, "Villages",54);
        this.player = player;
        this.villageManager = plugin.getVillageManager();
        this.page = page;
        this.plugin = plugin;
    }

    @Override
    public Menu build() {
        List<Village> villageList = new ArrayList<>(villageManager.getSet());
        for (int i = 0; i < villageList.size(); i++) {
            if(i >= 45 ) break;
            Village village = villageList.get(i + ((page - 1) * 45));
            if(village != null) {
                addItems(new MenuItem(i, village(village, i + 1).build(null), null));
            }
        }

        pageSelect();
        return this;
    }

    private void pageSelect() {
        int pages = (int) Math.ceil((double) villageManager.getSet().size() / 45);
        MenuItem next = new MenuItem(50,
                page("Next").build(null),
                inventoryClickEvent -> new VillageListMenu(player, plugin, page + 1).build().open(player));
        MenuItem previous = new MenuItem(48,
                page("Previous").build(null),
                inventoryClickEvent -> new VillageListMenu(player, plugin, page - 1).build().open(player));

        addItems(new MenuItem(49, close().build(null), inventoryClickEvent -> player.closeInventory()));

        if(pages == page && page != 1) {
            addItems(previous);
            return;
        }
        if(page == 1 && pages > 1) {
            addItems(next);
        }
        if(page < pages && page > 1) {
            addItems(next, previous);
        }
    }

    private Item village(Village village, int index) {
        String owner = village.getOwner().toString();
        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(village.getOwner());
        if(offlinePlayer != null) {
            owner = offlinePlayer.getName();
        }
        return new Item(Chat.color("&a&l" + String.valueOf(index) + ". " + village.toString()), Material.GRASS, (short) 0,
                new String[] {
                    "",
                    "&7Owner: &f" + owner,
                    "&7Land: &f" + village.getChunks().size(),
                    "&7Members: &f" + village.getMembers().size()
                }
        );
    }

    private Item close() {
        return new Item(Chat.color("&c&lClose"), Material.BARRIER, (short) 0, new String[] {
                "&7Click to close the menu."
        });
    }

    private Item page(String name) {
        return new Item(ChatColor.WHITE + name, Material.ARROW, (short) 0, new String[] {
                "&7Click to go the " + name.toLowerCase() + " page."
        });
    }
}
