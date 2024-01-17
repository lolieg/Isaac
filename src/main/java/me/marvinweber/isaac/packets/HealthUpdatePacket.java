package me.marvinweber.isaac.packets;

import io.netty.buffer.Unpooled;
import io.wispforest.owo.network.ClientAccess;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.stats.HealthManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public record HealthUpdatePacket(List<HealthManager.Heart> hearts) {
    @Environment(EnvType.CLIENT)
    public static void onPacket(HealthUpdatePacket message, ClientAccess access) {

        if (IsaacClient.healthManager == null) {
            IsaacClient.healthManager = new HealthManager();
        }
        IsaacClient.healthManager.hearts = new ArrayList<>(message.hearts());
    }
}
