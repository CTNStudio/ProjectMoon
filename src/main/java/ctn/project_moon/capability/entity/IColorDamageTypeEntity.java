package ctn.project_moon.capability.entity;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.entity.Entity;

public interface IColorDamageTypeEntity {
	PmDamageTool.ColorType getDamageType(Entity entity);
}
