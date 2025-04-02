package ctn.project_moon.client.events;

import ctn.project_moon.client.gui_layered.SpiritLayersDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.client.PmGuiLayers.PLAYER_SPIRIT;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.SELECTED_ITEM_NAME;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GuiLayersEvent {
    @SubscribeEvent
    public static void registerGuiLayersEvent(RegisterGuiLayersEvent event){
        event.registerBelow(SELECTED_ITEM_NAME, PLAYER_SPIRIT, SpiritLayersDraw::new);
    }
}
