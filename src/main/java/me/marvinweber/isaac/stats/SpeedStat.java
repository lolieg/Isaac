package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class SpeedStat extends Stat {
    public SpeedStat(float base) {
        super(base, base);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        super.calculate(items, "speed");
        if(this.current > 2) {
            this.current = 2;
        }
        if(this.current < 0.5) {
            this.current = 0.5f;
        }
    }
}
