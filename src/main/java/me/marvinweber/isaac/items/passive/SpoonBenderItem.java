package me.marvinweber.isaac.items.passive;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.BaseEnemyNearbyPredicate;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.entities.TearEntity;
import me.marvinweber.isaac.items.IsaacItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpoonBenderItem extends IsaacItem {
    public SpoonBenderItem() {
        super(new Item.Settings().group(Isaac.ISAAC_ITEMS).maxCount(1));
        this.ID = 3;
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new LiteralText("Spoon Bender").formatted(Formatting.AQUA, Formatting.BOLD);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Homing Shots").formatted(Formatting.ITALIC));
        tooltip.add(new LiteralText("Effects: Grants homing Tears.").formatted(Formatting.GOLD));
    }

    @Override
    public void tearTickEffect(TearEntity tearEntity, Player player) {
        if (!tearEntity.world.isClient() && tearEntity.age > 4) {
            ServerWorld serverWorld = (ServerWorld) tearEntity.world;
            List<? extends BaseEntity> entities = serverWorld.getEntitiesByType(TypeFilter.instanceOf(BaseEntity.class), new BaseEnemyNearbyPredicate(tearEntity.getPos()));
            if (entities.size() > 0 && player != null) {
                BaseEntity enemy = entities.get(0);
                Vec3d vec3d = tearEntity.getPos().subtract(enemy.getPos()).negate().multiply(0.05f);

                tearEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z, player.playerStats.shotSpeed.current, 2f);
                tearEntity.world.getPlayers().forEach(playerEntity -> ((ServerPlayerEntity) playerEntity).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(tearEntity)));
            }
        }
    }
}
