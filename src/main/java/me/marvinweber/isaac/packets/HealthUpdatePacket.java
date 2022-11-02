package me.marvinweber.isaac.packets;

import io.netty.buffer.Unpooled;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.stats.HealthManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;


public class HealthUpdatePacket {
    public static PacketByteBuf create(HealthManager healthManager) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());

        byteBuf.writeCollection(healthManager.hearts, ((packetByteBuf, heart) -> {
            packetByteBuf.writeString(heart.getClass().getName());
            packetByteBuf.writeEnumConstant(heart.heartSate);
        }));
        return byteBuf;
    }

    @Environment(EnvType.CLIENT)
    public static void onPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender responseSender) {
        List<HealthManager.Heart> hearts = byteBuf.readList(packetByteBuf -> {
            String type = packetByteBuf.readString();

            HealthManager.HeartSate heartSate = packetByteBuf.readEnumConstant(HealthManager.HeartSate.class);
            if (type.equals(HealthManager.Heart.class.getName())) {
                return new HealthManager.Heart(heartSate);
            }
            if (type.equals(HealthManager.RedHeartContainer.class.getName())) {
                return new HealthManager.RedHeartContainer(heartSate);
            }
            if (type.equals(HealthManager.SoulHeart.class.getName())) {
                return new HealthManager.SoulHeart(heartSate);
            }

            return new HealthManager.Heart(heartSate);
        });
        if (IsaacClient.healthManager == null) {
            IsaacClient.healthManager = new HealthManager();
        }
        IsaacClient.healthManager.hearts = new ArrayList<>(hearts);
    }
}
