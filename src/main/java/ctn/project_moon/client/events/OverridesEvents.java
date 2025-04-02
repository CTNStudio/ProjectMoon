package ctn.project_moon.client.events;

import com.google.common.collect.Maps;
import ctn.project_moon.common.item.components.PmDataComponents;
import ctn.project_moon.init.PmItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverridesEvents {
    public static final ResourceLocation MODE_BOOLEAN = createProperties("mode_boolean");
    public static final ClampedItemPropertyFunction PROPERTY_MODE_BOOLEAN =
            (itemStack, clientLevel, livingEntity, i) ->
                    itemStack.get(PmDataComponents.MODE_BOOLEAN) ? 1 : 0;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        createProperties(event, PmItems.CREATIVE_SPIRIT_TOOL.asItem(), MODE_BOOLEAN, PROPERTY_MODE_BOOLEAN);
    }

    public static void createProperties(FMLClientSetupEvent event, Item item, ResourceLocation propertiesName, ClampedItemPropertyFunction propertyFunction) {
        event.enqueueWork(() -> ItemProperties.register(item, propertiesName, propertyFunction));
    }

    public static ResourceLocation createProperties(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
