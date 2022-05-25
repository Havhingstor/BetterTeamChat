package com.Havhingstor.BetterTeamChat.chatMsgHandler;

import com.Havhingstor.BetterTeamChat.ArgumentType.PlayerSuggestions;
import com.Havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

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
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Using global chat"), MinecraftClient.getInstance().player.getUuid());

            return 1;
        }));

        dispatcher.register(literal("stdChatTeam").executes(context -> {
            ChatMsgHandler.setTeam();
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Using team chat"), MinecraftClient.getInstance().player.getUuid());

            return 1;
        }));

        dispatcher.register(literal("stdChatPlayer").then(argument("Player", word()).suggests(new PlayerSuggestions()).executes(context -> {

            String player = getString(context, "Player");

            if(Utils.isPlayer(player)) {

                ChatMsgHandler.setPlayer(player);
                MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Using playerchat with "
                        + player), MinecraftClient.getInstance().player.getUuid());

                return 1;
            } else {
                MinecraftClient.getInstance().player.sendMessage(new LiteralText("No player with name \"" + player + "\" found!").setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), false);

                return -1;
            }
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("You message " + ChatMsgHandler.getType() + "."), MinecraftClient.getInstance().player.getUuid());

            return 1;
        }));
    }
}
