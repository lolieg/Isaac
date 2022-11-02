package me.marvinweber.isaac.packets;

import io.netty.buffer.Unpooled;
import me.marvinweber.isaac.client.IsaacClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

public class HealthRemovePacket {
    public static PacketByteBuf create() {
        return new PacketByteBuf(Unpooled.buffer());
    }

    @Environment(EnvType.CLIENT)
    public static void onPacket() {
        IsaacClient.healthManager = null;

    }
}
