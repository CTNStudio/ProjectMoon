package ctn.project_moon.capability.block;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.CheckForNull;

public interface ILevelBlock {
	@CheckForNull
	PmDamageTool.Level getItemLevel(BlockState state);
}
