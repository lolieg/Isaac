package me.marvinweber.isaac.items.passive;

import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NumberOneItem extends IsaacItem {
    public NumberOneItem() {
        super(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
        this.ID = 6;
        this.statModifiers.put("tears", 1.5f);
        this.statModifiers.put("range", -1.5f);
        this.statModifiers.put("numberOneMultiplier", 0f);
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new LiteralText("Number One").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Tears Up + Range Down").formatted(Formatting.ITALIC));
        tooltip.add(new LiteralText("Effects: +1.5 Tears.\n-1.5 Range and -20% range multiplier").formatted(Formatting.GOLD));
    }
}
