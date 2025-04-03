package ctn.project_moon.events.client;

import ctn.project_moon.client.PmGuiLayers;
import ctn.project_moon.common.client.gui_layered.SpiritLayersDraw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/** 屏幕相关事件 */
public class ScreenEvents {

    /** 渲染到hud */
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class RenderHud{
        @SubscribeEvent
        public static void renderGuiEventPre(RenderGuiEvent.Pre event){

        }

        @SubscribeEvent
        public static void renderGuiEventPost(RenderGuiEvent.Post event){

        }
    }

    /** 拦截gui图层渲染 */
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class interceptGuiLayer {
        @SubscribeEvent
        public static void renderGuiEventPre(RenderGuiLayerEvent.Pre event){

        }

        @SubscribeEvent
        public static void renderGuiEventPost(RenderGuiLayerEvent.Post event){

        }
    }
}
