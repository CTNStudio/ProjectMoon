package ctn.project_moon.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ItemRenderEvents {
    @SubscribeEvent
    public static void itemRender(RegisterClientExtensionsEvent event) {
//        IClientItemExtensions paradiseLost = IClientItemExtensions.of(PmItems.PARADISE_LOST.asItem());
////        paradiseLost.
//        event.registerItem(paradiseLost, PmItems.PARADISE_LOST);
    }
}
