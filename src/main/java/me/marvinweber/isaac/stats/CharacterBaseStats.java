package me.marvinweber.isaac.stats;

import java.util.List;

public class CharacterBaseStats {
    public static PlayerStats getIsaacBaseStats() {
        return new PlayerStats(List.of(new HealthManager.Heart[]{
                new HealthManager.RedHeartContainer(),
                new HealthManager.RedHeartContainer(),
                new HealthManager.RedHeartContainer()}), 3.5f, 0, 6.5f, 1f, 1f, 0f, 0.1f);
    }
}
