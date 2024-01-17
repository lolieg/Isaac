package me.marvinweber.isaac.items.passive;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SadOnionItem extends IsaacItem {

    public SadOnionItem() {
        super(new OwoItemSettings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 1;
        statModifiers.put("tears", .7f);
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return Text.literal("The Sad Onion").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Tears Up").formatted(Formatting.ITALIC));
        tooltip.add(Text.literal("Effects: +0.7 Tears.").formatted(Formatting.GOLD));
    }

}
