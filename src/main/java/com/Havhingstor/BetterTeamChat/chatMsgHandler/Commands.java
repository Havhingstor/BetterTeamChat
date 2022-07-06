package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.ArgumentType.PlayerSuggestions;
import com.havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(literal("globalmsg").then(argument("global message", greedyString()).executes(context -> {
            ChatMsgHandler.jumpOver = true;
            MinecraftClient.getInstance().player.sendChatMessage(getString(context, "global message"));
            ChatMsgHandler.jumpOver = false;
            return 1;
        })));

        dispatcher.register(literal("stdChatGlobal").executes(context -> {
            ChatMsgHandler.setGlobal();
            MinecraftClient.getInstance().player.sendMessage(MutableText.of(new LiteralTextContent("Using global chat")));

            return 1;
        }));

        dispatcher.register(literal("stdChatTeam").executes(context -> {
            ChatMsgHandler.setTeam();
            MinecraftClient.getInstance().player.sendMessage(MutableText.of(new LiteralTextContent("Using team chat")));

            return 1;
        }));

        dispatcher.register(literal("stdChatPlayer").then(argument("Player", word()).suggests(new PlayerSuggestions()).executes(context -> {

            String player = getString(context, "Player");

            if(Utils.isPlayer(player)) {

                ChatMsgHandler.setPlayer(player);
                MinecraftClient.getInstance().player.sendMessage(MutableText.of(new LiteralTextContent("Using playerchat with " + player)));

                return 1;
            } else {
                MinecraftClient.getInstance().player.sendMessage( MutableText.of(new LiteralTextContent("No player with name \"" + player + "\" found!")).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), false);

                return -1;
            }
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            MinecraftClient.getInstance().player.sendMessage(MutableText.of(new LiteralTextContent("You message " + ChatMsgHandler.getType() + ".")));

            return 1;
        }));
    }
}
