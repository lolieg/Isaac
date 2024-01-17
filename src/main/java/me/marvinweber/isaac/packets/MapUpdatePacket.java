package me.marvinweber.isaac.packets;

import io.wispforest.owo.network.ClientAccess;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.stats.HealthManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

public record MapUpdatePacket(List<Room> rooms, Room current) {
    @Environment(EnvType.CLIENT)
    public static void onPacket(MapUpdatePacket message, ClientAccess access) {
        IsaacClient.map.setRooms((ArrayList<Room>) message.rooms());
        IsaacClient.map.setCurrentRoom(message.current());
    }
}
