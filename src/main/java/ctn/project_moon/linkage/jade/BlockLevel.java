package ctn.project_moon.linkage.jade;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.tool.PmTool;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static ctn.project_moon.init.PmCapability.Level.LEVEL_BlOCK;

public enum BlockLevel implements IBlockComponentProvider {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip itooltip, BlockAccessor accessor, IPluginConfig config) {
		var capability = accessor.getLevel().getCapability(LEVEL_BlOCK, accessor.getPosition());
		if (capability == null) {
			return;
		}
		PmDamageTool.Level level = capability.getItemLevel(accessor.getBlockState());
		if (level == null) {
			return;
		}
		itooltip.add(1, Component.literal(level.getName()).withColor(PmTool.colorConversion(level.getColourText())));
	}

	@Override
	public ResourceLocation getUid() {
		return PmPlugin.LEVEL;
	}
}