package ctn.project_moon.events;

import com.mojang.blaze3d.platform.InputConstants;
import ctn.project_moon.common.RandomDamageProcessor;
import ctn.project_moon.common.item.RequestItems;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.ChaosKnifeItem;
import ctn.project_moon.tool.GradeTypeTool;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.client.Minecraft;
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
import static ctn.project_moon.common.item.Ego.getItemLevel;
import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static ctn.project_moon.init.PmDamageTypes.Types.egoDamageTypes;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;
import static ctn.project_moon.tool.PmTool.*;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;

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
        List<Component> components = event.getToolTip();
        ItemStack stack = event.getItemStack();
        levelText(components, stack);
        randomDamageText(event, components);
        damageTypeText(stack, components);

        itemReminderText(stack, components);
        detailedText(event, stack, components);
    }

    /** 提示文本 */
    private static void itemReminderText(ItemStack stack, List<Component> components) {
        if (!(stack.getItem() instanceof RequestItems)) {
            return;
        }
        components.add(2, Component.translatable(MOD_ID + ".item_tooltip.press_the_key",
                        Component.literal(Minecraft.ON_OSX ? "COMMAND" : "CTRL").withColor(colorConversion("#FFFFFF")))
                .withColor(colorConversion("#AAAAAA")));
    }

    /** 伤害类型文本 */
    private static void damageTypeText(ItemStack stack, List<Component> components) {
        if (stack.getItem() instanceof ChaosKnifeItem) {
            components.add(i18ColorText(MOD_ID + ".item_tooltip.geo_describe.damage_type", "#AAAAAA"));
            components.add(Component.literal(" ").append(createColorText(" ????", "#ffb638")));
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

        components.add(Mth.clamp(components.size(), 1, 2),i18ColorText(MOD_ID + ".item_tooltip.geo_describe.damage_type", "#AAAAAA"));
        listIn.forEach(it -> components.add(Mth.clamp(components.size(), 2, 3), Component.literal(" ").append(i18ColorText(MOD_ID + ".item_tooltip.geo_describe." + it.location().getPath(), COLOR_MAP.get(it)))));
    }

    /** 详细描述文本 */
    private static void detailedText(ItemTooltipEvent event, ItemStack stack, List<Component> components){
        if (stack.getItem() instanceof RequestItems && !Minecraft.ON_OSX ? !InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_LEFT_CONTROL) : !InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW_KEY_RIGHT_CONTROL)) {
            return;
        }
        components.clear();
        // 使用条件
        if (stack.getComponents().has(ITEM_COLOR_USAGE_REQ.get())) {
            ItemColorUsageReq itemColorUsageReq = stack.get(ITEM_COLOR_USAGE_REQ);
            if (itemColorUsageReq != null) {
                itemColorUsageReq.addToTooltip(event.getContext(), components, event.getFlags());
            }
        }
    }

    /**
     * 等级文本
     */
    private static void levelText(List<Component> components, ItemStack stack) {
        MutableComponent mutableComponent = switch (getItemLevel(stack)) {
            case ZAYIN -> createColorText(GradeTypeTool.Level.ZAYIN.getName(), PmColourTool.ZAYIN.getColour());
            case TETH -> createColorText(GradeTypeTool.Level.TETH.getName(), PmColourTool.TETH.getColour());
            case HE -> createColorText(GradeTypeTool.Level.HE.getName(), PmColourTool.HE.getColour());
            case WAW -> createColorText(GradeTypeTool.Level.WAW.getName(), PmColourTool.WAW.getColour());
            case ALEPH -> createColorText(GradeTypeTool.Level.ALEPH.getName(), PmColourTool.ALEPH.getColour());
        };
        components.add(Mth.clamp(components.size(), 0, 1), mutableComponent);
    }

    /** 添加随机伤害处理文本*/
    private static void randomDamageText(ItemTooltipEvent event, List<Component> components) {
        if (!(event.getItemStack().getItem() instanceof RandomDamageProcessor item)) {
            return;
        }
        int maxDamage = item.getMaxDamage();
        int minDamage = item.getMinDamage();
        // 如果一致则不处理
        if (maxDamage == minDamage) {
            return;
        }
        for (Component damageTexts : components) {
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
