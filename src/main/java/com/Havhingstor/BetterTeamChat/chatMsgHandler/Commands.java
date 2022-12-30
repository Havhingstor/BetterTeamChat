package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.ArgumentType.PlayerSuggestions;
import com.havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.havhingstor.BetterTeamChat.BetterTeamChat;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static com.havhingstor.BetterTeamChat.BetterTeamChat.*;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("globalmsg").then(argument("global message", greedyString()).executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            ChatMsgHandler.jumpOver = true;
            String message = getString(context, "global message");
            BetterTeamChat.sendChatMessage(message);
            ChatMsgHandler.jumpOver = false;
            return 1;
        })));

        dispatcher.register(literal("stdChatGlobal").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            ChatMsgHandler.setGlobal();
            sendMessageToClientPlayer("Using global chat");

            return 1;
        }));

        dispatcher.register(literal("stdChatTeam").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            if(ChatMsgHandler.isInTeam()) {
                ChatMsgHandler.setTeam();
                sendMessageToClientPlayer("Using team chat");

                return 1;
            } else {
                sendErrorMessageToClientPlayer(BetterTeamChat.teamFailMessage);

                return -1;
            }
        }));

        dispatcher.register(literal("stdChatPlayer").then(argument("Player", word()).suggests(new PlayerSuggestions()).executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            String player = getString(context, "Player");

            if(Utils.isPlayer(player)) {

                ChatMsgHandler.setPlayer(player);
                sendMessageToClientPlayer("Using playerchat with " + player);

                return 1;
            } else {
                sendErrorMessageToClientPlayer("No player with name \"" + player + "\" found!");

                return -1;
            }
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            sendMessageToClientPlayer("You message " + ChatMsgHandler.getType() + ".");

            return 1;
        }));
    }
}
