package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class LuckStat extends Stat {
    public LuckStat(float base) {
        super(base, base);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        super.calculate(items, "luck");
    }
}
