package com.havhingstor.BetterTeamChat.chatMsgHandler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class StandardTeamType implements CustomTeamType{
    @Override
    public boolean isInTeam() {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;
        return MinecraftClient.getInstance().world.getScoreboard().getPlayerTeam(localPlayer.getEntityName()) != null;
    }

    @Override
    public void sendMessage(String message) {
        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;
        localPlayer.sendCommand("teammsg " + message);
    }
}
