package me.marvinweber.isaac.registry.client;

import me.marvinweber.isaac.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class PacketRegistry {


    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(me.marvinweber.isaac.registry.common.PacketRegistry.STATS_UPDATE_PACKET, StatsUpdatePacket::onPacket);
        ClientPlayNetworking.registerGlobalReceiver(me.marvinweber.isaac.registry.common.PacketRegistry.STATS_REMOVE_PACKET, (a, b, c, d) -> StatsRemovePacket.onPacket());
        ClientPlayNetworking.registerGlobalReceiver(me.marvinweber.isaac.registry.common.PacketRegistry.HEALTH_UPDATE_PACKET, HealthUpdatePacket::onPacket);
        ClientPlayNetworking.registerGlobalReceiver(me.marvinweber.isaac.registry.common.PacketRegistry.HEALTH_REMOVE_PACKET, (a, b, c, d) -> HealthRemovePacket.onPacket());
        receiveEntityPacket();
    }

    private static void receiveEntityPacket() {
        ClientSidePacketRegistryImpl.INSTANCE.register(me.marvinweber.isaac.registry.common.PacketRegistry.SPAWN_PACKET, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
