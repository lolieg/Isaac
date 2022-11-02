package me.marvinweber.isaac.items;

import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.entities.TearEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;

public abstract class IsaacItem extends Item {
    public int ID;

    public HashMap<String, Float> statModifiers = new HashMap<>();

    public IsaacItem(Settings settings) {
        super(settings);
        this.ID = 0;
    }

    public void tearTickEffect(TearEntity tearEntity, Player player) {

    }

    public void tearFireEffect(TearItem tearItem, Player player, World world, ItemStack itemStack) {

    }
}
