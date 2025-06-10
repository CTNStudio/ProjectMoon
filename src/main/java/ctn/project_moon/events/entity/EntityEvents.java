package ctn.project_moon.events.entity;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import javax.annotation.Nullable;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.addSpiritAttribute;
import static ctn.project_moon.api.SpiritAttribute.refreshSpiritValue;
import static ctn.project_moon.api.tool.PmDamageTool.resistanceTreatment;
import static ctn.project_moon.init.PmEntityAttributes.MAX_SPIRIT;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
	/** 即将受到伤害但还没处理 */
	@SubscribeEvent
	public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		if (!(damageSource instanceof IModDamageSource iModDamageSource)){
			return;
		}
		@Nullable
		PmDamageTool.ColorType damageColorType = iModDamageSource.getFourColorDamageTypes();
		@Nullable
		PmDamageTool.Level damageLevel = iModDamageSource.getDamageLevel();
		int invincibleTick = iModDamageSource.getInvincibleTick();
		event.setInvulnerabilityTicks(invincibleTick);
		// 根据四色伤害类型处理抗性
		resistanceTreatment(event, damageSource, damageLevel, damageColorType);
//
//
//
//		// 修改生物无敌帧
//		if (damageSource.getDirectEntity() instanceof IInvincibleTickItem entity) {
//			event.setInvulnerabilityTicks(entity.getInvincibleTick(itemStack));
//		} else if (damageSource.getEntity() instanceof IInvincibleTickItem entity) {
//			event.setInvulnerabilityTicks(entity.getInvincibleTick(itemStack));
//		} else if (itemStack != null) {
//			// 随机物品伤害处理
//			randomDamageLogic:
//			{
//				if (!(event.getSource().getEntity().level() instanceof ServerLevel serverLevel)) {
//					break randomDamageLogic;
//				}
//				IRandomDamage randomDamageItem = itemStack.getCapability(RANDOM_DAMAGE_ITEM);
//				if (randomDamageItem == null) {
//					break randomDamageLogic;
//				}
//				float damageScale = (event.getAmount() - randomDamageItem.getMaxDamage());
//				event.setAmount(randomDamageItem.getDamageValue(serverLevel.getRandom()) + damageScale);
//
//				if (itemStack.getItem() instanceof IInvincibleTickItem item) {
//					event.setInvulnerabilityTicks(item.getInvincibleTick(itemStack));
//				}
//			}
//		}
//
//		// 获取伤害来源等级
//		PmDamageTool.Level damageLevel = getDamageLevelDefault(damageSource, itemStack);
//
//		PmDamageTool.ColorType damageType = PmDamageTool.ColorType.getFourColorDamageTypeDefault(damageSource, itemStack);
//		// 根据四色伤害类型处理抗性
//		switch (damageType) {
//			case PHYSICS, THE_SOUL, EROSION, SPIRIT -> resistanceTreatment(event, damageLevel, damageType);
//			case null -> resistanceTreatment(event, damageLevel, null);
//		}
	}

	/**
	 * 实体死亡事件
	 */
	@SubscribeEvent
	public static void deathEvent(LivingDeathEvent event) {
	}

	@SubscribeEvent
	public static void addSpirtAttyibute(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof LivingEntity entity && entity.getAttributes().hasAttribute(MAX_SPIRIT)) {
			addSpiritAttribute(entity);
		}
	}

	@SubscribeEvent
	public static void entityTickEvent(EntityTickEvent.Pre event) {
		refreshSpiritValue(event);
	}
}
