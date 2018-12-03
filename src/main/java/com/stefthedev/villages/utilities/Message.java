package com.stefthedev.villages.utilities;

import org.bukkit.configuration.file.FileConfiguration;

public enum Message {
    PREFIX("prefix", "&e&lVillage: &7"),
    USAGE("usage", "Usage: &b{usage}"),
    VILLAGE_CLAIM_DENY("village-claim-deny", "You are not allowed &b{0} &7as this land belongs to &b&l{1}"),
    VILLAGE_CLAIM_LIMIT("village-claim-limit", "You have reached the claim limit. You can only claim &b25&7 chunks."),
    VILLAGE_CLAIM_OTHER("village-claim-other", "This land has already been claimed by &b{0}&7."),
    VILLAGE_CLAIM_OWNER("village-claim-owner", "You must be owner to claim land for your village."),
    VILLAGE_CLAIM_SELF("village-claim-self", "This land has already been claimed by your village."),
    VILLAGE_CLAIM_SUCCESS("village-claim-success", "You have successfully claimed new land: &b{0}"),
    VILLAGE_CREATE_ALL("village-create-all", "&b{0} &7has established a new village: &a&l{1}"),
    VILLAGE_DENY_PLAYER("village-deny-player", "You have denied &b{0}'s &7join invitation."),
    VILLAGE_DENY_TARGET("village-deny-target", "&b{0} &7has denied your invitation."),
    VILLAGE_DISBAND_BROADCAST("village-disband-broadcast", "&b{0} &7fell into ruins..."),
    VILLAGE_DISBAND_OWNER("village-disband-owner","You must be owner to disband the village."),
    VILLAGE_INVITE_OTHER("village-invite-other", "You have been invited by &b{0} &7to join &a&l{1}&7..."),
    VILLAGE_INVITE_OWNER("village-invite-owner", "You cannot invite yourself to the village"),
    VILLAGE_INVITE_SELF("village-invite-self", "You have invited &b{0} &7to join your village."),
    VILLAGE_INVITE_TRUE("village-invite-true", "&b{0}&7 has already been invited to join a village"),
    VILLAGE_PLAYER_JOIN("village-player-join", "You have successfully joined &b{0}&7."),
    VILLAGE_PLAYER_TRUE("village-player-true", "You already belong to a village."),
    VILLAGE_PLAYER_FALSE("village-player-false", "You do not belong to a village."),
    VILLAGE_PLAYER_OFFLINE("village-player-offline", "The player &b{0} &7does not seem to be online."),
    VILLAGE_UNCLAIM("village-unclaim", "You have unclaimed an area for your village."),
    VILLAGE_UNCLAIM_FALSE("village-unclaim-false", "This land has not yet been claimed."),
    VILLAGE_UNCLAIM_OTHER("village-unclaim-other", "You can't unclaim this land because it belongs to &b{0}&7."),
    VILLAGE_UNCLAIM_OWNER("village-unclaim-owner", "You must be owner to unclaim your village land."),
    VILLAGE_TARGET_JOIN("village-target-join", "&b{0} &7has joined your village."),
    VILLAGE_TARGET_TRUE("village-invite-true", "&b{0} &7already belongs to a village."),;

    private String path, def;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    @Override
    public String toString() {
        return Chat.color(configuration.getString(path, def));
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }
}
