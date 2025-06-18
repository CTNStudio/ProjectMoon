package ctn.project_moon.common.item.components;

import ctn.project_moon.capability_provider.EntitySkillHandler;
import ctn.project_moon.common.skill.SkillStack;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemSkillStack extends ToTooltip {
	private final EntitySkillHandler skillHandler = new EntitySkillHandler();
	
	public ItemSkillStack(NonNullList<SkillStack> skillStacks) {
		skillHandler.setSkill(skillStacks);
	}
	
	public ItemSkillStack(SkillStack... skillStacks) {
		for (SkillStack stack : skillStacks) {
			if (stack.isEmpty()) {
				continue;
			}
			skillHandler.addSkill(stack);
		}
	}
	
	@Override
	public void getToTooltip(List<Component> components) {
	
	}
}
