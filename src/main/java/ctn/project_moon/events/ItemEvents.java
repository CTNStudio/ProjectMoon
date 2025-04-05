package ctn.project_moon.events;

import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

/** 物品事件 */
public class ItemEvents{

    /** 物品提示 */
    @EventBusSubscriber(modid = MOD_ID)
    public static class ItemTooltip {
        @SubscribeEvent
        public static void itemTooltip(final ItemTooltipEvent event) {
            List<Component> component = Lists.newArrayList(event.getToolTip());
            ItemStack stack = event.getItemStack();
            levelText(component, stack);
            injuryType(component, stack);

            // 清除所有并重新提添加
            event.getToolTip().clear();
            event.getToolTip().addAll(component);
        }

        /** 等级 */
        private static void levelText(List<Component> tooltipComponents, ItemStack stack) {
            TagKey<Item> levelTags = EgoItem.getEgoLevelTag(stack);
            MutableComponent mutableComponent = switch (AbnosEntity.AbnosType.getTypeByTag(levelTags)) {
                case ZAYIN -> createColorText("ZAYIN", "#00ff00");
                case TETH -> createColorText("TETH", "#1e90ff");
                case HE -> createColorText("HE", "#ffff00");
                case WAW -> createColorText("WAW", "#8a2be2");
                case ALEPH -> createColorText("ALEPH", "#ff0000");
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

        /** 伤害类型 */
        private static void injuryType(List<Component> tooltipComponents, ItemStack stack) {
            List<TagKey<Item>> damageTypesTags = EgoItem.egoDamageTypes(stack);
            if (damageTypesTags.isEmpty()) {
                // TODO
                final boolean isEmpty = Objects.requireNonNullElse(stack.getComponents()
                                        .get(ATTRIBUTE_MODIFIERS), ItemAttributeModifiers.EMPTY)
                        .modifiers()
                        .stream()
                        .anyMatch(it -> it.matches(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID));
                if (!isEmpty) {
                    return;
                }
            }

            final var listIn = damageTypesTags.stream().filter(COLOR_MAP::containsKey).toList();
            if (listIn.isEmpty()) {
                return;
            }

            tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe.damage_type", "#AAAAAA"));
            listIn.forEach(it ->
                            tooltipComponents.add(i18ColorText(MOD_ID + ".item.geo_describe." +
                                    it.location().getPath(), COLOR_MAP.get(it))));
        }

        private static @NotNull MutableComponent createColorText(String text, String color) {
            return Component.literal(text).withColor(PmApi.colorConversion(color));
        }

        private static @NotNull MutableComponent i18ColorText(String text, String color) {
            return Component.translatable(text).withColor(PmApi.colorConversion(color));
        }
    }
}
