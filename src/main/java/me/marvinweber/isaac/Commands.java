package me.marvinweber.isaac;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.marvinweber.isaac.debug.DebugOptions;
import me.marvinweber.isaac.debug.DebugOptionsArgumentType;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.registry.common.ItemRegistry;

import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.event.Level;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Commands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("isaac")
                .then(literal("stop")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.RUNNING) {
                                Isaac.game.stop();
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendError(Text.literal("The Game is not running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("restart")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.RUNNING) {
                                Isaac.game.restart();
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendError(Text.literal("The Game is not running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("start")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.STOPPED) {
                                Isaac.game.start();
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendError(Text.literal("The Game is already running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("debug")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.STOPPED) {
                                ctx.getSource().sendError(Text.literal("The Game is not running!"));
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendFeedback(Text.literal("Active Debug: " + Isaac.game.activeDebugOptions.stream().map(Enum::name).collect(Collectors.joining(", "))), false);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(argument("option", DebugOptionsArgumentType.debugOption())
                                .executes(ctx -> {
                                    if (Isaac.game.gameState == Game.State.STOPPED) {
                                        ctx.getSource().sendError(Text.literal("The Game is not running!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    DebugOptions debugOption = DebugOptionsArgumentType.getDebugOption(ctx, "option");
                                    if(Isaac.game.activeDebugOptions.contains(debugOption)) {
                                        Isaac.game.activeDebugOptions.remove(debugOption);
                                        ctx.getSource().sendFeedback(Text.literal("Deactivated " + debugOption.name()), false);
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    Isaac.game.activeDebugOptions.add(debugOption);
                                    ctx.getSource().sendFeedback(Text.literal("Activated " + debugOption.name()), false);
                                    return Command.SINGLE_SUCCESS;

                                })
                        )
                )
                .then(literal("give")
                        .then(argument("id", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                    if (Isaac.game.gameState != Game.State.RUNNING) {
                                        ctx.getSource().sendError(Text.literal("The Game is not running!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    Player player = Isaac.game.getPlayer(ctx.getSource().getPlayer());
                                    if (player == null) {
                                        ctx.getSource().sendError(Text.literal("You are not participating in the current Game!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    IsaacItem item = ItemRegistry.ITEMS.get(IntegerArgumentType.getInteger(ctx, "id"));
                                    if (item == null) {
                                        ctx.getSource().sendError(Text.literal("Couldn't find a item with this id!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    player.addItem(item);
                                    player.playerEntity.giveItemStack(new ItemStack(item));
                                    return Command.SINGLE_SUCCESS;
                                })
                        )

                )
        );
        // TODO finish this!
        /*dispatcher.register(literal("stats")
            .then(literal("set")
                    .then(argument("name", StringArgumentType.word())
                            .then(argument("value", FloatArgumentType.floatArg())
                                    .executes(ctx -> {
                                        if (!(ctx.getSource().getEntity() instanceof PlayerEntity)) {
                                            ctx.getSource().sendError(Text.literal("Only Players can use this command!"));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        if (IsaacServer.game.gameState != Game.State.RUNNING) {
                                            ctx.getSource().sendError(Text.literal("The Game is not running!"));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        String stat = StringArgumentType.getString(ctx, "name");
                                        float value = FloatArgumentType.getFloat(ctx, "value");

                                        Player player = IsaacServer.game.getPlayer((PlayerEntity) ctx.getSource().getEntity());
                                        try {
                                            Field field = PlayerStats.class.getField(stat);
                                            player.playerStats

                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            ctx.getSource().sendError(Text.literal("This stat does not exist! Available stats: " + Arrays.asList(PlayerStats.class.getFields())));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )

                    )
            )
        );*/

    }
}
