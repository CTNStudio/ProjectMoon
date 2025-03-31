package ctn.project_moon;

import com.mojang.logging.LogUtils;
import ctn.project_moon.create.PmBlocks;
import ctn.project_moon.create.PmItems;
import ctn.project_moon.events.ItemEvents;
import ctn.project_moon.events.LivingEvent;
import ctn.project_moon.events.PlayerEvents;
import ctn.project_moon.events.ScreenEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static ctn.project_moon.create.PmTab.PROJECT_MOON_TAB;

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

    @SubscribeEvent
    public void itemTooltip(final ItemTooltipEvent event){
        ItemEvents.itemTooltip(event);
    }

    @SubscribeEvent
    public void saveToSpiritValue(PlayerEvent.SaveToFile event){
        PlayerEvents.saveToSpiritValue(event);
    }

    @SubscribeEvent
    public void loadFromSpiritValue(PlayerEvent.LoadFromFile event){
        PlayerEvents.loadFromSpiritValue(event);
    }

    @SubscribeEvent
    public void resetSpiritValue(PlayerEvent.PlayerRespawnEvent event){
        PlayerEvents.resetSpiritValue(event);
    }

    @SubscribeEvent
    public void damage(LivingIncomingDamageEvent event){
        LivingEvent.damage(event);
    }

    @SubscribeEvent
    public void hudExtensions(RenderGuiEvent.Pre event){
        ScreenEvents.preScreenEvent(event);
    }

    @SubscribeEvent
    public void hudExtensions(RenderGuiEvent.Post event){
        ScreenEvents.postScreenEvent(event);
    }
}
