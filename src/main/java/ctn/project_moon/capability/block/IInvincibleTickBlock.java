package ctn.project_moon.capability.block;

import net.minecraft.world.level.block.state.BlockState;

/** 用于判断是否更改攻击造成的无敌时间刻 */
public interface IInvincibleTickBlock {
	int getInvincibleTick(BlockState state);
}
