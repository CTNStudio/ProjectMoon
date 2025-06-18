package ctn.project_moon.common.skill;

import ctn.project_moon.init.PmRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.skill.SkillStack.Type.ADDITIONAL;

/// 技能
///
/// @author 尽
public class Skill {
	private static final ResourceLocation EMPTY       = ResourceLocation.fromNamespaceAndPath(MOD_ID, "empty");
	public static final  Skill            EMPTY_SKILL = new Skill(EMPTY, EMPTY, 0);
	protected final      ResourceLocation skillId;
	/// 路径 resources/assets/{mod_id}/textures/skills/
	protected final      ResourceLocation iconPath;
	protected final      int              maxCd;
	/// 默认技能按键
	protected            int              defaultKey;
	protected            List<Component>  describe;
	protected            SkillStack       defaultSkillStack;
	
	public Skill(ResourceLocation id, ResourceLocation iconPath, SkillStack.Type defaultType, int maxCd, int defaultKey) {
		this.skillId           = id;
		this.iconPath          = iconPath;
		this.maxCd             = maxCd;
		this.defaultSkillStack = new SkillStack(this, 0, defaultType);
		this.defaultKey        = defaultKey;
	}
	
	public Skill(ResourceLocation id, ResourceLocation iconPath, int maxCd, int defaultKey) {
		this(id, iconPath, ADDITIONAL, maxCd, defaultKey);
	}
	
	public Skill(ResourceLocation id, ResourceLocation iconPath, int maxCd) {
		this(id, iconPath, maxCd, -1);
	}
	
	public Skill(ResourceLocation id, ResourceLocation iconPath, SkillStack.Type defaultType, int maxCd) {
		this(id, iconPath, defaultType, maxCd, -1);
	}
	
	public static boolean validity(Skill skill) {
		return skill == null || skill.isEmpty();
	}
	
	public ResourceLocation getSkillId() {
		return skillId;
	}
	
	public ResourceLocation getIconPath() {
		return iconPath;
	}
	
	public int getMaxCd() {
		return maxCd;
	}
	
	public SkillStack getDefaultSkillStack() {
		return defaultSkillStack;
	}
	
	public void setDefaultSkillStack(SkillStack.Type type) {
		defaultSkillStack = new SkillStack(this, maxCd, type);
	}
	
	public void setDefaultSkillStack(SkillStack skillStack) {
		this.defaultSkillStack = skillStack;
	}
	
	/// 名称
	public MutableComponent getTooltip() {
		return Component.translatable("skills." + skillId.toString());
	}
	
	public List<Component> getDescribe() {
		return describe;
	}
	
	public void setDescribe(List<Component> describe) {
		this.describe = describe;
	}
	
	public void tick(Level level, Entity entity) {
	}
	
	public boolean isEmpty() {
		return skillId == null || iconPath == null || skillId.equals(EMPTY);
	}
	
	//	public Holder.Reference<Skill> builtInRegistryHolder() {
//		return this.builtInRegistryHolder;
//	}
//
	public Holder.Reference<Skill> builtInRegistryHolder() {
		return PmRegistries.SKILL.getHolder(skillId).orElseThrow();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Skill skill)) {
			return false;
		}
		return skill.getSkillId().equals(skillId);
	}
}
