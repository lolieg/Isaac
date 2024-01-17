package me.marvinweber.isaac.stats;


import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    public final List<HealthManager.Heart> hearts;
    public final DamageStat damage;
    public final TearsStat tears;
    public final RangeStat range;
    public final ShotSpeedStat shotSpeed;
    public final SpeedStat speed;
    public final LuckStat luck;
    public final KnockbackStat knockback;

    public PlayerStats(List<HealthManager.Heart> hearts, float damage, float tears, float range, float shotSpeed, float speed, float luck, float knockback) {
        this.hearts = hearts;
        this.damage = new DamageStat(damage);
        this.tears = new TearsStat(tears);
        this.range = new RangeStat(range);
        this.shotSpeed = new ShotSpeedStat(shotSpeed);
        this.speed = new SpeedStat(speed);
        this.luck = new LuckStat(luck);
        this.knockback = new KnockbackStat(knockback);
    }

    public void calculate(List<IsaacItem> items) {
        damage.calculate(items);
        tears.calculate(items);
        range.calculate(items);
        shotSpeed.calculate(items);
        speed.calculate(items);
        luck.calculate(items);
        knockback.calculate(items);
    }

    public static void serialize(PacketByteBuf packetByteBuf, PlayerStats playerStats) {
        packetByteBuf.writeFloat(playerStats.damage.current);
        packetByteBuf.writeFloat(playerStats.tears.current);
        packetByteBuf.writeFloat(playerStats.range.current);
        packetByteBuf.writeFloat(playerStats.shotSpeed.current);
        packetByteBuf.writeFloat(playerStats.speed.current);
        packetByteBuf.writeFloat(playerStats.luck.current);
        packetByteBuf.writeFloat(playerStats.knockback.current);
    }

    public static PlayerStats deserialize(PacketByteBuf packetByteBuf) {
        return new PlayerStats(new ArrayList<>(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat());
    }
}

