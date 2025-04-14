package ctn.project_moon.common.payload;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadInit {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                SpiritValueDelivery.TYPE, SpiritValueDelivery.CODEC,
                new DirectionalPayloadHandler<>(
                        PmClientPayloadHandler::spiritValue,
                        PmServerPayloadHandler::spiritValue
                )
        );
//        registrar.playToClient(SpiritValueDelivery.TYPE, SpiritValueDelivery.CODEC,
//                (payload, context) ->
//                context.enqueueWork(() -> {
//                    PlayerEntityClient.setSpiritValue(payload.spiritValue());
//                    PlayerEntityClient.setMaxSpiritBalue(payload.maxSpiritValue());
//                }));
    }

    @SubscribeEvent
    public static void register(final RegisterConfigurationTasksEvent event) {
        event.register(new PmConfigurationTask(event.getListener()));
    }
}
