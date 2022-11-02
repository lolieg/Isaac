package me.marvinweber.isaac.packets;

import io.netty.buffer.Unpooled;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.stats.PlayerStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;

public class StatsUpdatePacket {
    public static PacketByteBuf create(PlayerStats playerStats) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());

        byteBuf.writeFloat(playerStats.speed.current);
        byteBuf.writeFloat(playerStats.damage.current);
        byteBuf.writeFloat(playerStats.tears.current);
        byteBuf.writeFloat(playerStats.range.current);
        byteBuf.writeFloat(playerStats.shotSpeed.current);
        byteBuf.writeFloat(playerStats.luck.current);
        byteBuf.writeFloat(playerStats.knockback.current);

        return byteBuf;
    }

    @Environment(EnvType.CLIENT)
    public static void onPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender responseSender) {
        float speed = byteBuf.readFloat();
        float damage = byteBuf.readFloat();
        float tears = byteBuf.readFloat();
        float range = byteBuf.readFloat();
        float shotSpeed = byteBuf.readFloat();
        float luck = byteBuf.readFloat();
        float knockback = byteBuf.readFloat();

        if (IsaacClient.playerStats == null) {
            IsaacClient.playerStats = new PlayerStats(new ArrayList<>(), damage, tears, range, shotSpeed, speed, luck, knockback);
            return;
        }
        IsaacClient.playerStats.speed.current = speed;
        IsaacClient.playerStats.damage.current = damage;
        IsaacClient.playerStats.tears.current = tears;
        IsaacClient.playerStats.range.current = range;
        IsaacClient.playerStats.shotSpeed.current = shotSpeed;
        IsaacClient.playerStats.luck.current = luck;
        IsaacClient.playerStats.knockback.current = knockback;

    }
}
