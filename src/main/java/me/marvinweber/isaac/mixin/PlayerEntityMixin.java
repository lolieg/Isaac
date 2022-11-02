package me.marvinweber.isaac.mixin;

import com.mojang.authlib.GameProfile;
import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.Player;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract GameProfile getGameProfile();

    @Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;", cancellable = true)
    private void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
            cir.cancel();
    }

    @Inject(at = @At("HEAD"), method = "jump", cancellable = true)
    private void jump(CallbackInfo ci) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
            ci.cancel();
    }

//    @Inject(at = @At("HEAD"), method = "collideWithEntity")
//    private void collideWithEntity(Entity entity, CallbackInfo ci) {
//        try {
//            BaseEntity entity1 = (BaseEntity) entity;
//            Player player = Player.findPlayer(this.getGameProfile().getId());
//            assert player != null;
//            player.hit(1);
//        } catch (Exception ignored) {
//
//        }
//    }
}
