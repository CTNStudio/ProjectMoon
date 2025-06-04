package ctn.project_moon.mixin_extend;

import ctn.project_moon.api.tool.PmDamageTool;

public interface PmDamageSourceMixin {
	PmDamageTool.FourColorType getFourColorDamageTypes();
	PmDamageTool.Level getDamageLevel();
}
