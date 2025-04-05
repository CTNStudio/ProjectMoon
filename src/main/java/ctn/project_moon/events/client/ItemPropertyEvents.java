package ctn.project_moon.events.client;

import ctn.project_moon.common.item.components.PmDataComponents;
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

/** 物品渲染附加 */
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemPropertyEvents {
    // 命名空间
    public static final ResourceLocation MODE_BOOLEAN = createProperties("mode_boolean");
    // 条件
    public static final ClampedItemPropertyFunction PROPERTY_MODE_BOOLEAN =
            (itemStack, clientLevel, livingEntity, i) ->
                    itemStack.get(PmDataComponents.MODE_BOOLEAN) ? 1 : 0;

    /** 注册物品渲染附加 */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        createProperties(event, PmItems.CREATIVE_SPIRIT_TOOL.asItem(), MODE_BOOLEAN, PROPERTY_MODE_BOOLEAN);
    }

    private static void createProperties(FMLClientSetupEvent event, Item item, ResourceLocation propertiesName, ClampedItemPropertyFunction propertyFunction) {
        event.enqueueWork(() -> ItemProperties.register(item, propertiesName, propertyFunction));
    }

    private static ResourceLocation createProperties(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
