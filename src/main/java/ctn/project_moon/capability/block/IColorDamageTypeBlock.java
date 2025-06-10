package ctn.project_moon.capability.block;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.level.block.state.BlockState;

public interface IColorDamageTypeBlock {
	PmDamageTool.ColorType getDamageType(BlockState state);
}
