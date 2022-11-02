package me.marvinweber.isaac.stats;

import me.marvinweber.isaac.items.IsaacItem;

import java.util.List;

public class Stat {
    public float base;
    public float current;

    public Stat(float base, float current) {
        this.base = base;
        this.current = current;
    }

    void calculate(List<IsaacItem> items) {

    }
    void calculate(List<IsaacItem> items, String name) {
        this.current = this.base;
        for (IsaacItem item :
                items) {
            if(item.statModifiers.get(name) != null)
                this.current += item.statModifiers.get(name);
        }
    }

    public float getCurrentRounded() {
        return (float) (Math.round(current * 100.0) / 100.0);
    }
}
