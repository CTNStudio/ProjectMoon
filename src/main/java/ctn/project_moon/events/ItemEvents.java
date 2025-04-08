package ctn.project_moon.events;

import com.google.common.collect.Lists;
import ctn.project_moon.api.GradeType;
import ctn.project_moon.api.PmApi;
import ctn.project_moon.api.PmColour;
import ctn.project_moon.common.item.EgoItem;
import ctn.project_moon.common.item.creative_tool.ChaosKnifeItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PmApi.createColorText;
import static ctn.project_moon.api.PmApi.i18ColorText;
import static ctn.project_moon.common.item.EgoItem.getItemLevel;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

/**
 * 物品事件
 */
public class ItemEvents {
    /**
     * 物品提示
     */
    @EventBusSubscriber(modid = MOD_ID)
    public static class ItemTooltip {
        private static final Map<TagKey<Item>, String> COLOR_MAP = Map.of(
                PHYSICS, PmColour.PHYSICS.getColour(),
                SPIRIT, PmColour.SPIRIT.getColour(),
                EROSION, PmColour.EROSION.getColour(),
                THE_SOUL, PmColour.THE_SOUL.getColour()
        );

        @SubscribeEvent
        public static void itemTooltip(final ItemTooltipEvent event) {
            List<Component> component = event.getToolTip();
            ItemStack stack = event.getItemStack();
            levelText(component, stack);
            injuryType(component, stack);
        }

        /**
         * 等级
         */
        private static void levelText(List<Component> tooltipComponents, ItemStack stack) {
            MutableComponent mutableComponent = switch (getItemLevel(stack)) {
                case ZAYIN -> createColorText(GradeType.Level.ZAYIN.getName(), PmColour.ZAYIN.getColour());
                case TETH -> createColorText(GradeType.Level.TETH.getName(), PmColour.TETH.getColour());
                case HE -> createColorText(GradeType.Level.HE.getName(), PmColour.HE.getColour());
                case WAW -> createColorText(GradeType.Level.WAW.getName(), PmColour.WAW.getColour());
                case ALEPH -> createColorText(GradeType.Level.ALEPH.getName(), PmColour.ALEPH.getColour());
            };
            tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 0, 1), mutableComponent);
        }

        /**
         * 伤害类型
         */
        private static void injuryType(List<Component> tooltipComponents, ItemStack stack) {
            if (stack.getItem() instanceof ChaosKnifeItem) {
                return;
            }
            final List<TagKey<Item>> damageTypesTags = EgoItem.egoDamageTypes(stack);
            if (damageTypesTags.isEmpty()) {
                final boolean isEmpty =
                        Objects.requireNonNullElse(
                                        stack.getComponents().get(ATTRIBUTE_MODIFIERS), ItemAttributeModifiers.EMPTY)
                                .modifiers()
                                .stream()
                                .anyMatch(it -> it.matches(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID));
                if (!isEmpty) {
                    return;
                }
            }

            final var listIn = new ArrayList<>(damageTypesTags.stream().filter(COLOR_MAP::containsKey).toList());
            if (listIn.isEmpty()) {
                listIn.add(PHYSICS);
            }

            tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 1, 2),i18ColorText(MOD_ID + ".item.geo_describe.damage_type", "#AAAAAA"));
            listIn.forEach(it -> tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 2, 3),
                    i18ColorText(MOD_ID + ".item.geo_describe." + it.location().getPath(), COLOR_MAP.get(it))));
        }
    }
}
