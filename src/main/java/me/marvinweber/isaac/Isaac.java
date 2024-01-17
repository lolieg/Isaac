package me.marvinweber.isaac;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.network.OwoNetChannel;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import me.marvinweber.isaac.debug.DebugOptionsArgumentType;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.registry.common.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class Isaac implements ModInitializer {
    public static final String MOD_ID = "isaac";
    public static final OwoItemGroup ISAAC_ITEMS = OwoItemGroup.builder(
            new Identifier(MOD_ID, "isaac"),
            () -> Icon.of(ItemRegistry.TEAR_ITEM)
    ).build();

    public static MinecraftServer minecraftServer;
    public static Game game;

    public static final OwoNetChannel GAME_CHANNEL = OwoNetChannel.create(new Identifier(Isaac.MOD_ID, "game"));
    public static final OwoNetChannel PLAYER_CHANNEL = OwoNetChannel.create(new Identifier(Isaac.MOD_ID, "players"));
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            Isaac.minecraftServer = server;
            Isaac.game = new Game(server);
        }));
        ServerTickEvents.END_SERVER_TICK.register(server -> Isaac.game.update());

        PacketRegistry.initialize();
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> Commands.register(dispatcher)));
        ArgumentTypeRegistry.registerArgumentType(new Identifier(MOD_ID, "debug_options"), DebugOptionsArgumentType.class, ConstantArgumentSerializer.of(DebugOptionsArgumentType::debugOption));

        EntityRegistry.initialize();
        // Auto registration owo
        FieldRegistrationHandler.register(ItemRegistry.class, MOD_ID, false);
        FieldRegistrationHandler.register(ParticleRegistry.class, MOD_ID, false);
        FieldRegistrationHandler.register(BlockRegistry.class, MOD_ID, false);
        FieldRegistrationHandler.register(SoundRegistry.class, MOD_ID, false);

        ISAAC_ITEMS.initialize();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof BaseEntity) return ActionResult.FAIL;
            return ActionResult.PASS;
        });
    }
}
