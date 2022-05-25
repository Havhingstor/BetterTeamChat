package com.Havhingstor.BetterTeamChat.chatMsgHandler;

// getString(ctx, "string")

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Collection;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Commands {
    public static void registerCommands(CommandDispatcher dispatcher) {

        dispatcher.register(literal("globalmsg").then(argument("global message", greedyString()).executes(context -> {
            ChatMsgHandler.jumpOver = true;
            MinecraftClient.getInstance().player.sendChatMessage(StringArgumentType.getString(context, "global message"));
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

        dispatcher.register(literal("stdChatPlayer").then(argument("Player", EntityArgumentType.players()).executes(context -> {
            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "Player");
            ChatMsgHandler.setPlayers(players);
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Using playerchat with " + ChatMsgHandler.getPlayerStrings()), MinecraftClient.getInstance().player.getUuid());

            return 1;
        })));

        dispatcher.register(literal("stdChat").executes(context -> {
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("You message " + ChatMsgHandler.getType() + "."), MinecraftClient.getInstance().player.getUuid());

            return 1;
        }));
    }
}
