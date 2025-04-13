package ctn.project_moon.init;

import ctn.project_moon.common.payload.SpiritValueDelivery;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handlers.ClientPayloadHandler;
import net.neoforged.neoforge.network.handlers.ServerPayloadHandler;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadInit {
    @SubscribeEvent
    public static void spiritPayload(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SpiritValueDelivery.TYPE,
                SpiritValueDelivery.CODEC,
                (payload, context) ->
                context.enqueueWork(() -> {
                    // TODO 这里开始写存储逻辑。
                }));
    }
}
