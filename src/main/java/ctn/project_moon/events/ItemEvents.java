package ctn.project_moon.events;

import ctn.project_moon.PmMain;
import ctn.project_moon.api.PmApi;
import ctn.project_moon.common.items.EgoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

public class ItemEvents{

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
        MutableComponent mutableComponent = null;
        if (levelTags.equals(ALEPH)) {
            mutableComponent = createColorText("ALEPH", "#ff0000");
        } else if (levelTags.equals(WAW)) {
            mutableComponent = createColorText("WAW", "#8a2be2");
        } else if (levelTags.equals(HE)) {
            mutableComponent = createColorText("HE", "#ffff00");
        } else if (levelTags.equals(TETH)) {
            mutableComponent = createColorText("TETH", "#1e90ff");
        } else if (levelTags.equals(ZAYIN)) {
            mutableComponent = createColorText("ZAYIN", "#00ff00");
        }
        if (tooltipComponents.size() <= 1) {
            tooltipComponents.add(mutableComponent);
        }else {
            tooltipComponents.add(1, mutableComponent);
        }
    }

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

        for (TagKey<Item> itemTag : damageTypesTags) {
            if (itemTag.equals(PHYSICS)) {
                texts.put("physics", "#ff0000");
            } else if (itemTag.equals(SPIRIT)) {
                texts.put("spirit", "#ffffff");
            } else if (itemTag.equals(EROSION)) {
                texts.put("erosion", "#8a2be2");
            } else if (itemTag.equals(THE_SOUL)) {
                texts.put("the_soul", "#00ffff");
            }
        }
        Set<String> levelText = texts.keySet();
        if (levelText.isEmpty()) {
            return;
        }

        tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe.damage_type", "#AAAAAA"));
        for (String level : levelText) {
            String color = texts.get(level);
            tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe." + level, color));
        }
    }

    private static @NotNull MutableComponent createColorText(String text, String color) {
        return Component.literal(text).withColor(PmApi.colorConversion(color));
    }

    private static @NotNull MutableComponent i18ColorText(String text, String color) {
        return Component.translatable(text).withColor(PmApi.colorConversion(color));
    }
}
