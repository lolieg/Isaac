package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.entities.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthManager {
    Player player;
    public ArrayList<Heart> hearts;

    public HealthManager(Player player, List<Heart> hearts) {
        this.player = player;
        this.hearts = new ArrayList<>(hearts);
    }

    public HealthManager() {
        this.hearts = new ArrayList<>();
    }

    public void update() {
        this.hearts.removeIf(heart -> heart instanceof SoulHeart && heart.heartSate == HeartSate.EMPTY);
        if (!hasHearts())
            player.onNoHealth();
    }

    private boolean hasHearts() {
        boolean hasHearts = false;
        for (Heart heart :
                this.hearts) {
            if (heart.heartSate.value > 0) {
                hasHearts = true;
                break;
            }
        }
        return hasHearts;
    }
    /**
     * Damage Player.
     *
     * @param amount the amount
     */
    public void damage(int amount) {
        int damageApplied = 0;
        Collections.reverse(this.hearts);
        while (damageApplied < amount) {
            for (Heart heart :
                    this.hearts) {
                if (heart.heartSate == HeartSate.FULL || heart.heartSate == HeartSate.HALF) {
                    heart.decrement();
                    damageApplied++;
                    break;
                }
            }
        }
        Collections.reverse(this.hearts);
    }
    /**
     * Heal Player. MAX AMOUNT 2. EXECUTE MULTIPLE TIMES FOR MORE.
     * // FIXME: 16.12.2021 Works, but only 1 heart can be healed per invoke.
     * @param amount the amount
     */
    public void heal(int amount) {
        int healed = 0;
        while (healed < amount) {
            if (this.hearts.get(this.hearts.size() - 1) instanceof SoulHeart) continue;
            this.hearts.get(this.hearts.size() - 1).increment();
            healed++;
        }
    }

    public int getHealthAmount() {
        int health = 0;
        for (Heart h :
                this.hearts) {
            health += h.heartSate.value;
        }
        return health;
    }
    public int getHeartsAmount() {
        return this.hearts.size();
    }

    public static class Heart {
        public int ID = 0;
        public HeartSate heartSate;

        public Heart(HeartSate heartSate) {
            this.heartSate = heartSate;
        }

        public void decrement() {
            if (this.heartSate == HeartSate.FULL) {
                this.heartSate = HeartSate.HALF;
                return;
            }
            if (this.heartSate == HeartSate.HALF) {
                this.heartSate = HeartSate.EMPTY;
            }
        }
        public void increment() {
            if (this.heartSate == HeartSate.EMPTY) {
                this.heartSate = HeartSate.HALF;
                return;
            }
            if (this.heartSate == HeartSate.HALF) {
                this.heartSate = HeartSate.FULL;
            }
        }
    }

    public static class RedHeartContainer extends Heart{
        public int ID = 1;

        public RedHeartContainer() {
            super(HeartSate.FULL);
        }
        public RedHeartContainer(HeartSate heartSate) {
            super(heartSate);
        }
    }

    public static class SoulHeart extends Heart{
        public int ID = 2;
        public SoulHeart(HeartSate heartSate) {
            super(heartSate);
        }
        public SoulHeart() {
            super(HeartSate.FULL);
        }
    }
    public enum HeartSate {
        EMPTY(0),
        HALF(1),
        FULL(2);

        int value;
        HeartSate(int i) {
            this.value = i;
        }
    }
}
