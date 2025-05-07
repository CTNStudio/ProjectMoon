package ctn.project_moon.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class HudScreenEvents {
    @SubscribeEvent
    public static void renderGuiEventPre(RenderGuiEvent.Pre event) {

    }

    @SubscribeEvent
    public static void renderGuiEventPost(RenderGuiEvent.Post event) {

    }
}
