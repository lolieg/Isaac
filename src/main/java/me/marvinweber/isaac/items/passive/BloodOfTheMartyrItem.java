package me.marvinweber.isaac.items.passive;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodOfTheMartyrItem extends IsaacItem {

    public BloodOfTheMartyrItem() {
        super(new Settings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 51;

        statModifiers.put("damage", 1f);
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new LiteralText("Blood of the Martyr").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("DMG Up").formatted(Formatting.ITALIC));
        tooltip.add(new LiteralText("Effects: +1 Damage.").formatted(Formatting.GOLD));
    }

}
