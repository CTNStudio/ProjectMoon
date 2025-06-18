package ctn.project_moon.init;

import ctn.project_moon.capability.ILevel;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.capability.block.ILevelBlock;
import ctn.project_moon.capability.entity.IColorDamageTypeEntity;
import ctn.project_moon.capability.entity.IInvincibleTickEntity;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.capability.item.IUsageReqItem;
import ctn.project_moon.mixin.DamageSourceMixin;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmCapabilitys {
	/// 随机伤害
	public static final ItemCapability<IRandomDamage, Void> RANDOM_DAMAGE_ITEM =
			ItemCapability.createVoid(getResourceLocation("random_damage"), IRandomDamage.class);
	
	/// 物品使用条件
	public static final ItemCapability<IUsageReqItem, Void> USAGE_REQ_ITEM =
			ItemCapability.createVoid(getResourceLocation("usage_req_item"), IUsageReqItem.class);
	
	private static @NotNull ResourceLocation getResourceLocation(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
	
	public static class Skill {
		public static final ItemCapability<ISkillHandler, Void> SKILL_ITEM =
				ItemCapability.createVoid(getResourceLocation("skill_item"), ISkillHandler.class);
		
		public static final EntityCapability<ISkillHandler, Void> SKILL_ENTITY =
				EntityCapability.createVoid(getResourceLocation("skill_entity"), ISkillHandler.class);
	}
	
	/// 等级
	public static class Level {
		public static final ItemCapability<ILevel, Void> LEVEL_ITEM =
				ItemCapability.createVoid(getResourceLocation("level_item"), ILevel.class);
		
		public static final EntityCapability<ILevel, Void> LEVEL_ENTITY =
				EntityCapability.createVoid(getResourceLocation("level_entity"), ILevel.class);
		
		public static final BlockCapability<ILevelBlock, Void> LEVEL_BlOCK =
				BlockCapability.createVoid(getResourceLocation("level_block"), ILevelBlock.class);
		
	}
	
	/// 也可以通过mixin类 {@link DamageSourceMixin} 的 {@link IModDamageSource}接口方法代替或覆盖这些
	public static class InvincibleTick {
		public static final ItemCapability<IInvincibleTickItem, Void> INVINCIBLE_TICK_ITEM =
				ItemCapability.createVoid(getResourceLocation("invincible_tick_item"), IInvincibleTickItem.class);
		
		/// 注意：此能力是不被推荐的，因为实体的伤害方法是多种多样的应该使用<p>
		/// mixin类 {@link DamageSourceMixin} 的 {@link IModDamageSource}接口方法代替，<p>
		/// 或者你想给关于这个实体的所有都是一个值，那可以使用此能力
		/// <p>
		/// 应用场景：让玩家造成的伤害都没有无敌帧，弹幕
		/// <p>
		/// 注：基于原版的伤害系统
		public static final EntityCapability<IInvincibleTickEntity, Void> INVINCIBLE_TICK_ENTITY =
				EntityCapability.createVoid(getResourceLocation("invincible_tick_entity"), IInvincibleTickEntity.class);
	}
	
	/// 颜色伤害
	public static class ColorDamageType {
		public static final ItemCapability<IColorDamageTypeItem, Void> COLOR_DAMAGE_TYPE_ITEM =
				ItemCapability.createVoid(getResourceLocation("color_damage_type_item"), IColorDamageTypeItem.class);
		
		public static final EntityCapability<IColorDamageTypeEntity, Void> COLOR_DAMAGE_TYPE_ENTITY =
				EntityCapability.createVoid(getResourceLocation("color_damage_type_entity"), IColorDamageTypeEntity.class);
		
	}
}