package ctn.project_moon;

import com.mojang.logging.LogUtils;
import ctn.project_moon.init.PmBlocks;
import ctn.project_moon.init.PmItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static ctn.project_moon.datagen.CuriosTest.*;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static ctn.project_moon.init.PmAttributes.PM_ATTRIBUTE;
import static ctn.project_moon.init.PmParticleTypes.PARTICLE_TYPES;
import static ctn.project_moon.init.PmTab.PROJECT_MOON_TAB;
import static top.theillusivec4.curios.api.CuriosApi.registerCurioPredicate;

@Mod(PmMain.MOD_ID)
public class PmMain {
    public static final String MOD_ID = "project_moon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PmMain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        PmBlocks.BLOCKS.register(modEventBus);
        PmItems.ITEMS.register(modEventBus);
        PROJECT_MOON_TAB.register(modEventBus);
        PM_ATTRIBUTE.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);

        createValidators();
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    public void createValidators() {
        createValidators(EGO_CURIOS_TAG, EGO_CURIOS);
        createValidators(HEADWEAR_TAG, EGO_CURIOS_HEADWEAR);
        createValidators(HEAD_TAG, EGO_CURIOS_HEAD);
        createValidators(HINDBRAIN_TAG, EGO_CURIOS_HINDBRAIN);
        createValidators(EYE_AREA_TAG, EGO_CURIOS_EYE_AREA);
        createValidators(FACE_TAG, EGO_CURIOS_FACE);
        createValidators(CHEEK_TAG, EGO_CURIOS_CHEEK);
        createValidators(MASK_TAG, EGO_CURIOS_MASK);
        createValidators(MOUTH_TAG, EGO_CURIOS_MOUTH);
        createValidators(NECK_TAG, EGO_CURIOS_NECK);
        createValidators(CHEST_TAG, EGO_CURIOS_CHEST);
        createValidators(HAND_TAG, EGO_CURIOS_HAND);
        createValidators(GLOVE_TAG, EGO_CURIOS_GLOVE);
        createValidators(RIGHT_BACK_TAG, EGO_CURIOS_RIGHT_BACK);
        createValidators(LEFT_BACK_TAG, EGO_CURIOS_LEFT_BACK);
    }

    public static void createValidators(ResourceLocation name,TagKey<Item> tagKey){
        registerCurioPredicate(name, (slotResult) -> slotResult.stack().is(tagKey));
    }
}
