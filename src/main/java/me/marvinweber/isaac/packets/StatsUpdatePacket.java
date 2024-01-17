package me.marvinweber.isaac.packets;

import io.netty.buffer.Unpooled;
import io.wispforest.owo.network.ClientAccess;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.stats.PlayerStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;

public record StatsUpdatePacket(PlayerStats playerStats) {
    @Environment(EnvType.CLIENT)
    public static void onPacket(StatsUpdatePacket message, ClientAccess access) {
        IsaacClient.playerStats = message.playerStats();
    }
}
