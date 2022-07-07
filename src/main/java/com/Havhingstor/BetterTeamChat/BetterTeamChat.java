package com.havhingstor.BetterTeamChat;

import com.havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import com.havhingstor.BetterTeamChat.chatMsgHandler.CustomTeamType;
import com.havhingstor.BetterTeamChat.chatMsgHandler.StandardTeamType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BetterTeamChat implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("betterteamchat");
	private static List<CustomTeamType> customTeamTypes = new ArrayList<>();
	public static String teamFailMessage = "You are not in a team";

	public static List<CustomTeamType> getCustomTeamTypes() {
		return customTeamTypes;
	}

	public static void registerTeamType(CustomTeamType type) {
		customTeamTypes.add(type);
	}

	@Override
	public void onInitializeClient() {
		registerTeamType(new StandardTeamType());
		Commands.registerCommands(ClientCommandManager.DISPATCHER);
	}
}
