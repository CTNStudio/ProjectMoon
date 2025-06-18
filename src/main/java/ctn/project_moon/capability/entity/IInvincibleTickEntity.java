package ctn.project_moon.capability.entity;

import net.minecraft.world.entity.Entity;

/** 用于判断是否更改攻击造成的无敌时间刻 */
public interface IInvincibleTickEntity {
	
	int getInvincibleTick(Entity entity);
}
