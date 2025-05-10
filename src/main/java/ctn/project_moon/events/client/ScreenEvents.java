package ctn.project_moon.events.client;

import ctn.project_moon.client.gui.widget.PlayerAttributeButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 屏幕相关事件
 */
@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ScreenEvents {
	@SubscribeEvent
	public static void renderGuiEventPre(RenderGuiLayerEvent.Pre event) {

	}

	@SubscribeEvent
	public static void renderGuiEventPost(RenderGuiLayerEvent.Post event) {

	}

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		Screen screen = evt.getScreen();

		if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
			AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
			evt.addListener(new PlayerAttributeButton(gui));
		}
	}
}
