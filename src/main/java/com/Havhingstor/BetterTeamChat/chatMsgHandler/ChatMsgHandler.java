package com.havhingstor.BetterTeamChat.chatMsgHandler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ChatMsgHandler {
    private static ChatMsgType type = ChatMsgType.GLOBAL;
    private static String player = null;

    public static boolean jumpOver = false;

    public static void getMsg(String message) {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;
        jumpOver = true;
        if(type == ChatMsgType.GLOBAL) {
            localPlayer.sendChatMessage(message);
        } else if(type == ChatMsgType.TEAM) {
            localPlayer.sendCommand("teammsg " + message);
        } else {
            localPlayer.sendCommand("msg " + player + " " + message);
        }
        jumpOver = false;
    }

    public static void setGlobal() {
        type = ChatMsgType.GLOBAL;
        player = null;
    }

    public static void setTeam() {
        type = ChatMsgType.TEAM;
        player = null;
    }

    public static void setPlayer(String player) {
        if(player != null) {
            type = ChatMsgType.PLAYER;
            ChatMsgHandler.player = player;
        }
    }

    public static String getType() {
        switch (type) {
            case TEAM:
                return "your team";
            case GLOBAL:
                return "globally";
            default:
                return "the player " + player;
        }
    }
}

