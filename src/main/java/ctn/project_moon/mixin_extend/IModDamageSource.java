package ctn.project_moon.mixin_extend;

import ctn.project_moon.api.tool.PmDamageTool;

import javax.annotation.CheckForNull;

public interface IModDamageSource {
	@CheckForNull
	PmDamageTool.ColorType getFourColorDamageTypes();
	
	void setFourColorDamageTypes(PmDamageTool.ColorType type);
	
	@CheckForNull
	PmDamageTool.Level getDamageLevel();
	
	void setDamageLevel(PmDamageTool.Level level);
	
	int getInvincibleTick();
	
	void setInvincibleTick(int tick);
}
