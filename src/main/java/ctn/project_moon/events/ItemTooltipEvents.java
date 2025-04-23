package ctn.project_moon.events;

import ctn.project_moon.tool.GradeTypeTool;
import ctn.project_moon.tool.PmColourTool;
import ctn.project_moon.common.RandomDamageProcessor;
import ctn.project_moon.common.item.weapon.ChaosKnifeItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.PmTool.createColorText;
import static ctn.project_moon.tool.PmTool.i18ColorText;
import static ctn.project_moon.common.item.Ego.getItemLevel;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static ctn.project_moon.init.PmDamageTypes.Types.egoDamageTypes;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

/**
 * 物品提示/描述处理
 */
@EventBusSubscriber(modid = MOD_ID)
public class ItemTooltipEvents {
    private static final Map<TagKey<Item>, String> COLOR_MAP = Map.of(
            PHYSICS, PmColourTool.PHYSICS.getColour(),
            SPIRIT, PmColourTool.SPIRIT.getColour(),
            EROSION, PmColourTool.EROSION.getColour(),
            THE_SOUL, PmColourTool.THE_SOUL.getColour()
    );

    @SubscribeEvent
    public static void itemTooltip(final ItemTooltipEvent event) {
        List<Component> componentList = event.getToolTip();
        ItemStack stack = event.getItemStack();
        levelText(componentList, stack);
        injuryType(componentList, stack);
        randomDamageText(event, componentList);
    }

    /**
     * 等级文本
     */
    private static void levelText(List<Component> tooltipComponents, ItemStack stack) {
        MutableComponent mutableComponent = switch (getItemLevel(stack)) {
            case ZAYIN -> createColorText(GradeTypeTool.Level.ZAYIN.getName(), PmColourTool.ZAYIN.getColour());
            case TETH -> createColorText(GradeTypeTool.Level.TETH.getName(), PmColourTool.TETH.getColour());
            case HE -> createColorText(GradeTypeTool.Level.HE.getName(), PmColourTool.HE.getColour());
            case WAW -> createColorText(GradeTypeTool.Level.WAW.getName(), PmColourTool.WAW.getColour());
            case ALEPH -> createColorText(GradeTypeTool.Level.ALEPH.getName(), PmColourTool.ALEPH.getColour());
        };
        tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 0, 1), mutableComponent);
    }

    /**
     * 伤害类型文本
     */
    private static void injuryType(List<Component> tooltipComponents, ItemStack stack) {
        if (stack.getItem() instanceof ChaosKnifeItem) {
            return;
        }
        final List<TagKey<Item>> damageTypesTags = egoDamageTypes(stack);
        final boolean isEmpty = Objects.requireNonNullElse(stack.getComponents().get(ATTRIBUTE_MODIFIERS), ItemAttributeModifiers.EMPTY)
                .modifiers().stream()
                .anyMatch(it -> it.matches(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID));
        if (damageTypesTags.isEmpty()) {
            if (!isEmpty) {
                return;
            }
        }
        final var listIn = new ArrayList<>(damageTypesTags.stream().filter(COLOR_MAP::containsKey).toList());
        if (listIn.isEmpty()) {
            listIn.add(PHYSICS);
        }

        tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 1, 2), i18ColorText(MOD_ID + ".item.geo_describe.damage_type", "#AAAAAA"));
        listIn.forEach(it -> tooltipComponents.add(Mth.clamp(tooltipComponents.size(), 2, 3),
                i18ColorText(MOD_ID + ".item.geo_describe." + it.location().getPath(), COLOR_MAP.get(it))));
    }

    /** 添加随机伤害处理文本*/
    private static void randomDamageText(ItemTooltipEvent event, List<Component> componentList) {
        if (!(event.getItemStack().getItem() instanceof RandomDamageProcessor item)) {
            return;
        }
        int maxDamage = item.getMaxDamage();
        int minDamage = item.getMinDamage();
        // 如果一致则不处理
        if (maxDamage == minDamage) {
            return;
        }
        for (Component damageTexts : componentList) {
            if (!(damageTexts.contains(Component.keybind(Attributes.ATTACK_DAMAGE.value().getDescriptionId())))){
                continue;
            }
            int damage = 0;
            for (Component c2 : damageTexts.getSiblings()) {
                damage = Integer.parseInt((c2.getString().split(" ")[0]));
            }
            // 放置在数字前
            damageTexts.getSiblings().addFirst(Component.keybind(minDamage + (damage - maxDamage) + "~"));
        }
    }
}
