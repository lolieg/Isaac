package me.marvinweber.isaac.items.passive;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.entities.TearEntity;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpoonBenderItem extends IsaacItem {
    public SpoonBenderItem() {
        super(new OwoItemSettings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 3;
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return Text.literal("Spoon Bender").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Homing Shots").formatted(Formatting.ITALIC));
        tooltip.add(Text.literal("Effects: Grants homing Tears.").formatted(Formatting.GOLD));
    }

    @Override
    public void tearTickEffect(TearEntity tearEntity, Player player) {
        if (!tearEntity.world.isClient()) {
            //TODO make homing effect
        }
    }

}
