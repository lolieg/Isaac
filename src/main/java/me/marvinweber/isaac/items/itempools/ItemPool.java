package me.marvinweber.isaac.items.itempools;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Random;

public class ItemPool {
    public String name;
    public ArrayList<Item> items;

    public ItemPool(String name, ArrayList<Item> items) {
        this.name = name;
        this.items = items;
    }

    public Item pickRandomItemWeighted(Random random) {
        double totalWeight = 0.0;
        for (Item item :
                items) {
            totalWeight += Double.parseDouble(item.weight);
        }

        int idx = 0;
        for (double r = random.nextDouble() * totalWeight; idx < items.size() - 1; ++idx) {
            r -= Double.parseDouble(items.get(idx).weight);
            if (r <= 0.0) break;
        }
        Item item = items.get(idx);
        this.items.remove(item);
        this.items.add(new Item("25", "1.0"));
        return item;
    }

    public IsaacItem getMinecraftItem(Item item) {
        return ItemRegistry.ITEMS.get(Integer.parseInt(item.id));
    }

    public static class Item {
        public String id;
        public String weight;

        public Item(String id, String weight) {
            this.id = id;
            this.weight = weight;
        }
    }
}
