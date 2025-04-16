package ctn.project_moon.linkage.jade;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.api.PmApi;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.getEntityLevel;

public enum MobEntityLevel implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (!(entityAccessor.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        GradeType.Level level = getEntityLevel(entity);
        iTooltip.add(1, Component.literal(level.getName()).withColor(PmApi.colorConversion(level.getColourText())));
    }

    @Override
    public ResourceLocation getUid() {
        return PmPlugin.LEVEL;
    }

    private static @NotNull ResourceLocation getResourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
