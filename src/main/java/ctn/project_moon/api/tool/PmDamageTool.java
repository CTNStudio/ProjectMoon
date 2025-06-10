package ctn.project_moon.api.tool;

import ctn.project_moon.capability.entity.IAbnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static ctn.project_moon.PmMain.LOGGER;
import static ctn.project_moon.api.SpiritAttribute.handleRationally;
import static ctn.project_moon.capability.ILevel.getItemLevelValue;
import static ctn.project_moon.init.PmCapability.Level.LEVEL_ENTITY;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

/**
 * ProjectMoon伤害系统工具
 *
 * @author 尽
 */
public class PmDamageTool {
	/// 获取伤害物品
	@CheckForNull
	public static ItemStack getDamageItemStack(DamageSource damageSource) {
		ItemStack itemStack = damageSource.getWeaponItem();
		if (itemStack == null && damageSource.getEntity() != null) {
			itemStack = damageSource.getEntity().getWeaponItem();
		}
		return itemStack;
	}

	/** 伤害计算 */
	public static void resistanceTreatment(
			LivingIncomingDamageEvent event,
			DamageSource damageSource,
			@Nullable PmDamageTool.Level damageLevel,
			@Nullable ColorType fourColorDamageTypes) {
		if (damageLevel == null && fourColorDamageTypes == null) {
			return;
		}

		float newDamageAmount = event.getAmount();
		int armorItemStackLaval = 0; // 盔甲等级
		int number = 0; // 护甲数量
		boolean isArmorItemStackEmpty = true;
		boolean flag = !(event.getEntity() instanceof IAbnos);
		//  盔甲
		Iterator<ItemStack> itor = event.getEntity().getArmorAndBodyArmorSlots().iterator();

		ItemStack[] armorSlots = new ItemStack[4];
		for (int i = 0; i < 4; i++) {
			armorSlots[i] = flag ? itor.next() : ItemStack.EMPTY;
		}

		for (ItemStack armorItemStack : armorSlots) {
			if (armorItemStack != null && !armorItemStack.isEmpty()) {
				isArmorItemStackEmpty = false;
				armorItemStackLaval += getItemLevelValue(armorItemStack);
				number++;
			}
		}

		/// 等级处理
		/// 判断实体是否有护甲如果没有就用实体的等级
		if (damageLevel != null) {
			if (!isArmorItemStackEmpty) {
				armorItemStackLaval /= number;
				newDamageAmount *= getDamageMultiple(armorItemStackLaval - damageLevel.getLevelValue());
			} else {
				var level = event.getEntity().getCapability(LEVEL_ENTITY);
				if (level != null) {
					PmDamageTool.Level entityLaval = level.getItemLevel();
					newDamageAmount *= getDamageMultiple(entityLaval, damageLevel);
				} else {
				}
			}
		}


		if (fourColorDamageTypes != null) {
			if (PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get()) {
				if (fourColorDamageConfigImpact(fourColorDamageTypes)) {
					fourColorDamageTypes = ColorType.PHYSICS;
				}

				/// 抗性处理
				newDamageAmount *= (float) switch (fourColorDamageTypes) {
					case PHYSICS -> event.getEntity().getAttributeValue(PHYSICS_RESISTANCE);
					case SPIRIT -> event.getEntity().getAttributeValue(SPIRIT_RESISTANCE);
					case EROSION -> event.getEntity().getAttributeValue(EROSION_RESISTANCE);
					case THE_SOUL -> event.getEntity().getAttributeValue(THE_SOUL_RESISTANCE);
				};
			}
		}

		event.setAmount(newDamageAmount);
	}

	/** 四色伤害配置影响 */
	private static boolean fourColorDamageConfigImpact(ColorType fourColorDamageTypes) {
		if (!PmConfig.SERVER.ENABLE_THE_SOUL_DAMAGE.get() && fourColorDamageTypes.equals(ColorType.THE_SOUL)) {
			return true;
		}
		if (!(PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() || PmConfig.COMMON.ENABLE_RATIONALITY.get() && fourColorDamageTypes.equals(ColorType.SPIRIT))) {
			return true;
		}
		return false;
	}

	/**
	 * 返回实体或物品的伤害倍数
	 */
	public static float getDamageMultiple(Level laval, Level laval2) {
		return getDamageMultiple(leveDifferenceValue(laval, laval2));
	}

