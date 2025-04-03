package ctn.project_moon.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadEvents {
    @SubscribeEvent
    public static void spiritPayload(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
    }
}
