package ctn.project_moon.events.client;

import com.mojang.blaze3d.platform.InputConstants;
import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Map;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PmCapabilitys.ColorDamageType.COLOR_DAMAGE_TYPE_ITEM;
import static ctn.project_moon.api.PmCapabilitys.RANDOM_DAMAGE_ITEM;
import static ctn.project_moon.api.PmCapabilitys.USAGE_REQ_ITEM;
import static ctn.project_moon.capability.ILevel.getItemLevel;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;
import static ctn.project_moon.tool.PmColourTool.colorConversion;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;

/**
 * 物品提示/描述处理
 */
@EventBusSubscriber(modid = MOD_ID)
public class ItemTooltipEvents {
	private static final Map<PmDamageTool.ColorType, String> COLOR_MAP = Map.of(
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
		if (!stack.getComponents().has(ITEM_COLOR_USAGE_REQ.get()) || stack.getCapability(USAGE_REQ_ITEM) == null) {
			return;
		}
		components.add(2, Component.translatable(MOD_ID + ".item_tooltip.use_condition"
				, Component.literal(Minecraft.ON_OSX ? "COMMAND" : "CTRL").withColor(colorConversion("#FFFFFF"))).withColor(colorConversion("#AAAAAA")));
	}
	
	/** 伤害类型文本 */
	private static void damageTypeText(ItemStack stack, List<Component> components) {
		// 物品没有IColorDamageTypeItem能力就返回
		final IColorDamageTypeItem capability = stack.getCapability(COLOR_DAMAGE_TYPE_ITEM);
		if (capability == null) {
			return;
		}
		
		// 物品是否有自定义的伤害描述
		final Component tooltip = capability.getColorDamageTypeToTooltip();
		if (tooltip != null) {
			components.add(Mth.clamp(components.size(), 1, 2), tooltip);
			return;
		}
		
		// 物品属性
		final ItemAttributeModifiers attribute = stack.getComponents().get(ATTRIBUTE_MODIFIERS);
		if (attribute == null) {
			return;
		}
		
		// 是否有近战伤害修改属性
		final boolean isEmpty = attribute.modifiers().stream().anyMatch(it -> it.matches(Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID));
		if (!isEmpty) {
			return;
		}
		
		// 获取物品可造成伤害类型
		List<PmDamageTool.ColorType> colorTypeList = capability.getCanCauseDamageTypes();
		if (colorTypeList == null) {
			colorTypeList = List.of(PHYSICS);
		}
		
		components.add(Mth.clamp(components.size(), 1, 2),
		               i18ColorText(MOD_ID + ".item_tooltip.geo_describe.damage_type", "#AAAAAA"));
		colorTypeList.forEach(colorType -> components.add(Mth.clamp(components.size(), 2, 3),
		                                                  Component.literal(" ").append(i18ColorText(MOD_ID + ".item_tooltip.geo_describe." + colorType.getName(), COLOR_MAP.get(colorType)))));
	}
	
	/** 详细描述文本 */
	private static void detailedText(ItemTooltipEvent event, ItemStack stack, List<Component> components) {
		if (!stack.getComponents().has(ITEM_COLOR_USAGE_REQ.get()) || stack.getCapability(USAGE_REQ_ITEM) == null) {
			return;
		}
		if (!(isKeyDown(GLFW_KEY_LEFT_CONTROL)) && !isKeyDown(GLFW_KEY_RIGHT_CONTROL)) {
			return;
		}
		components.clear();
		// 使用条件
		ItemColorUsageReq itemColorUsageReq = stack.get(ITEM_COLOR_USAGE_REQ);
		if (itemColorUsageReq == null) {
			return;
		}
		itemColorUsageReq.getToTooltip(components);
	}
	
	private static boolean isKeyDown(int keyCode) {
		return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyCode);
	}
	
	/**
	 * 等级文本
	 */
	private static void levelText(List<Component> components, ItemStack stack) {
		PmDamageTool.Level itemLevel = getItemLevel(stack);
		if (itemLevel == null) {
			return;
		}
		components.add(Mth.clamp(components.size(), 0, 1), createColorText(itemLevel.getName(), itemLevel.getColour().getColour()));
	}
	
	/** 添加随机伤害处理文本 */
	private static void randomDamageText(ItemTooltipEvent event, List<Component> components) {
		IRandomDamage capability = event.getItemStack().getCapability(RANDOM_DAMAGE_ITEM);
		if (capability == null) {
			return;
		}
		int maxDamage = capability.getMaxDamage();
		int minDamage = capability.getMinDamage();
		// 如果一致则不处理
		if (maxDamage == minDamage) {
			return;
		}
		for (Component damageTexts : components) {
			if (!(damageTexts.contains(Component.keybind(Attributes.ATTACK_DAMAGE.value().getDescriptionId())))) {
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
