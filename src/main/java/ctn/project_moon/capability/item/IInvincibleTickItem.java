package ctn.project_moon.capability.item;

import net.minecraft.world.item.ItemStack;

/** 用于判断是否更改攻击造成的无敌时间刻 */
public interface IInvincibleTickItem {
	int getInvincibleTick(ItemStack stack);
}
