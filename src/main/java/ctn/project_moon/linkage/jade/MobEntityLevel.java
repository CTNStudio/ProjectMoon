package ctn.project_moon.linkage.jade;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.tool.PmTool;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.tool.PmDamageTool.Level.getEntityLevel;

// 实体等级显示
public enum MobEntityLevel implements IEntityComponentProvider {
	INSTANCE;
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
	
	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		PmDamageTool.Level level = getEntityLevel(entityAccessor.getEntity());
		if (level == null) return;
		iTooltip.add(1, Component.literal(level.getName()).withColor(PmTool.colorConversion(level.getColourText())));
	}
	
	@Override
	public ResourceLocation getUid() {
		return PmPlugin.LEVEL;
	}
}
