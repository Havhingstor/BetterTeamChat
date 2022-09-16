package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.havhingstor.BetterTeamChat.BetterTeamChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;

import java.util.List;

public class ChatMsgHandler {
    private static ChatMsgType type = ChatMsgType.GLOBAL;
    private static String player = null;
    protected static Style errorStyle = Style.EMPTY.withColor(TextColor.parse("red"));

    public static boolean jumpOver = false;

    public static boolean isInTeam() {
        for(CustomTeamType type: BetterTeamChat.getCustomTeamTypes()) {
            if(type.isInTeam()) {
                return true;
            }
        }
        return false;
    }

    private static void sendFailMessage(String failMessage, String originalMessage) {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;

        localPlayer.sendMessage(BetterTeamChat.getTextOfString(failMessage)
                .setStyle(errorStyle), false);
        localPlayer.sendMessage(BetterTeamChat.getTextOfString("You now message globally."), false);
        localPlayer.sendMessage(BetterTeamChat.getTextOfString("The original message is copied to the clipboard."), false);
        type = ChatMsgType.GLOBAL;
        MinecraftClient.getInstance().keyboard.setClipboard(originalMessage);
    }

    public static void getMsg(String message) {
       ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;

       if(localPlayer == null) {
           return;
       }

        jumpOver = true;
        if(type == ChatMsgType.GLOBAL) {
            localPlayer.sendChatMessage(message, null);
        } else if(type == ChatMsgType.TEAM) {
            boolean wasInTeam = false;

            for(CustomTeamType type: BetterTeamChat.getCustomTeamTypes()) {
                if(type.isInTeam()) {
                    wasInTeam = true;
                    type.sendMessage(message);
                }
            }

            if(!wasInTeam) {
                sendFailMessage(BetterTeamChat.teamFailMessage, message);
            }

        } else {
            if(Utils.isPlayer(player)) {
                BetterTeamChat.sendCommand("msg " + player + " " + message);
            } else {
                sendFailMessage("No player with name \"" + player + "\" found!", message);
            }
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

