package me.marvinweber.isaac.registry.common;

import me.marvinweber.isaac.Isaac;
import net.minecraft.util.Identifier;

public class PacketRegistry {
    public static final Identifier SPAWN_PACKET = new Identifier(Isaac.MOD_ID, "spawn_packet");
    public static final Identifier STATS_UPDATE_PACKET = new Identifier(Isaac.MOD_ID, "stats_packet");
    public static final Identifier STATS_REMOVE_PACKET = new Identifier(Isaac.MOD_ID, "stats_remove_packet");
    public static final Identifier HEALTH_UPDATE_PACKET = new Identifier(Isaac.MOD_ID, "health_update_packet");
    public static final Identifier HEALTH_REMOVE_PACKET = new Identifier(Isaac.MOD_ID, "health_remove_packet");
}
