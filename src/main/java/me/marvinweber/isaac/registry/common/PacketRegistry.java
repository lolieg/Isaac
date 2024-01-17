package me.marvinweber.isaac.registry.common;

import io.wispforest.owo.network.serialization.PacketBufSerializer;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.packets.HealthUpdatePacket;
import me.marvinweber.isaac.packets.MapUpdatePacket;
import me.marvinweber.isaac.packets.StateUpdatePacket;
import me.marvinweber.isaac.packets.StatsUpdatePacket;
import me.marvinweber.isaac.stats.HealthManager;
import me.marvinweber.isaac.stats.PlayerStats;
import net.minecraft.util.Identifier;

public class PacketRegistry {
    public static void initialize() {
        // serializer
        PacketBufSerializer.register(HealthManager.Heart.class, HealthManager.Heart::serialize, HealthManager.Heart::deserialize);
        PacketBufSerializer.register(PlayerStats.class, PlayerStats::serialize, PlayerStats::deserialize);
        PacketBufSerializer.register(Room.class, Room::serialize, Room::deserialize);

        // Packets to client
        Isaac.GAME_CHANNEL.registerClientbound(StateUpdatePacket.class, ((message, access) -> {
            IsaacClient.stateManager.gameState = message.state();
        }));
        Isaac.GAME_CHANNEL.registerClientbound(MapUpdatePacket.class, MapUpdatePacket::onPacket);
        Isaac.PLAYER_CHANNEL.registerClientbound(HealthUpdatePacket.class, HealthUpdatePacket::onPacket);
        Isaac.PLAYER_CHANNEL.registerClientbound(StatsUpdatePacket.class, StatsUpdatePacket::onPacket);

        // Packets to server
    }

}
