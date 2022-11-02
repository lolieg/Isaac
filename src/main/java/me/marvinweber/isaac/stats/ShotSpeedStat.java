package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class ShotSpeedStat extends Stat {
    public ShotSpeedStat(float base) {
        super(base, base);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        super.calculate(items, "shotSpeed");


        boolean myReflectionMultiplier = false;
        for (IsaacItem item:
                items) {
            if(item.statModifiers.get("myReflectionMultiplier") != null)
                myReflectionMultiplier = true;
        }

        if(myReflectionMultiplier) {
            this.current *= 1.6;
        }

        if(this.current < .6f) {
            this.current = .6f;
        }
    }
}
