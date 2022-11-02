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

public class CricketsHeadItem extends IsaacItem {
    public CricketsHeadItem() {
        super(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
        this.ID = 4;
        this.statModifiers.put("cricketsHeadMultiplier", 0f);
        this.statModifiers.put("damage", 0.5f);
    }
    @Override
    public Text getName(ItemStack itemStack) {
        return new LiteralText("Cricket´s Head").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("DMG Up").formatted(Formatting.ITALIC));
        tooltip.add(new LiteralText("Effects: +0.5 Damage. \n x1.5 Damage Multiplier.").formatted(Formatting.GOLD));
    }
}
