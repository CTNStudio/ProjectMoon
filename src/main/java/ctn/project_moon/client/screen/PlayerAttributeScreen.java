package ctn.project_moon.client.screen;

import ctn.project_moon.client.gui.widget.MessageTooltip;
import ctn.project_moon.client.gui.widget.PmImageSprite;
import ctn.project_moon.client.gui.widget.StateSprite;
import ctn.project_moon.common.menu.PlayerAttributeMenu;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICuriosScreen;

import java.text.MessageFormat;
import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.attr.FourColorAttribute.*;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;
import static net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PlayerAttributeScreen extends EffectRenderingInventoryScreen<PlayerAttributeMenu> implements ICuriosScreen {
	// 翻译键
	public static final String           PREFIX                       = MOD_ID + ".gui.player_attribute_v2.";
	public static final String           ATTRIBUTE_POINTS_TOOLTIP     = PREFIX + "attribute_points.message";
	public static final String           ATTRIBUTE_EXPERIENCE_TOOLTIP = PREFIX + "attribute_experience.message";
	public static final String           DAMAGE_RESISTANCE_TOOLTIP    = PREFIX + "damage_resistance.message_tooltip";
	public static final String           DAMAGE_RESISTANCE            = PREFIX + "damage_resistance.message";
	public static final String[]         ATTRIBUTE_TOOLTIP            = new String[]{
			PREFIX + "fortitude.message",
			PREFIX + "prudence.message",
			PREFIX + "temperance.message",
			PREFIX + "justice.message",
			PREFIX + "composite_rating.message"
	};
	public static final String[]         RESISTANCE_TOOLTIP           = new String[]{
			PREFIX + "physics.message",
			PREFIX + "rationality.message",
			PREFIX + "erosion.message",
			PREFIX + "the_soul.message"
	};
	
	// 纹理
	public static final ResourceLocation   BG           = getPath("textures/gui/container/player_attribute_v2.png");
	public static final ResourceLocation[] ATTRIBUTE_LV = {
			getPath("player_attribute/attribute_lv1"),
			getPath("player_attribute/attribute_lv2"),
			getPath("player_attribute/attribute_lv3"),
			getPath("player_attribute/attribute_lv4"),
			getPath("player_attribute/attribute_lv5"),
			getPath("player_attribute/attribute_lv_ex")
	};
	public static final ResourceLocation[] COMPOSITE_LV  = {
			getPath("player_attribute/lv1"),
			getPath("player_attribute/lv2"),
			getPath("player_attribute/lv3"),
			getPath("player_attribute/lv4"),
			getPath("player_attribute/lv5"),
			getPath("player_attribute/lv_ex")
	};
	public static final ResourceLocation[] RESISTANCE_LV = {
			getPath("player_attribute/resistance_immunity"),
			getPath("player_attribute/resistance_very_tall"),
			getPath("player_attribute/resistance_tall"),
			getPath("player_attribute/resistance_ordinary"),
			getPath("player_attribute/resistance_low"),
			getPath("player_attribute/resistance_very_low")
	};
	public static final ResourceLocation[] ATTRIBUTE  = {
			getPath("player_attribute/fortitude"),
			getPath("player_attribute/prudence"),
			getPath("player_attribute/temperance"),
			getPath("player_attribute/justice")
	};
	public static final ResourceLocation[] RESISTANCE = {
			getPath("player_attribute/resistance_physics"),
			getPath("player_attribute/resistance_rationality"),
			getPath("player_attribute/resistance_erosion"),
			getPath("player_attribute/resistance_the_soul")
	};
	public static final ResourceLocation SCROLLBAR = getPath("player_attribute/scrollbar");
	public static final ResourceLocation SCROLLBAR_PRESS = getPath("player_attribute/scrollbar_press");
	
	// 控件
	private final       StateSprite[] attributeLvWidget = new StateSprite[4];
	private final       StateSprite[] resistanceWidget  = new StateSprite[4];
	
	// 属性
	private final       Player           player;
	private             StateSprite      compositeRatingWidget;
	
	public PlayerAttributeScreen(PlayerAttributeMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		player = playerInventory.player;
	}
	
	/** 获取属性加成 */
	private static @NotNull MutableComponent getBonusComponent(double bonus) {
		MutableComponent component;
		if (bonus == 0) {
			component = Component.literal("+0 ");
		} else if (bonus < 0) {
			component = Component.literal(bonus + " ").withColor(CommonColors.SOFT_RED);
		} else {
			component = Component.literal("+" + bonus + " ").withColor(PmColourTool.TETH.getColourRGB());
		}
		return component;
	}
	
	/** 获取属性加成 */
	private static @NotNull MutableComponent getBonusComponent(AttributeModifier attributeModifier) {
		if (attributeModifier == null) {
			return Component.literal("");
		}
		final double amount = attributeModifier.amount();
		if (amount == 0) {
			return Component.literal(String.valueOf(0));
		}
		String symbol = amount > 0 ? "+" : "-";
		return Component.literal(MessageFormat.format(
				"{0}{1} ", switch (attributeModifier.operation()) {
					case ADD_VALUE -> symbol;
					case ADD_MULTIPLIED_BASE -> symbol + "*";
					case ADD_MULTIPLIED_TOTAL -> "*1" + symbol;
				}, Math.abs(amount))
		).withColor(amount > 0 ? PmColourTool.TETH.getColourRGB() : CommonColors.SOFT_RED);
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
	
	@Override
	protected void init() {
		imageWidth  = 176;
		imageHeight = 166;
		leftPos     = (this.width - this.imageWidth) / 2;
		topPos      = (this.height - this.imageHeight) / 2;
		compositeRatingWidget = new StateSprite(
				leftPos + 80, topPos + 19,
				16, 16, COMPOSITE_LV,
				Component.translatable(ATTRIBUTE_TOOLTIP[4]));
		
		addRenderableWidget(compositeRatingWidget);
		attributeLvInit();
		resistanceInit();
	}
	
	/// 属性
	private void attributeLvInit() {
		for (int i = 0; i < 4; i++) {
			PmImageSprite imageSprite = new PmImageSprite(
					leftPos + 8, topPos + 8 + 18 * i,
					16, 16, ATTRIBUTE[i],
					Component.translatable(ATTRIBUTE_TOOLTIP[i]));
			
			attributeLvWidget[i] = new StateSprite(
					leftPos + 27, topPos + 8 + (16 + 2) * i,
					16, 16, ATTRIBUTE_LV,
					Component.empty());
			
			addRenderableWidget(imageSprite);
			addRenderableWidget(attributeLvWidget[i]);
		}
		
		attributeLvWidget[0].setTooltip((widget, guiGraphics, mouseX, mouseY) -> {
			List<Component> list = addAttributeTooltip(widget, getBaseFortitude(player), FORTITUDE_ADDITIONAL);
			double bonus = player.getAttribute(MAX_HEALTH).getValue() - 20;
			
			addAttributeBonusTooltip(list, "attribute.name.max_health", bonus);
		});
		
		attributeLvWidget[1].setTooltip((widget, guiGraphics, mouseX, mouseY) -> {
			List<Component> list = addAttributeTooltip(widget, getBasePrudence(player), PRUDENCE_ADDITIONAL);
			double bonus = player.getAttribute(MAX_RATIONALITY).getValue() - 20;
			
			addAttributeBonusTooltip(list, "attribute.name.generic.max_rationality", bonus);
		});
		
		attributeLvWidget[2].setTooltip((widget, guiGraphics, mouseX, mouseY) -> {
			List<Component> list = addAttributeTooltip(widget, getBaseTemperance(player), TEMPERANCE_ADDITIONAL);
			AttributeModifier attributeModifierValue = getAttributeModifierValue(BLOCK_BREAK_SPEED, TEMPERANCE_ADD_BLOCK_BREAK_SPEED);
			AttributeModifier attributeModifierValue1 = getAttributeModifierValue(ATTACK_KNOCKBACK, TEMPERANCE_ADD_KNOCKBACK);
			
			addAttributeBonusTooltip(list, "attribute.name.player.block_break_speed", attributeModifierValue);
			addAttributeBonusTooltip(list, "attribute.name.generic.attack_knockback", attributeModifierValue1);
		});
		
		attributeLvWidget[3].setTooltip((widget, guiGraphics, mouseX, mouseY) -> {
			List<Component> list = addAttributeTooltip(widget, getBaseJustice(player), JUSTICE_ADDITIONAL);
			AttributeModifier attributeModifierValue = getAttributeModifierValue(MOVEMENT_SPEED, JUSTICE_ADD_MOVEMENT_SPEED);
			AttributeModifier attributeModifierValue1 = getAttributeModifierValue(ATTACK_SPEED, JUSTICE_ADD_ATTACK_SPEED);
			AttributeModifier attributeModifierValue2 = getAttributeModifierValue(SWIM_SPEED, JUSTICE_ADD_SWIM_SPEED);
			
			addAttributeBonusTooltip(list, "attribute.name.generic.movement_speed", attributeModifierValue);
			addAttributeBonusTooltip(list, "attribute.name.generic.attack_speed", attributeModifierValue1);
			addAttributeBonusTooltip(list, "neoforge.swim_speed", attributeModifierValue2);
		});
	}
	
	// 抗性
	private void resistanceInit() {
		for (int i = 0; i < 4; i++) {
			PmImageSprite imageSprite = new PmImageSprite(
					leftPos + 152, topPos + 8 + 18 * i,
					16, 16, RESISTANCE[i],
					Component.translatable(RESISTANCE_TOOLTIP[i]));
			
			resistanceWidget[i] = new StateSprite(
					leftPos + 133, topPos + 8 + (16 + 2) * i,
					16, 16, RESISTANCE_LV,
					Component.empty());
			
			addRenderableWidget(imageSprite);
			addRenderableWidget(resistanceWidget[i]);
		}
		
		resistanceWidget[0].setTooltip((widget, guiGraphics, mouseX, mouseY) ->
				                               addResistanceTooltip(widget, PHYSICS_RESISTANCE));
		
		resistanceWidget[1].setTooltip((widget, guiGraphics, mouseX, mouseY) ->
				                               addResistanceTooltip(widget, SPIRIT_RESISTANCE));
		
		resistanceWidget[2].setTooltip((widget, guiGraphics, mouseX, mouseY) ->
				                               addResistanceTooltip(widget, EROSION_RESISTANCE));
		
		resistanceWidget[3].setTooltip((widget, guiGraphics, mouseX, mouseY) ->
				                               addResistanceTooltip(widget, THE_SOUL_RESISTANCE));
	}
	
	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		compositeRatingWidget.setIndex(getCompositeRatting(player) - 1);
		modifyRatingWidget(0, getFortitudeRating(player) - 1);
		modifyRatingWidget(1, getPrudenceRating(player) - 1);
		modifyRatingWidget(2, getTemperanceRating(player) - 1);
		modifyRatingWidget(3, getJusticeRating(player) - 1);
		
		modifyResistanceWidget(0, PHYSICS_RESISTANCE);
		modifyResistanceWidget(1, SPIRIT_RESISTANCE);
		modifyResistanceWidget(2, EROSION_RESISTANCE);
		modifyResistanceWidget(3, THE_SOUL_RESISTANCE);
		
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		super.renderTooltip(guiGraphics, mouseX, mouseY);
	}
	
	private void addResistanceTooltip(MessageTooltip widget, Holder<Attribute> theSoulResistance) {
		widget.getMessageList().clear();
		List<Component> list = widget.getMessageList();
		list.add(Component.translatable(DAMAGE_RESISTANCE_TOOLTIP, player.getAttributeValue(theSoulResistance)));
		list.add(Component.translatable(DAMAGE_RESISTANCE));
	}
	
	private void modifyRatingWidget(int index, int value) {
		attributeLvWidget[index].setIndex(value);
	}
	
	private void modifyResistanceWidget(int index, Holder<Attribute> physicsResistance) {
		double state = player.getAttributeValue(physicsResistance);
		int i = 0;
		if (state < 0.5) {
			i = 1;
		} else if (state < 1.0) {
			i = 2;
		} else if (state == 1.0) {
			i = 3;
		} else if (state >= 2.0) {
			i = 5;
		} else if (state > 1.0) {
			i = 4;
		}
		resistanceWidget[index].setIndex(i);
	}
	
	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}
	
	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		guiGraphics.blit(BG, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
	}
	
	/** 获取属性修改器 */
	private AttributeModifier getAttributeModifierValue(Holder<Attribute> attribute, String attributeModifierName) {
		return player.getAttribute(attribute).getModifiers().stream()
		             .filter((a) -> a.id().equals(getPath(attributeModifierName)))
		             .findFirst().orElse(null);
	}
	
	/** 添加属性加成文本 */
	private void addAttributeBonusTooltip(List<Component> list, String key, double bonus) {
		list.add(getBonusComponent(bonus).append(Component.translatable(key)));
	}
	
	/** 添加属性加成文本 */
	private void addAttributeBonusTooltip(List<Component> list, String key, AttributeModifier attributeModifier) {
		if (attributeModifier == null) {
			return;
		}
		list.add(getBonusComponent(attributeModifier).append(Component.translatable(key)));
	}
	
	/** 添加属性点数和属性经验文本 */
	private List<Component> addAttributeTooltip(MessageTooltip widget, final int baseValue, Holder<Attribute> attribute) {
		widget.getMessageList().clear();
		List<Component> MessageList = widget.getMessageList();
		
		MessageList.add(getAttributePoints(baseValue, attribute));
		MessageList.add(gerAttributeExperience(0));// TODO 待实装属性经验
		return MessageList;
	}
	
	/**
	 * 获取属性点数
	 */
	private @NotNull Component getAttributePoints(final int baseValue, Holder<Attribute> attribute) {
		return Component.translatable(ATTRIBUTE_POINTS_TOOLTIP, getAdditionalComponent(baseValue, attribute));
	}
	
	/** 获取属性点数加成 */
	private @NotNull Component getAdditionalComponent(final int baseValue, Holder<Attribute> attribute) {
		MutableComponent component = Component.literal(String.valueOf(baseValue));
		final int additionalValue = (int) player.getAttribute(attribute).getValue();
		MutableComponent additionalComponent = Component.literal(String.valueOf(additionalValue));
		if (additionalValue == 0) {
			return component;
		}
		if (additionalValue < 0) {
			additionalComponent.withColor(CommonColors.SOFT_RED);
		} else {
			additionalComponent = Component.literal("+" + additionalValue).withColor(PmColourTool.TETH.getColourRGB());
		}
		return component.append(additionalComponent);
	}
	
	/** 获取属性经验 */
	private @NotNull Component gerAttributeExperience(final int exValue) {
		return Component.translatable(ATTRIBUTE_EXPERIENCE_TOOLTIP, exValue).withColor(CommonColors.GREEN);
	}
	
	/** 获取抗性 */
	private @NotNull String getResistanceString(Holder<Attribute> attribute) {
		return String.format("%.2f", player.getAttribute(attribute).getValue());
	}
}
