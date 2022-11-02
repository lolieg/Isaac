package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class RangeStat extends Stat {
    public RangeStat(float base) {
        super(base, base);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        super.calculate(items, "range");

        boolean numberOneMultiplier = false;
        boolean myReflectionMultiplier = false;
        for (IsaacItem item:
                items) {
            if(item.statModifiers.get("numberOneMultiplier") != null)
                numberOneMultiplier = true;
            if(item.statModifiers.get("myReflectionMultiplier") != null)
                myReflectionMultiplier = true;
        }

        if(numberOneMultiplier) {
            this.current -= this.current * 0.2f;
        }
        if(myReflectionMultiplier) {
            this.current *= 1.6;
        }

        if(this.current < 1) {
            this.current = 1;
        }
    }
}
