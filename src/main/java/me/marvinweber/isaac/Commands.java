package me.marvinweber.isaac;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

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
                            ctx.getSource().sendError(new LiteralText("The Game is not running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("restart")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.RUNNING) {
                                Isaac.game.restart();
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendError(new LiteralText("The Game is not running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("start")
                        .executes(ctx -> {
                            if (Isaac.game.gameState == Game.State.STOPPED) {
                                Isaac.game.start();
                                return Command.SINGLE_SUCCESS;
                            }
                            ctx.getSource().sendError(new LiteralText("The Game is already running!"));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("give")
                        .then(argument("id", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                    if (Isaac.game.gameState != Game.State.RUNNING) {
                                        ctx.getSource().sendError(new LiteralText("The Game is not running!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    Player player = Isaac.game.getPlayer(ctx.getSource().getPlayer());
                                    if (player == null) {
                                        ctx.getSource().sendError(new LiteralText("You are not participating in the current Game!"));
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    IsaacItem item = ItemRegistry.ITEMS.get(IntegerArgumentType.getInteger(ctx, "id"));
                                    if (item == null) {
                                        ctx.getSource().sendError(new LiteralText("Couldn't find a item with this id!"));
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
                                            ctx.getSource().sendError(new LiteralText("Only Players can use this command!"));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        if (IsaacServer.game.gameState != Game.State.RUNNING) {
                                            ctx.getSource().sendError(new LiteralText("The Game is not running!"));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        String stat = StringArgumentType.getString(ctx, "name");
                                        float value = FloatArgumentType.getFloat(ctx, "value");

                                        Player player = IsaacServer.game.getPlayer((PlayerEntity) ctx.getSource().getEntity());
                                        try {
                                            Field field = PlayerStats.class.getField(stat);
                                            player.playerStats

                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            ctx.getSource().sendError(new LiteralText("This stat does not exist! Available stats: " + Arrays.asList(PlayerStats.class.getFields())));
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
