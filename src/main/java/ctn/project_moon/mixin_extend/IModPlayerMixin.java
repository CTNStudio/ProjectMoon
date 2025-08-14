package ctn.project_moon.mixin_extend;

import ctn.project_moon.capability.ISkillHandler;

public interface IModPlayerMixin {
	ISkillHandler getSkillHandler();
	
	void setSkillHandler(ISkillHandler skillHandler);
}
