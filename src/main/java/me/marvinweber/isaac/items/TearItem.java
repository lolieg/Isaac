package me.marvinweber.isaac.items;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.entities.TearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TearItem extends Item {
    public TearItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent

        if (!world.isClient) {
            Player player = Isaac.game.getPlayer(user);
            if (player == null) return TypedActionResult.fail(itemStack);
            player.playerEntity.getItemCooldownManager().set(this, (int) player.playerStats.tears.getCurrentRounded());
            spawnTearEntity(world, user, itemStack, user.getYaw());

            for (IsaacItem item :
                    player.items) {
                item.tearFireEffect(this, player, world, itemStack);
            }

        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    public void spawnTearEntity(World world, PlayerEntity user, ItemStack itemStack, float yaw) {
        TearEntity tearEntity;
        tearEntity = new TearEntity(world, user);
        tearEntity.setItem(itemStack);
        tearEntity.setProperties(user, user.getPitch(), yaw, 0.0F, 0F, 0F);
        world.spawnEntity(tearEntity);
    }


}
