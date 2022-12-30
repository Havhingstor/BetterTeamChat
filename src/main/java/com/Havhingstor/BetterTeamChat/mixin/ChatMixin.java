package com.havhingstor.BetterTeamChat.mixin;

import com.havhingstor.BetterTeamChat.chatMsgHandler.ChatMsgHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.havhingstor.BetterTeamChat.BetterTeamChat.getLocalPlayer;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {

    @Inject(method = "sendChatMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if(!ChatMsgHandler.jumpOver && getLocalPlayer() != null) {
            ChatMsgHandler.sendMsg(message);
            ci.cancel();
        }
    }
}
