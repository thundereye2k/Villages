package com.stefthedev.villages.utilities.general;

import org.bukkit.configuration.file.FileConfiguration;

public enum Message {
    ACCEPT("accept", "&a[Accept]"),
    DENY("deny", "&c[Deny]"),
    PERMISSION("permission", "You do not have permission todo this."),
    PREFIX("prefix", "&e&lVillage: &7"),
    TOOLTIP("tooltip", "&7Click to select."),
    USAGE("usage", "Usage: &b{0}"),
    VILLAGE_CLAIM_CLOSE("village-claim-close", "You are only able to claim land that is connected to your village."),
    VILLAGE_CLAIM_CONFLICT("village-claim-close", "You are not able to claim on this land as it overlaps with a WorldGuard region."),
    VILLAGE_CLAIM_DENY("village-claim-deny", "You are not allowed &b{0} &7as this land belongs to &b&l{1}"),
    VILLAGE_CLAIM_LIMIT("village-claim-limit", "You have reached the claim limit. You can only claim &b25&7 chunks."),
    VILLAGE_CLAIM_OTHER("village-claim-other", "This land has already been claimed by &b{0}&7."),
    VILLAGE_CLAIM_OWNER("village-claim-owner", "You must be owner to claim land for your village."),
    VILLAGE_CLAIM_SELF("village-claim-self", "This land has already been claimed by your village."),
    VILLAGE_CLAIM_SUCCESS("village-claim-success", "You have successfully claimed new land: &b{0}"),
    VILLAGE_CLAIM_WORLD("village-claim-world", "You can only claim in the &b{0} &7world."),
    VILLAGE_CREATE_ALL("village-create-all", "&b{0} &7has established a new village: &a&l{1}"),
    VILLAGE_CREATE_CONFLICT("village-create-conflict", "You are not able to create a village on this land as it overlaps with a WorldGuard Region."),
    VILLAGE_CREATE_EXISTS("village-create-exists", "&7The village &b{0}&7 already exists."),
    VILLAGE_DENY_PLAYER("village-deny-player", "You have denied &b{0}'s &7join invitation."),
    VILLAGE_DENY_TARGET("village-deny-target", "&b{0} &7has denied your invitation."),
    VILLAGE_DISBAND("village-disband", "Are you sure you want to disband your village? Your members may not be happy."),
    VILLAGE_DISBAND_BROADCAST("village-disband-broadcast", "&b{0} &7fell into ruins..."),
    VILLAGE_DISBAND_DENY("village-disband-deny", "You decided to not disband your village."),
    VILLAGE_DISBAND_OWNER("village-disband-owner","You must be owner to disband the village."),
    VILLAGE_DISBAND_STATUS("village-disband-status", "You need to accept or deny disbanding your village."),
    VILLAGE_INVITE_NULL("village-invite-null", "You do not have any pending invitations."),
    VILLAGE_INVITE_OTHER("village-invite-other", "You have been invited by &b{0} &7to join &a&l{1}&7..."),
    VILLAGE_INVITE_OWNER("village-invite-owner", "You cannot invite yourself to the village"),
    VILLAGE_INVITE_SELF("village-invite-self", "You have invited &b{0} &7to join your village."),
    VILLAGE_INVITE_TRUE("village-invite-true", "&b{0}&7 has already been invited to join a village"),
    VILLAGE_LEAVE("village-leave", "You have left the village."),
    VILLAGE_LEAVE_MEMBERS("village-leave-members", "&b{0} &7has decided to leave the town."),
    VILLAGE_LEAVE_OWNER("village-leave-owner", "You must disband your village if you want to leave as you are owner."),
    VILLAGE_LOCATION("village-location", "You have been teleported to your village home."),
    VILLAGE_LOCATION_FALSE("village-location-true", "Your village does not have a home."),
    VILLAGE_LOCATION_OWNER("village-location-owner", "You must be owner of the village to set a home."),
    VILLAGE_LOCATION_OTHER("village-location-other", "You are not able to set your home on other village land."),
    VILLAGE_LOCATION_SET("village-location-set", "You have set a new home at your location."),
    VILLAGE_LOCATION_WILDERNESS("village-location-wilderness", "You are not able to set home in the wilderness."),
    VILLAGE_NULL("village-null", "No villages have been created."),
    VILLAGE_RELOAD("village-reload", "You have successfully reloaded the configuration."),
    VILLAGE_RELOAD_ERROR("village-reload-error", "An error occurred while reloading the configuration."),
    VILLAGE_PLAYER_JOIN("village-player-join", "You have successfully joined &b{0}&7."),
    VILLAGE_PLAYER_TRUE("village-player-true", "You already belong to a village."),
    VILLAGE_PLAYER_FALSE("village-player-false", "You do not belong to a village."),
    VILLAGE_PLAYER_OFFLINE("village-player-offline", "The player &b{0} &7does not seem to be online."),
    VILLAGE_TARGET_MEMBER("village-target-member", "&b{0} &7is already a member of your village."),
    VILLAGE_TARGET_JOIN("village-target-join", "&b{0} &7has joined your village."),
    VILLAGE_TARGET_TRUE("village-target-true", "&b{0} &7already belongs to a village."),
    VILLAGE_TITLE_HEADER("village-title-header", "&e&l{0}"),
    VILLAGE_TITLE_FOOTER("village-title-footer", "&7A peaceful settlement."),
    VILLAGE_TITLE_HEADER_WILDERNESS("village-title-header-wilderness", "&a&l{0}"),
    VILLAGE_TITLE_FOOTER_WILDERNESS("village-title-footer-wilderness", "&7Fresh new land awaits you."),
    VILLAGE_UNCLAIM("village-unclaim", "You have unclaimed an area for your village."),
    VILLAGE_UNCLAIM_FALSE("village-unclaim-false", "This land has not yet been claimed."),
    VILLAGE_UNCLAIM_ONE("village-unclaim-one", "You are not able to unclaim this land as this is the only land that you have."),
    VILLAGE_UNCLAIM_OTHER("village-unclaim-other", "You can't unclaim this land because it belongs to &b{0}&7."),
    VILLAGE_UNCLAIM_OWNER("village-unclaim-owner", "You must be owner to unclaim your village land.");

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