	/**
	 * 返回实体或物品之间的等级差值
	 */
	public static int leveDifferenceValue(Level level, Level level2) {
		return level.getLevelValue() - level2.getLevelValue();
	}

	/**
	 * 获取伤害倍数
	 */
	public static float getDamageMultiple(int i) {
		return switch (i) {
			case 4 -> 0.4F;
			case 3 -> 0.6F;
			case 2 -> 0.7F;
			case 1 -> 0.8F;
			case 0, -1 -> 1.0F;
			case -2 -> 1.2F;
			case -3 -> 1.5F;
			case -4 -> 2.0F;
			default -> {
				LOGGER.info("Ego difference error");
				throw new IllegalArgumentException("Ego difference error");
			}
		};
	}

	/** 低抗减慢 */
	public static boolean applySlowdownIfAttributeExceedsOne(Holder<Attribute> attribute, @NotNull LivingEntity entity) {
		if (entity.getAttributeValue(attribute) > 1.0) {
			entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
			return true;
		}
		return false;
	}

	/** 灵魂伤害判断 */
	public static boolean doesTheOrganismSufferFromTheSoulDamage(LivingEntity entity) {
		if (!PmConfig.SERVER.THE_SOUL_AFFECT_ABOMINATIONS.get() && entity instanceof AbnosEntity) {
			return true;
		}
		if (!PmConfig.SERVER.THE_SOUL_AFFECT_PLAYERS.get() && entity instanceof Player) {
			return true;
		}
		if (!PmConfig.SERVER.THE_SOUL_AFFECT_ENTITIES.get() && !(entity instanceof AbnosEntity || entity instanceof Player)) {
			return true;
		}
		return false;
	}

	/** 扣除生命 */
	public static boolean reply(LivingDamageEvent.Pre event, LivingEntity entity) {
		float newDamage = event.getNewDamage();
		if (newDamage <= 0) { // 如果低于0则恢复生命值
			entity.heal(-newDamage);
			event.getContainer().setPostAttackInvulnerabilityTicks(0);
			return true;
		}
		return false;
	}

	/** 百分比扣生命 */
	public static void executeTheSoulDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
		if (reply(event, entity)) return;
		float max = entity.getMaxHealth();
		event.setNewDamage(max * (event.getNewDamage() / 100));
	}

	/** 如果受伤者没有理智，则理智和生命同时减少 */
	public static void executeErosionDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
		handleRationally(event, entity);
	}

	/** 四色伤害类型 */
	public enum ColorType {
		/**
		 * 物理
		 */
		PHYSICS(0, "physics", PmColourTool.PHYSICS),
		/**
		 * 精神
		 */
		SPIRIT(1, "spirit", PmColourTool.SPIRIT),
		/**
		 * 侵蚀
		 */
		EROSION(2, "erosion", PmColourTool.EROSION),
		/**
		 * 灵魂
		 */
		THE_SOUL(3, "the_soul", PmColourTool.THE_SOUL),
		;

		private final int index;
		private final String       name;
		private final PmColourTool colour;

		ColorType(int index, String name, PmColourTool colour) {
			this.index  = index;
			this.name   = name;
			this.colour = colour;
		}

		public static ColorType is(String name) {
			for (ColorType colorType : ColorType.values()) {
				if (colorType.name.equals(name)){
					return colorType;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public PmColourTool getColour() {
			return colour;
		}

		public int getIndex() {
			return index;
		}
	}

	/** 等级 */
	public enum Level {
		ZAYIN("ZAYIN", 1, PmColourTool.ZAYIN),
		TETH("TETH", 2, PmColourTool.TETH),
		HE("HE", 3, PmColourTool.HE),
		WAW("WAW", 4, PmColourTool.WAW),
		ALEPH("ALEPH", 5, PmColourTool.ALEPH);

		private final String       name;
		private final int          levelValue;
		private final PmColourTool colour;

		Level(String name, int levelValue, PmColourTool colour) {
			this.name       = name;
			this.levelValue = levelValue;
			this.colour     = colour;
		}

		public String getName() {
			return name;
		}

		public int getLevelValue() {
			return levelValue;
		}

		public PmColourTool getColour() {
			return colour;
		}

		public String getColourText() {
			return colour.getColour();
		}
	}
}
