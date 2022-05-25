package com.Havhingstor.BetterTeamChat;

import com.Havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterTeamChat implements ModInitializer {
	//public static final Logger LOGGER = LoggerFactory.getLogger("betterteamchat");

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated)-> {
			Commands.registerCommands(dispatcher);
		});
	}
}
