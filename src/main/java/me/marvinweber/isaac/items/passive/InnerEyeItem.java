package me.marvinweber.isaac.items.passive;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.items.TearItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InnerEyeItem extends IsaacItem {
    public InnerEyeItem() {
        super(new OwoItemSettings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 2;

        this.statModifiers.put("innerEyeModifier", 0f);
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return Text.literal("The Inner Eye").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Triple Shot").formatted(Formatting.ITALIC));
        tooltip.add(Text.literal("Effects: Isaac fires a spread of three tears at once. \n Tears fired per second * 0.51.").formatted(Formatting.GOLD));
    }

    @Override
    public void tearFireEffect(TearItem tearItem, Player player, World world, ItemStack itemStack) {
        if (player.items.stream().anyMatch(c -> c instanceof InnerEyeItem)) {
            tearItem.spawnTearEntity(world, player.playerEntity, itemStack, player.playerEntity.getYaw() + 6);
            tearItem.spawnTearEntity(world, player.playerEntity, itemStack, player.playerEntity.getYaw() - 6);
        }
    }
}
