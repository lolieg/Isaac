package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class TearsStat extends Stat {
    public float tps;

    public TearsStat(float base) {
        super(base, base);
        this.tps = getTearsPerSecond(false);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        this.current = base;
        float totalTears = 0;
        float tearDelayModifier = 0;
        float tearDelayUncapped = 0;
        boolean innerEyeModifier = false;
        for (IsaacItem item:
                items) {
            if(item.statModifiers.get("tears") != null)
                totalTears += item.statModifiers.get("tears");
            if(item.statModifiers.get("tearDelay") != null)
                tearDelayModifier += item.statModifiers.get("tearDelay");
            if(item.statModifiers.get("tearDelayUncapped") != null)
                tearDelayUncapped += item.statModifiers.get("tearDelayUncapped");
            if(item.statModifiers.get("innerEyeModifier") != null)
                innerEyeModifier = true;
        }

        if (totalTears >= 0) {
            this.current += 13 - 6 * Math.sqrt(totalTears * 1.3 + 1);
        }
        if (totalTears < 0 && totalTears > -0.77) {
            this.current += 13 - 6 * Math.sqrt(totalTears * 1.3 + 1) - 6 * totalTears;
        }
        if (totalTears <= -0.77) {
            this.current += 13 - 6 * totalTears;
        }
        this.current = (float) Math.round(this.current);

        this.current += tearDelayModifier;
        if (this.current < 3) {
            this.current = 3;
        }

        this.current += tearDelayUncapped;
        if (this.current < 0) {
            this.current = 0;
        }
        this.tps = getTearsPerSecond(false);
        if (innerEyeModifier){
            this.tps *= 0.51f;
            this.current = this.getDelay();
        }
    }

    public float getTearsPerSecond(boolean round) {
        if(round)
            return Math.round((20 / (this.current + 1)) * 100) / 100f;
        return 20 / (this.current + 1);
    }

    public float getDelay() {
        return Math.round((20 / this.tps - 1) * 100) / 100f;
    }
}
