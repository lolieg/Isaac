package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Inject(at = @At("TAIL"), method = "onPlayerCollision")
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        ItemStack itemStack = this.getStack();
        try {
            IsaacItem item = (IsaacItem) itemStack.getItem();
            Player player1 = Player.findPlayer(player.getUuid());
            if (player1 == null || item.ID == 0) return;
            player1.addItem(ItemRegistry.ITEMS.get(item.ID));
            if (player1.game.currentRoom.spawner == null || player1.game.currentRoom.spawner.multipleChoiceItems.size() <= 1) return;
            ArrayList<ItemEntity> itemEntities =  player1.game.currentRoom.spawner.multipleChoiceItems;
            if (itemEntities.contains((ItemEntity) (Object)this)) {
                player1.game.currentRoom.spawner.multipleChoiceItems.remove((ItemEntity) (Object)this);
                player1.game.currentRoom.spawner.multipleChoiceItems.forEach(itemEntity -> itemEntity.remove(RemovalReason.KILLED));
                player1.game.currentRoom.spawner.multipleChoiceItems.clear();
            }
        } catch (Exception ignored){

        }


    }
}
