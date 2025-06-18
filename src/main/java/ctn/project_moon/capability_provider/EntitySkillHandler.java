package ctn.project_moon.capability_provider;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.common.skill.Skill;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.init.PmPayloadInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;

import static ctn.project_moon.common.skill.SkillStack.validity;

public class EntitySkillHandler implements ISkillHandler {
	private NonNullList<SkillStack> skills = NonNullList.create();
	
	public EntitySkillHandler() {
	}
	
	/// 从list中加载并保存到tag中
	public EntitySkillHandler(Entity entity, List<SkillStack> skills) {
		this.skills.addAll(skills);
		saveAllSkills(entity.getPersistentData(), this.skills, entity.level().registryAccess());
	}
	
	public static void loadAllSkills(CompoundTag tag, ISkillHandler skillHandler, HolderLookup.Provider levelRegistry) {
		loadAllSkills(tag, skillHandler.getSkills(), levelRegistry);
	}
	
	public static void loadAllSkills(CompoundTag tag, NonNullList<SkillStack> skills, HolderLookup.Provider levelRegistry) {
		ListTag listtag = tag.getList("Skills", 10);
		for (int i = 0; i < listtag.size(); i++) {
			CompoundTag compoundtag = listtag.getCompound(i);
			skills.add(SkillStack.parse(levelRegistry, compoundtag).orElse(SkillStack.EMPTY));
		}
	}
	
	public static CompoundTag saveAllSkills(CompoundTag tag, ISkillHandler skillHandler, HolderLookup.Provider levelRegistry) {
		return saveAllSkills(tag, skillHandler.getSkills(), levelRegistry);
	}
	
	public static CompoundTag saveAllSkills(CompoundTag tag, NonNullList<SkillStack> skills, HolderLookup.Provider levelRegistry) {
		return saveAllSkills(tag, skills, true, levelRegistry);
	}
	
	public static CompoundTag saveAllSkills(CompoundTag tag, NonNullList<SkillStack> items, boolean alwaysPutTag, HolderLookup.Provider levelRegistry) {
		ListTag listtag = new ListTag();
		
		for (int i = 0; i < items.size(); i++) {
			SkillStack skillStack = items.get(i);
			if (!skillStack.isEmpty()) {
				CompoundTag compoundtag = new CompoundTag();
				compoundtag.putByte("Slot", (byte) i);
				listtag.add(skillStack.save(levelRegistry, compoundtag));
			}
		}
		
		if (!listtag.isEmpty() || alwaysPutTag) {
			tag.put("Skills", listtag);
		}
		
		return tag;
	}
	
	@Override
	public SkillStack getSkill(int index) {
		return skills.get(index);
	}
	
	@Override
	public void setSkill(NonNullList<SkillStack> skills) {
		this.skills = skills;
	}
	
	@Override
	public void setSkill(SkillStack skill, int index) {
		if (validity(skill)) return;
		skills.set(index, skill);
	}
	
	@Override
	public void addSkill(SkillStack skill) {
		if (validity(skill)) return;
		skills.add(skill);
	}
	
	@Override
	public void addSkill(SkillStack skill, int index) {
		if (validity(skill)) return;
		skills.add(index, skill);
	}
	
	@Override
	public SkillStack removeSkill(int index) {
		return skills.remove(index);
	}
	
	@Override
	public NonNullList<SkillStack> clearSkills() {
		NonNullList<SkillStack> skills = this.skills;
		this.skills.clear();
		return skills;
	}
	
	@Override
	public NonNullList<SkillStack> getSkills() {
		skills.removeIf(SkillStack::isEmpty);// 移除空的技能
		return skills;
	}
	
	@Override
	public void tick(Level level, Entity entity) {
		for (int i = 0, skillsSize = skills.size(); i < skillsSize; i++) {
			SkillStack skillStack = skills.get(i);
			if (skillStack.getCd() > 0) {
				skillStack.tick(level, entity);
				
				/// 给玩家客户端同步
				if (entity instanceof ServerPlayer serverPlayer) {
					PmPayloadInit.syncSkill(serverPlayer, skillStack, i);
				}
			}
		}
	}
	
	@Override
	public void addSkill(Skill skill) {
		if (Skill.validity(skill)) return;
		skills.add(skill.getDefaultSkillStack());
	}
	
	@Override
	public void addSkill(Skill skill, int index) {
		if (Skill.validity(skill)) return;
		skills.add(index, skill.getDefaultSkillStack());
	}
	
	@Override
	public void setSkill(Skill skill, int index) {
		if (Skill.validity(skill)) return;
		skills.set(index, skill.getDefaultSkillStack());
	}
}
