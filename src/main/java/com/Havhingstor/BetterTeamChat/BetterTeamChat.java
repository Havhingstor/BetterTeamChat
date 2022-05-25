package com.Havhingstor.BetterTeamChat;

import com.Havhingstor.BetterTeamChat.chatMsgHandler.Commands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class BetterTeamChat implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("betterteamchat");

	@Override
	public void onInitializeClient() {
		Commands.registerCommands(ClientCommandManager.DISPATCHER);
	}
}
