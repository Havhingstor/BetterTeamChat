package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.ArgumentType.PlayerSuggestions;
import com.havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.havhingstor.BetterTeamChat.BetterTeamChat;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;

        if(localPlayer == null) {
            return;
        }

        dispatcher.register(literal("globalmsg").then(argument("global message", greedyString()).executes(context -> {
            ChatMsgHandler.jumpOver = true;
            localPlayer.sendChatMessage(getString(context, "global message"));
            ChatMsgHandler.jumpOver = false;
            return 1;
        })));

        dispatcher.register(literal("stdChatGlobal").executes(context -> {
            ChatMsgHandler.setGlobal();
            localPlayer.sendMessage(Text.literal("Using global chat"));

            return 1;
        }));

        dispatcher.register(literal("stdChatTeam").executes(context -> {

            if(ChatMsgHandler.isInTeam()) {
                ChatMsgHandler.setTeam();
                localPlayer.sendMessage(Text.literal("Using team chat"));

                return 1;
            } else {
                localPlayer.sendMessage( Text.literal(BetterTeamChat.teamFailMessage).setStyle(ChatMsgHandler.errorStyle), false);

                return -1;
            }
        }));

        dispatcher.register(literal("stdChatPlayer").then(argument("Player", word()).suggests(new PlayerSuggestions()).executes(context -> {

            String player = getString(context, "Player");

            if(Utils.isPlayer(player)) {

                ChatMsgHandler.setPlayer(player);
                localPlayer.sendMessage(Text.literal("Using playerchat with " + player));

                return 1;
            } else {
                localPlayer.sendMessage( Text.literal("No player with name \"" + player + "\" found!").setStyle(ChatMsgHandler.errorStyle), false);

                return -1;
            }
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            MinecraftClient.getInstance().player.sendMessage(MutableText.of(new LiteralTextContent("You message " + ChatMsgHandler.getType() + ".")));

            return 1;
        }));
    }
}
