package ctn.project_moon.events.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * gui渲染图层事件
 */
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GuiLayersEvent {
    /**
     * 添加gui渲染图层及渲染事件
     */
    @SubscribeEvent
    public static void registerGuiLayersEvent(RegisterGuiLayersEvent event) {
//        event.registerBelow(SELECTED_ITEM_NAME, PLAYER_SPIRIT, (guiGraphics, deltaTracker) -> new SpiritLayersDraw(guiGraphics, deltaTracker, Minecraft.getInstance()));
    }
}
