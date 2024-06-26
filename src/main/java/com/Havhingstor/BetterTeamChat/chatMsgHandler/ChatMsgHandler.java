package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.ArgumentType.Utils;
import com.havhingstor.BetterTeamChat.BetterTeamChat;
import net.minecraft.client.MinecraftClient;

import static com.havhingstor.BetterTeamChat.BetterTeamChat.*;

public class ChatMsgHandler {
    private static ChatMsgType type = ChatMsgType.GLOBAL;
    private static String player = null;

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
        sendErrorMessageToClientPlayer(failMessage);
        sendMessageToClientPlayer("You now message globally");
        MinecraftClient.getInstance().keyboard.setClipboard(originalMessage);
        sendMessageToClientPlayer("The original message is copied to the clipboard.");
        type = ChatMsgType.GLOBAL;
    }

    public static void sendMsg(String message) {
        jumpOver = true;
        if(type == ChatMsgType.GLOBAL) {
            sendChatMessage(message);
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
        return switch (type) {
            case TEAM -> "your team";
            case GLOBAL -> "globally";
            default -> "the player " + player;
        };
    }
}

