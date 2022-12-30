package com.havhingstor.BetterTeamChat.chatMsgHandler;

import com.havhingstor.BetterTeamChat.BetterTeamChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import static com.havhingstor.BetterTeamChat.BetterTeamChat.getLocalPlayer;

public class StandardTeamType implements CustomTeamType{
    @Override
    public boolean isInTeam() {
        ClientPlayerEntity localPlayer = getLocalPlayer();
        return MinecraftClient.getInstance().world.getScoreboard().getPlayerTeam(localPlayer.getEntityName()) != null;
    }

    @Override
    public void sendMessage(String message) {
        BetterTeamChat.sendCommand("teammsg " + message);
    }
}
