package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class DamageStat extends Stat {
    public DamageStat(float base) {
        super(base, base);
    }

    @Override
    void calculate(List<IsaacItem> items) {
        this.current = this.base;
        float totalDmgUps = 0;
        float totalFlatDmgUps = 0;
        boolean cricketsHeadMultiplier = false;
        for (IsaacItem item :
                items) {
            if(item.statModifiers.get("damage") != null)
                totalDmgUps += item.statModifiers.get("damage");
            if(item.statModifiers.get("damageFlat") != null)
                totalFlatDmgUps += item.statModifiers.get("damageFlat");
            if(item.statModifiers.get("cricketsHeadMultiplier") != null)
                cricketsHeadMultiplier = true;
        }
        this.current = (float) (this.base * Math.sqrt(totalDmgUps * 1.2 + 1) + totalFlatDmgUps);
        if (cricketsHeadMultiplier) {
            this.current *= 1.5;
        }
    }
}
