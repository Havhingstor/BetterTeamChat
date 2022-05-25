package com.Havhingstor.BetterTeamChat.chatMsgHandler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.Iterator;

public class ChatMsgHandler {
    private static ChatMsgType type = ChatMsgType.GLOBAL;
    private static Collection<ServerPlayerEntity> players = null;

    public static boolean jumpOver = false;

    public static void getMsg(String message) {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;
        jumpOver = true;
        if(type == ChatMsgType.GLOBAL) {
            localPlayer.sendChatMessage(message);
        } else if(type == ChatMsgType.TEAM) {
            localPlayer.sendChatMessage("/teammsg " + message);
        } else {
            for(ServerPlayerEntity player: players) {
                localPlayer.sendChatMessage("/msg " + player.getEntityName() + " " + message);
            }
        }
        jumpOver = false;
    }

    public static void setGlobal() {
        type = ChatMsgType.GLOBAL;
        players = null;
    }

    public static void setTeam() {
        type = ChatMsgType.TEAM;
        players = null;
    }

    public static void setPlayers(Collection<ServerPlayerEntity> players) {
        if(players.size() > 0) {
            type = ChatMsgType.PLAYER;
            ChatMsgHandler.players = players;
        }
    }

    public static String getType() {
        switch (type) {
            case TEAM:
                return "your team";
            case GLOBAL:
                return "globally";
            default:
                return "the player" + getExtendedPlayerString();
        }
    }

    private static String getExtendedPlayerString() {
        String result = " ";

        if (players.size() > 1) {
            result = "s" + result;
        }

        result += getPlayerStrings();

        return result;
    }

    public static String getPlayerStrings() {
        Iterator<ServerPlayerEntity> iterator = players.iterator();
        String result = iterator.next().getEntityName();

        while (iterator.hasNext()) {
            result += ", " + iterator.next().getEntityName();
        }

        return result;
    }
}

