package ctn.project_moon.client.events;

import ctn.project_moon.client.az_renderers.item.DetonatingBatonItemRenderer;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static ctn.project_moon.PmMain.LOGGER;
import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmItems.DETONATING_BATON;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        AzIdentityRegistry.register(DETONATING_BATON.asItem());
        AzItemRendererRegistry.register(DetonatingBatonItemRenderer::new, DETONATING_BATON.asItem());
    }
}
