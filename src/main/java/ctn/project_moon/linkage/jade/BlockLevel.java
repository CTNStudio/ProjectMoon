package ctn.project_moon.linkage.jade;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.api.PmApi;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static ctn.project_moon.api.GradeType.Level.getBlockLevel;
import static ctn.project_moon.api.GradeType.Level.getEgoLevelTag;

public enum BlockLevel implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip itooltip, BlockAccessor accessor, IPluginConfig config) {
        GradeType.Level level = getBlockLevel(getEgoLevelTag(accessor.getBlockState()));
        itooltip.add(1, Component.literal(level.getName()).withColor(PmApi.colorConversion(level.getColourText())));
    }

    @Override
    public ResourceLocation getUid() {
        return PmPlugin.LEVEL;
    }
}