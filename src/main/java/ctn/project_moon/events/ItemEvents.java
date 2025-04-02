package ctn.project_moon.events;

import ctn.project_moon.api.PmApi;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.common.item.EgoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

@EventBusSubscriber(modid = MOD_ID)
public class ItemEvents{
    @SubscribeEvent
    public static void itemTooltip(final ItemTooltipEvent event){
        LinkedList<Component> component = new LinkedList<>(event.getToolTip());
        ItemStack stack = event.getItemStack();
        levelText(component, stack);
        injuryType(component, stack);
        event.getToolTip().clear();
        event.getToolTip().addAll(component);
    }

    private static void levelText(LinkedList<Component> tooltipComponents, ItemStack stack) {
        TagKey<Item> levelTags = EgoItem.getEgoLevelTag(stack);
        MutableComponent mutableComponent = switch(AbnosEntity.AbnosType.getTypeByTag(levelTags)) {
            case ZAYIN -> createColorText("ALEPH", "#ff0000");
            case TETH -> createColorText("WAW", "#8a2be2");
            case HE -> createColorText("HE", "#ffff00");
            case WAW -> createColorText("TETH", "#1e90ff");
            case ALEPH -> createColorText("ZAYIN", "#00ff00");
            case null -> null;
        };

        tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 0, 1), mutableComponent);
    }

    private static final Map<TagKey<Item>, String> COLOR_MAP = Map.of(
            PHYSICS, "#ff0000",
            SPIRIT, "#ffffff",
            EROSION, "#8a2be2",
            THE_SOUL, "#00ffff"
    );

    private static void injuryType(LinkedList<Component> tooltipComponents, ItemStack stack) {
        List<TagKey<Item>> damageTypesTags = EgoItem.egoDamageTypes(stack);
        LinkedHashMap<String, String> texts = new LinkedHashMap<>();
        if (damageTypesTags.isEmpty()) {
            if (!stack.getComponents().has(ATTRIBUTE_MODIFIERS)){
                return;
            }
            boolean isPhysics = false;
            for (ItemAttributeModifiers.Entry itemattributemodifiers$entry : stack.getComponents().get(ATTRIBUTE_MODIFIERS).modifiers()) {
                if (itemattributemodifiers$entry.matches(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID)) {
                    texts.put("physics", "#ff0000");
                    isPhysics = true;
                    break;
                }
            }
            if (!isPhysics){
                return;
            }
        }

        tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe.damage_type", "#AAAAAA"));
        damageTypesTags.stream()
                .filter(COLOR_MAP::containsKey)
                .forEach(it ->
                        tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe." + it.location().getPath()
                                , COLOR_MAP.get(it)))
                );
    }

    private static @NotNull MutableComponent createColorText(String text, String color) {
        return Component.literal(text).withColor(PmApi.colorConversion(color));
    }

    private static @NotNull MutableComponent i18ColorText(String text, String color) {
        return Component.translatable(text).withColor(PmApi.colorConversion(color));
    }
}
