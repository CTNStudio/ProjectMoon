package ctn.project_moon.registrar;

import ctn.project_moon.common.payload.data.SpiritValueData;
import ctn.project_moon.common.payload.data.TempNbtAttrData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class RegistrarPayloadInit {
    /** 注册有效载荷数据包 */
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                SpiritValueData.TYPE, SpiritValueData.CODEC,
                new DirectionalPayloadHandler<>(SpiritValueData::client, SpiritValueData::server));
        registrar.playBidirectional(
                TempNbtAttrData.TYPE, TempNbtAttrData.CODEC,
                new DirectionalPayloadHandler<>(TempNbtAttrData::client, TempNbtAttrData::server));
    }
}
