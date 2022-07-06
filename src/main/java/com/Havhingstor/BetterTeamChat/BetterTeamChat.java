package com.havhingstor.BetterTeamChat;

import com.havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterTeamChat implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("betterteamchat");

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			Commands.registerCommands(dispatcher);
		}));
	}
}
