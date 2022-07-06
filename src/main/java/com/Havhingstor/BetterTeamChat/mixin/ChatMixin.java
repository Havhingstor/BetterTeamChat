package com.havhingstor.BetterTeamChat.mixin;

import com.havhingstor.BetterTeamChat.chatMsgHandler.ChatMsgHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ChatMixin {

    @Inject(method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At(value = "HEAD"), cancellable = true)
    private void onSendChatMessage(String message, Text preview, CallbackInfo ci) {
        if(!ChatMsgHandler.jumpOver) {
            ChatMsgHandler.getMsg(message);
            ci.cancel();
        }
    }
}
