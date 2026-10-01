package com.fodk.gemcolony.command;

import com.fodk.gemcolony.data.ModAttachments;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.regex.Pattern;

public class BubbleColorCommand {

    private static final Pattern HEX_PATTERN = Pattern.compile("^#?[0-9a-fA-F]{6}$");

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("bubblecolor")
                        .then(Commands.argument("hex", StringArgumentType.word())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                    String hex = StringArgumentType.getString(context, "hex");

                                    if (!HEX_PATTERN.matcher(hex).matches()) {
                                        context.getSource().sendFailure(Component.literal("Invalid color. Use a hex color like #FF55AA."));
                                        return 0;
                                    }

                                    if (hex.startsWith("#")) {
                                        hex = hex.substring(1);
                                    }

                                    int color = Integer.parseInt(hex, 16);
                                    String formattedHex = "#" + hex.toUpperCase();

                                    player.setData(ModAttachments.BUBBLE_COLOR, color);

                                    context.getSource().sendSuccess(
                                            () -> Component.literal("Bubble color set to " + formattedHex), false);

                                    return 1;
                                })
                        )
        );
    }
}
