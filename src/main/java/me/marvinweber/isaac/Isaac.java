package me.marvinweber.isaac;

import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.registry.common.BlockRegistry;
import me.marvinweber.isaac.registry.common.EntityRegistry;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import me.marvinweber.isaac.registry.common.ParticleRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class Isaac implements ModInitializer {
    public static final String MOD_ID = "isaac";
    public static final ItemGroup ISAAC_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "isaac"),
            () -> new ItemStack(ItemRegistry.TEAR_ITEM)
    );

    public static MinecraftServer minecraftServer;
    public static Game game;


    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            Isaac.minecraftServer = server;
            Isaac.game = new Game(server);
        }));
        ServerTickEvents.END_SERVER_TICK.register(server -> Isaac.game.update());

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> Commands.register(dispatcher)));
        EntityRegistry.initialize();
        ItemRegistry.initialize();
        ParticleRegistry.initialize();
        BlockRegistry.initialize();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof BaseEntity) return ActionResult.FAIL;
            return ActionResult.PASS;
        });
    }
}
