package com.Havhingstor.BetterTeamChat.mixin;

import com.Havhingstor.BetterTeamChat.chatMsgHandler.ChatMsgHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ChatMixin {

    @Inject(method = "sendChatMessage", at = @At(value = "HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if(! (message.startsWith("/") || ChatMsgHandler.jumpOver)) {
            ChatMsgHandler.getMsg(message);
            info.cancel();
        }
    }
}
