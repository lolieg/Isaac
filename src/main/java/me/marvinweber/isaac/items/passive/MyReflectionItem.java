package me.marvinweber.isaac.items.passive;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.BaseEnemyNearbyPredicate;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.entities.TearEntity;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MyReflectionItem extends IsaacItem {
    public MyReflectionItem() {
        super(new OwoItemSettings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 5;
        this.statModifiers.put("myReflectionMultiplier", 0f);
        this.statModifiers.put("range", 1.5f);
    }
    @Override
    public Text getName(ItemStack itemStack) {
        return Text.literal("My Reflection").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Boomerang Tears").formatted(Formatting.ITALIC));
        tooltip.add(Text.literal("Effects: Isaac's tears travel a short distance, then reverse direction and return to Isaac. \n 1.6x+1.5 Range. \n 1.6x Shot Speed Multiplier").formatted(Formatting.GOLD));
    }

    @Override
    public void tearTickEffect(TearEntity tearEntity, Player player) {
        if (!tearEntity.world.isClient() && tearEntity.age > 6) {
            Vec3d vec3d = tearEntity.getPos().subtract(player.playerEntity.getPos()).negate().multiply(0.5f);

            tearEntity.setVelocity(vec3d.x, vec3d.y * 0.2f, vec3d.z, player.playerStats.shotSpeed.current * 0.9f, 2f);
            tearEntity.world.getPlayers().forEach(playerEntity -> ((ServerPlayerEntity) playerEntity).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(tearEntity)));
        }
    }
}
