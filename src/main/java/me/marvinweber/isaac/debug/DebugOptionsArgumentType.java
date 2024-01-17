package me.marvinweber.isaac.debug;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DebugOptionsArgumentType extends EnumArgumentType<DebugOptions> {


    private DebugOptionsArgumentType() {
        super(DebugOptions.CODEC, DebugOptions::values);
    }

    public static EnumArgumentType<DebugOptions> debugOption() {
        return new DebugOptionsArgumentType();
    }

    public static DebugOptions getDebugOption(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, DebugOptions.class);
    }
}
