package com.havhingstor.BetterTeamChat;

import com.havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import com.havhingstor.BetterTeamChat.chatMsgHandler.CustomTeamType;
import com.havhingstor.BetterTeamChat.chatMsgHandler.StandardTeamType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.text.Text.literal;

public class BetterTeamChat implements ClientModInitializer {
	private static Style errorStyle = Style.EMPTY.withColor(TextColor.parse("red"));

	public static final Logger LOGGER = LoggerFactory.getLogger("betterteamchat");
	private static final List<CustomTeamType> customTeamTypes = new ArrayList<>();
	public static String teamFailMessage = "You are not in a team";

	public static List<CustomTeamType> getCustomTeamTypes() {
		return customTeamTypes;
	}

	public static void registerTeamType(CustomTeamType type) {
		customTeamTypes.add(type);
	}

	public static MutableText getTextOfString(String message) {
		return literal(message);
	}

	public static ClientPlayerEntity getLocalPlayer() {
		return MinecraftClient.getInstance().player;
	}

	public static void sendCommand(String command) {
		getLocalPlayer().networkHandler.sendChatCommand(command);
	}

	public static void sendChatMessage(String chatMessage) {
		getLocalPlayer().networkHandler.sendChatMessage(chatMessage);
	}

	public static void sendMessageToClientPlayer(String message) {
		ClientPlayerEntity player = getLocalPlayer();
		player.sendMessage(getTextOfString(message), false);
	}

	public static void sendErrorMessageToClientPlayer(String message) {
		ClientPlayerEntity player = getLocalPlayer();
		player.sendMessage(getTextOfString(message).setStyle(errorStyle), false);
	}

	@Override
	public void onInitializeClient() {
		registerTeamType(new StandardTeamType());
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			Commands.registerCommands(dispatcher);
		}));
	}
}
