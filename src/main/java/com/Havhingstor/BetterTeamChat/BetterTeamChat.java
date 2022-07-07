package com.havhingstor.BetterTeamChat;

import com.havhingstor.BetterTeamChat.chatMsgHandler.ChatMsgHandler;
import com.havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import com.havhingstor.BetterTeamChat.chatMsgHandler.CustomTeamType;
import com.havhingstor.BetterTeamChat.chatMsgHandler.StandardTeamType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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

	public static MutableText getTextOfString(String message) {
		return Text.literal(message);
	}

	public static void sendCommand(String command) {
		ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;

		if(localPlayer == null) {
			return;
		}

		localPlayer.sendCommand(command);
	}

	@Override
	public void onInitializeClient() {
		registerTeamType(new StandardTeamType());
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			Commands.registerCommands(dispatcher);
		}));
	}
}
