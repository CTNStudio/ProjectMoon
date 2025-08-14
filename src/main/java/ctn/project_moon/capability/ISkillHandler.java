package ctn.project_moon.capability;

import ctn.project_moon.common.skill.Skill;
import ctn.project_moon.common.skill.SkillStack;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/// 技能接口
///
/// @author 尽
public interface ISkillHandler {
	SkillStack getSkill(int index);
	
	void setSkill(NonNullList<SkillStack> skills);
	
	void setSkill(SkillStack skill, int index);
	
	void setSkill(Skill skills, int index);
	
	void addSkill(SkillStack skill);
	
	void addSkill(SkillStack skill, int index);
	
	void addSkill(Skill skill);
	
	void addSkill(Skill skill, int index);
	
	SkillStack removeSkill(int index);
	
	NonNullList<SkillStack> clearSkills();
	
	NonNullList<SkillStack> getSkills();
	
	void tick(Level level, Entity entity);
	
	int getSkillSlotIndex();
	
	void setSkillSlotIndex(int slotIndex);
}
