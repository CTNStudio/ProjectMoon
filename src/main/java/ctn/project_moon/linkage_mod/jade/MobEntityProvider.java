package ctn.project_moon.linkage_mod.jade;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.api.PmApi;
import ctn.project_moon.api.PmColour;
import ctn.project_moon.init.PmAttributes;
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

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.*;

public enum MobEntityProvider implements IEntityComponentProvider {
    INSTANCE;

    public static final String ATTRIBUTE_DESCRIPTION_KEY = getResourceLocation("entity.attribute_description").toLanguageKey();
    public static final String PHYSICS_KEY = getResourceLocation("entity.attribute_description.physics").toLanguageKey();
    public static final String SPIRIT_KEY = getResourceLocation("entity.attribute_description.spirit").toLanguageKey();
    public static final String EROSION_KEY = getResourceLocation("entity.attribute_description.erosion").toLanguageKey();
    public static final String THE_SOUL_KEY = getResourceLocation("entity.attribute_description.the_soul").toLanguageKey();

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (!(entityAccessor.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        GradeType.Level level = getEntityLevel(entity);
        iTooltip.add(1, Component.literal(level.getName()).withColor(PmApi.colorConversion(level.getColourText())));
        iTooltip.add(2, Component.translatable(ATTRIBUTE_DESCRIPTION_KEY));
        iTooltip.add(3, getComponent(entity, PHYSICS_KEY, PmColour.PHYSICS.getColourRGB(), PmAttributes.PHYSICS_RESISTANCE)
                .append("   ").append(getComponent(entity, SPIRIT_KEY, PmColour.SPIRIT.getColourRGB(), PmAttributes.SPIRIT_RESISTANCE))
        );
        iTooltip.add(4, getComponent(entity, EROSION_KEY, PmColour.EROSION.getColourRGB(), PmAttributes.EROSION_RESISTANCE)
                .append("   ").append(getComponent(entity, THE_SOUL_KEY, PmColour.THE_SOUL.getColourRGB(), PmAttributes.THE_SOUL_RESISTANCE)));
    }

    private static @NotNull MutableComponent getComponent(LivingEntity entity, String key, int color, Holder<Attribute> attribute) {
        return Component.translatable(key).withColor(color)
                .append(getAttributeValue(entity, attribute)).withColor(color);
    }

    private static @NotNull String getAttributeValue(LivingEntity entity, Holder<Attribute> attribute) {
        return String.format(" %.1f", entity.getAttributeValue(attribute));
    }

    @Override
    public ResourceLocation getUid() {
        return PmPlugin.LEVEL;
    }

    private static @NotNull ResourceLocation getResourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
