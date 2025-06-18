package ctn.project_moon.api.tool;

import ctn.project_moon.capability.ILevel;
import ctn.project_moon.capability.entity.IAbnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.*;

import static ctn.project_moon.api.attr.RationalityAttribute.handleSpirit;
import static ctn.project_moon.api.tool.PmDamageTool.Level.getEntityLevel;
import static ctn.project_moon.capability.ILevel.getItemLevelValue;
import static ctn.project_moon.init.PmCapabilitys.Level.LEVEL_ENTITY;
import static ctn.project_moon.init.PmDamageTypes.*;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

/**
 * ProjectMoon伤害系统工具
 *
 * @author 尽
 */
public class PmDamageTool {
	private static final List<ResourceKey<DamageType>> VANILLA_PHYSICS_KEYS = List.of(
			FALLING_ANVIL,
			FALLING_BLOCK,
			FALLING_STALACTITE,
			FIREWORKS,
			FLY_INTO_WALL,
			MOB_ATTACK,
			MOB_ATTACK_NO_AGGRO,
			MOB_PROJECTILE,
			PLAYER_ATTACK,
			SPIT,
			STING,
			SWEET_BERRY_BUSH,
			THORNS,
			THROWN,
			TRIDENT,
			UNATTRIBUTED_FIREBALL,
			WIND_CHARGE,
			ARROW,
			CACTUS,
			BAD_RESPAWN_POINT,
			FALL,
			FIREBALL,
			FLY_INTO_WALL,
			PHYSICS);
	
	private static final List<ResourceKey<DamageType>> VANILLA_RATIONALITY_KEYS = List.of(
			MOB_PROJECTILE,
			SPIRIT);
	
	private static final List<ResourceKey<DamageType>> VANILLA_EROSION_KEYS = List.of(
			WITHER_SKULL,
			WITHER,
			EROSION);
	
	private static final List<ResourceKey<DamageType>> VANILLA_THE_SOUL_KEYS = List.of(
			SONIC_BOOM,
			THE_SOUL);
	
	private static final Map<ResourceKey<DamageType>, ColorType> DAMAGE_TYPE_MAP = new HashMap<>();
	
	private static final List<ResourceKey<DamageType>> BYPASS_KEYS = List.of(
			IN_WALL,
			GENERIC,
			FREEZE,
			DRAGON_BREATH,
			MAGIC,
			FELL_OUT_OF_WORLD,
			OUTSIDE_BORDER,
			STARVE,
			CRAMMING,
			GENERIC_KILL
	);
	
