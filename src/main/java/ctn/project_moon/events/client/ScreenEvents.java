package ctn.project_moon.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ScreenEvents {
    @SubscribeEvent
    public static void renderGuiEventPre(RenderGuiEvent.Pre event){
    }

    @SubscribeEvent
    public static void renderGuiEventPost(RenderGuiEvent.Post event){

    }

    @SubscribeEvent
    public static void renderGuiEventPre(RenderGuiLayerEvent.Pre event){
//        if (event.getName() == PmGuiLayers.PLAYER_SPIRIT) {
//            LayeredDraw.Layer layer = event.getLayer();
//            GuiGraphics guiGraphics = event.getGuiGraphics();
//            DeltaTracker partialTick = event.getPartialTick();
//            layer.render(guiGraphics, partialTick);
//            LayeredDraw.Layer layer1 = new SpiritLayersDraw(guiGraphics, partialTick);
//            layer1.render(guiGraphics, partialTick);
//        }
    }

    @SubscribeEvent
    public static void renderGuiEventPost(RenderGuiLayerEvent.Post event){

    }
}
