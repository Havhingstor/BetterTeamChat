package com.havhingstor.BetterTeamChat.ArgumentType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

    public static boolean isPlayer(String name) {
        for(PlayerListEntry player: getPlayerList()) {
            if(player.getProfile().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    static List<String> getSuggestions(String name) {
        List<String> result = new ArrayList<>();

        for(PlayerListEntry player: getPlayerList()) {
            if(player.getProfile().getName().startsWith(name)) {
                result.add(player.getProfile().getName());
            }
        }
        return result;
    }

    private static Collection<PlayerListEntry> getPlayerList() {
        return MinecraftClient.getInstance().player.networkHandler.getPlayerList();
    }

}
