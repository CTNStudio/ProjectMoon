package ctn.project_moon;

import com.mojang.logging.LogUtils;
import ctn.project_moon.common.az_renderer.item.mace.DetonatingBatonItemRenderer;
import ctn.project_moon.create.PmBlocks;
import ctn.project_moon.create.PmItems;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static ctn.project_moon.create.PmItems.DETONATING_BATON;
import static ctn.project_moon.create.PmTab.PROJECT_MOON_TAB;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PmMain.MOD_ID)
public class PmMain {
    public static final String MOD_ID = "project_moon";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PmMain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        PmBlocks.BLOCKS.register(modEventBus);
        PmItems.ITEMS.register(modEventBus);
        PROJECT_MOON_TAB.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, PmConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (PmConfig.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(PmConfig.magicNumberIntroduction + PmConfig.magicNumber);

        PmConfig.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            AzIdentityRegistry.register(DETONATING_BATON.asItem());
            AzItemRendererRegistry.register(DetonatingBatonItemRenderer::new, DETONATING_BATON.asItem());
        }
    }
}
