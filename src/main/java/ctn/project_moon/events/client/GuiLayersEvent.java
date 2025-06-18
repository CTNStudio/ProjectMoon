package ctn.project_moon.events.client;

import com.mojang.datafixers.util.Function3;
import ctn.project_moon.client.gui.RationalityLayersDraw;
import ctn.project_moon.client.gui.SkillLayersDrew;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.client.PmGuiLayers.PLAYER_RATIONALITY;
import static ctn.project_moon.client.PmGuiLayers.PLAYER_SKILL;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.SELECTED_ITEM_NAME;

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
		register(event, SELECTED_ITEM_NAME, PLAYER_RATIONALITY, RationalityLayersDraw::new);
		register(event, SELECTED_ITEM_NAME, PLAYER_SKILL, SkillLayersDrew::new);
	}
	
	private static void register(RegisterGuiLayersEvent event, ResourceLocation other, ResourceLocation id, Function3<GuiGraphics, DeltaTracker, Minecraft, LayeredDraw.Layer> layer) {
		event.registerBelow(other, id, (guiGraphics, deltaTracker) -> layer.apply(guiGraphics, deltaTracker, Minecraft.getInstance()));
	}
}