	/// AI生成的初始化优化
	static {
		// 将所有伤害类型注册到 Map 中
		for (ResourceKey<DamageType> key : VANILLA_PHYSICS_KEYS) {
			DAMAGE_TYPE_MAP.put(key, PmDamageTool.ColorType.PHYSICS);
		}
		for (ResourceKey<DamageType> key : VANILLA_RATIONALITY_KEYS) {
			DAMAGE_TYPE_MAP.put(key, PmDamageTool.ColorType.SPIRIT);
		}
		for (ResourceKey<DamageType> key : VANILLA_EROSION_KEYS) {
			DAMAGE_TYPE_MAP.put(key, PmDamageTool.ColorType.EROSION);
		}
		for (ResourceKey<DamageType> key : VANILLA_THE_SOUL_KEYS) {
			DAMAGE_TYPE_MAP.put(key, PmDamageTool.ColorType.THE_SOUL);
		}
	}
	
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
			@Nullable Level damageLevel,
			@Nullable ColorType fourColorDamageTypes) {
		for (ResourceKey<DamageType> damageType : BYPASS_KEYS) {
			if (damageSource.typeHolder().is(damageType)) {
				return;
			}
		}
		
		float newDamageAmount = event.getAmount();
		int armorItemStackLaval = 0; // 盔甲等级
		int number = 0; // 护甲数量
		boolean isArmorItemStackEmpty = true;
		LivingEntity entity = event.getEntity();
		boolean flag = !(entity instanceof IAbnos);
		//  盔甲
		Iterator<ItemStack> itor = entity.getArmorAndBodyArmorSlots().iterator();
		
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
		if (damageLevel == null) {
			damageLevel = Level.ZAYIN;
		}
		
		if (!isArmorItemStackEmpty) {
			armorItemStackLaval /= number;
			newDamageAmount *= getDamageMultiple(armorItemStackLaval - damageLevel.getLevelValue());
		} else {
			newDamageAmount *= getDamageMultiple(getEntityLevel(entity), damageLevel);
		}
		
		if (fourColorDamageTypes != null) {
			if (PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get()) {
				if (fourColorDamageConfigImpact(fourColorDamageTypes)) {
					fourColorDamageTypes = ColorType.PHYSICS;
				}
				/// 抗性处理
				newDamageAmount *= (float) entity.getAttributeValue(fourColorDamageTypes.resistance);
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
	public static float getDamageMultiple(@NotNull Level laval, @NotNull Level laval2) {
		return getDamageMultiple(leveDifferenceValue(laval, laval2));
	}
	
	/**
	 * 返回实体或物品之间的等级差值
	 */
	public static int leveDifferenceValue(@NotNull Level level, @NotNull Level level2) {
		return level.getLevelValue() - level2.getLevelValue();
	}
	
	/**
	 * 获取伤害倍数
	 */
	public static float getDamageMultiple(int i) {
		if (i > 4) {
			
			i = 4;
		}
		if (i < -4) {
			i = -4;
		}
		
		return switch (i) {
			case 4 -> 0.4F;
			case 3 -> 0.6F;
			case 2 -> 0.7F;
			case 1 -> 0.8F;
			case 0, -1 -> 1.0F;
			case -2 -> 1.2F;
			case -3 -> 1.5F;
			case -4 -> 2.0F;
			default -> 0.0F;
		};
	}
	
	/** 低抗减慢 */
	public static boolean applySlowdownIfAttributeExceedsOne(ColorType colorType, @NotNull LivingEntity entity) {
		if (colorType == null) {
			return false;
		}
		if (entity.getAttributeValue(colorType.getResistance()) > 1.0) {
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
		handleSpirit(event, entity);
	}
	
	/// 获取颜色伤害类型
	public static PmDamageTool.ColorType getColorDamageType(PmDamageTool.ColorType colorType, Holder<DamageType> type) {
		if (colorType != null) {
			return colorType;
		}
		
		// 从 Holder 中提取 ResourceKey 并查询 Map
		Optional<ResourceKey<DamageType>> keyOptional = type.unwrapKey();
		return keyOptional.map(DAMAGE_TYPE_MAP::get).orElse(null);
	}
	
	/** 四色伤害类型 */
	public enum ColorType {
		/**
		 * 物理
		 */
		PHYSICS(0, "physics", PHYSICS_RESISTANCE, PmColourTool.PHYSICS),
		/**
		 * 精神
		 */
		SPIRIT(1, "spirit", SPIRIT_RESISTANCE, PmColourTool.SPIRIT),
		/**
		 * 侵蚀
		 */
		EROSION(2, "erosion", EROSION_RESISTANCE, PmColourTool.EROSION),
		/**
		 * 灵魂
		 */
		THE_SOUL(3, "the_soul", THE_SOUL_RESISTANCE, PmColourTool.THE_SOUL),
		;
		
		private final int               index;
		private final String            name;
		// 对应的抗性属性
		private final Holder<Attribute> resistance;
		private final PmColourTool      colour;
		
		ColorType(int index, String name, Holder<Attribute> resistance, PmColourTool colour) {
			this.index      = index;
			this.name       = name;
			this.resistance = resistance;
			this.colour     = colour;
		}
		
		public static ColorType is(String name) {
			for (ColorType colorType : ColorType.values()) {
				if (colorType.name.equals(name)) {
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
		
		public Holder<Attribute> getResistance() {
			return resistance;
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
		
		@NotNull
		public static PmDamageTool.Level getEntityLevel(@NotNull Entity entity) {
			ILevel capability = entity.getCapability(LEVEL_ENTITY);
			if (capability == null) {
				return Level.ZAYIN;
			}
			Level level = capability.getItemLevel();
			if (level == null) {
				level = Level.ZAYIN;
			}
			return level;
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
