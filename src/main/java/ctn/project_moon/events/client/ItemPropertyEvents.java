package ctn.project_moon.events.client;

import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.common.item.PmDataComponents;
import ctn.project_moon.init.PmItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 物品渲染附加
 */
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemPropertyEvents {
    public static final ResourceLocation MODE_BOOLEAN = createProperties("mode_boolean");
    public static final ResourceLocation CURRENT_DAMAGE_TYPE = createProperties("current_damage_type");

    public static final ClampedItemPropertyFunction PROPERTY_MODE_BOOLEAN = (itemStack, clientLevel, livingEntity, i) ->
            Boolean.TRUE.equals(itemStack.get(PmDataComponents.MODE_BOOLEAN)) ? 1 : 0;
    public static final ClampedItemPropertyFunction PROPERTY_CURRENT_DAMAGE_TYPE = (itemStack, clientLevel, livingEntity, i) ->
            switch (PmDamageTypes.Types.getType(itemStack.get(PmDataComponents.CURRENT_DAMAGE_TYPE))) {
                    case PHYSICS -> 0;
                    case SPIRIT -> 0.1F;
                    case EROSION-> 0.2F;
                    case THE_SOUL-> 0.3F;
                    case null -> 0;
            };

    /**
     * 注册物品渲染附加
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        createProperties(event, PmItems.CREATIVE_SPIRIT_TOOL.asItem(), MODE_BOOLEAN, PROPERTY_MODE_BOOLEAN);
        createProperties(event, PmItems.CHAOS_SWORD.asItem(), CURRENT_DAMAGE_TYPE, PROPERTY_CURRENT_DAMAGE_TYPE);
    }

    private static void createProperties(FMLClientSetupEvent event, Item item, ResourceLocation propertiesName, ClampedItemPropertyFunction propertyFunction) {
        event.enqueueWork(() -> ItemProperties.register(item, propertiesName, propertyFunction));
    }

    private static ResourceLocation createProperties(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
