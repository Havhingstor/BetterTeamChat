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

    private static ClientPlayerEntity getLocalPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("globalmsg").then(argument("global message", greedyString()).executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            ChatMsgHandler.jumpOver = true;
            getLocalPlayer().sendChatMessage(getString(context, "global message"));
            ChatMsgHandler.jumpOver = false;
            return 1;
        })));

        dispatcher.register(literal("stdChatGlobal").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            ChatMsgHandler.setGlobal();
            getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString("Using global chat"), false);

            return 1;
        }));

        dispatcher.register(literal("stdChatTeam").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            if(ChatMsgHandler.isInTeam()) {
                ChatMsgHandler.setTeam();
                getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString("Using team chat"), false);

                return 1;
            } else {
                getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString(BetterTeamChat.teamFailMessage).setStyle(ChatMsgHandler.errorStyle), false);

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
                getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString("Using playerchat with " + player), false);

                return 1;
            } else {
                getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString("No player with name \"" + player + "\" found!").setStyle(ChatMsgHandler.errorStyle), false);

                return -1;
            }
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            if(getLocalPlayer() == null) {
                return -1;
            }

            getLocalPlayer().sendMessage(BetterTeamChat.getTextOfString("You message " + ChatMsgHandler.getType() + "."), false);

            return 1;
        }));
    }
}
