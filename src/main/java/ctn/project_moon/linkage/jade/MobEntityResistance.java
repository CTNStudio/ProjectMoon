package ctn.project_moon.linkage.jade;

import ctn.project_moon.init.PmEntityAttributes;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.NotNull;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static ctn.project_moon.PmMain.MOD_ID;
import static snownee.jade.JadeInternals.getElementHelper;

public enum MobEntityResistance implements IEntityComponentProvider {
	INSTANCE;
	
	public static final String ATTRIBUTE_DESCRIPTION_KEY = getResourceLocation("entity.attribute_description").toLanguageKey();
	public static final String PHYSICS_KEY               = getResourceLocation("entity.attribute_description.physics").toLanguageKey();
	public static final String SPIRIT_KEY                = getResourceLocation("entity.attribute_description.rationality").toLanguageKey();
	public static final String EROSION_KEY               = getResourceLocation("entity.attribute_description.erosion").toLanguageKey();
	public static final String THE_SOUL_KEY              = getResourceLocation("entity.attribute_description.the_soul").toLanguageKey();
	
	/** 插入N个空格 */
	private static void emptys(ITooltip iTooltip) {
		iTooltip.append(Component.literal("   "));
	}
	
	/** 插入一个空格 */
	private static void empty(ITooltip iTooltip) {
		iTooltip.append(Component.literal(" "));
	}
	
	private static IElement getSprite(IElementHelper elements, String physics8x) {
		return elements.sprite(ResourceLocation.fromNamespaceAndPath(MOD_ID, physics8x), 8, 8);
	}
	
	private static @NotNull MutableComponent getComponent(LivingEntity entity, String key, int color, Holder<Attribute> attribute) {
		return Component.translatable(key).withColor(color)
		                .append(getAttributeValue(entity, attribute)).withColor(color);
	}
	
	private static @NotNull String getAttributeValue(LivingEntity entity, Holder<Attribute> attribute) {
		return String.format(" %.1f", entity.getAttributeValue(attribute));
	}
	
	private static @NotNull ResourceLocation getResourceLocation(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
	
	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		if (!(entityAccessor.getEntity() instanceof LivingEntity entity)) {
			return;
		}
		IElementHelper elements = getElementHelper();
		iTooltip.add(Component.translatable(ATTRIBUTE_DESCRIPTION_KEY));
		
		iTooltip.add(getSprite(elements, "physics8x"));
		empty(iTooltip);
		iTooltip.append(getComponent(entity, PHYSICS_KEY, PmColourTool.PHYSICS.getColourRGB(), PmEntityAttributes.PHYSICS_RESISTANCE));
		emptys(iTooltip);
		iTooltip.append(getSprite(elements, "spirit8x"));
		empty(iTooltip);
		iTooltip.append(getComponent(entity, SPIRIT_KEY, PmColourTool.SPIRIT.getColourRGB(), PmEntityAttributes.SPIRIT_RESISTANCE));
		
		iTooltip.add(getSprite(elements, "erosion8x"));
		empty(iTooltip);
		iTooltip.append(getComponent(entity, EROSION_KEY, PmColourTool.EROSION.getColourRGB(), PmEntityAttributes.EROSION_RESISTANCE));
		emptys(iTooltip);
		iTooltip.append(getSprite(elements, "the_soul8x"));
		empty(iTooltip);
		iTooltip.append(getComponent(entity, THE_SOUL_KEY, PmColourTool.THE_SOUL.getColourRGB(), PmEntityAttributes.THE_SOUL_RESISTANCE));
	}
	
	@Override
	public ResourceLocation getUid() {
		return PmPlugin.RESISTANCE;
	}
}
