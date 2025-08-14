package ctn.project_moon.init;

import ctn.project_moon.common.skill.Skill;
import ctn.project_moon.common.skill.SkillStack;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.skill.Skill.EMPTY_SKILL;

public class PmSkills {
	public static final DeferredRegister<Skill> SKILL_REGISTRY = DeferredRegister.create(PmRegistries.SKILL, MOD_ID);
	
	public static final DeferredHolder<Skill, Skill> EMPTY                  =
			createSkill("empty", EMPTY_SKILL);
	public static final DeferredHolder<Skill, Skill> MAGIC_BULLET_SILENCING =
			createSkill("magic_bullet_silencing", SkillStack.Type.POSSESS, 20 * 5);
	
	public static final DeferredHolder<Skill, Skill> MAGIC_BULLET_PENETRATING =
			createSkill("magic_bullet_penetrating", SkillStack.Type.ADDITIONAL, 20 * 8);
	
	public static final DeferredHolder<Skill, Skill> MAGIC_BULLET_FLOODED =
			createSkill("magic_bullet_flooded", SkillStack.Type.ADDITIONAL, 20 * 10);
	
	private static @NotNull DeferredHolder<Skill, Skill> createSkill(String name, Skill skill) {
		return SKILL_REGISTRY.register(name, () -> skill);
	}
	
	private static @NotNull DeferredHolder<Skill, Skill> createSkill(String name, SkillStack.Type defaultType, int maxCd) {
		return SKILL_REGISTRY.register(name, () -> new Skill(getPath(name), getPath(name), defaultType, maxCd));
	}
	
	private static @NotNull DeferredHolder<Skill, Skill> createSkill(String name, SkillStack.Type defaultType, int maxCd, String defaultKey) {
		return SKILL_REGISTRY.register(name, () -> new Skill(getPath(name), getPath(name), defaultType, maxCd, defaultKey));
	}
	
	private static @NotNull ResourceLocation getPath(String name) {
		return ResourceLocation.fromNamespaceAndPath(SKILL_REGISTRY.getRegistryName().getNamespace(), name);
	}
}
