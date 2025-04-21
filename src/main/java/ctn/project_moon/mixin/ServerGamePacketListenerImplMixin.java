package ctn.project_moon.mixin;

import ctn.project_moon.init.PmCommonHooks;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    @Inject(method = "handleSetCarriedItem", at = @At("TAIL"))
    protected void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet, CallbackInfo ci) {
        if (packet.getSlot() >= 0 && packet.getSlot() < Inventory.getSelectionSize()) {
            PmCommonHooks.stopUsingItem(player);
        }
    }
}
