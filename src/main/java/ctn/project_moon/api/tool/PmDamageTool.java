package ctn.project_moon.api.tool;

import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.datagen.PmTags;
import ctn.project_moon.event.DourColorDamageTypesEvent;
import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.mixin_extend.PmDamageSourceMixin;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ctn.project_moon.PmMain.LOGGER;
import static ctn.project_moon.api.SpiritAttribute.handleRationally;
import static ctn.project_moon.api.tool.PmDamageTool.Level.getEntityLevel;
import static ctn.project_moon.common.item.Ego.DAMAGE_TYPE;
import static ctn.project_moon.common.item.Ego.getItemLevelValue;
import static ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon.isCloseCombatEgo;
import static ctn.project_moon.init.PmCommonHooks.dourColorDamageType;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;
import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

/**
 * ProjectMoon伤害系统工具
 *
 * @author 尽
 */
public class PmDamageTool {
	/** 获取四色伤害类型ID */
	public static String getDamageTypeId(ItemStack itemStack, DamageSource damageSource) {
		return switch (FourColorType.getFourColorDamageType(itemStack, damageSource)) {
			case PHYSICS -> FourColorType.PHYSICS.getLocationString();
			case SPIRIT -> FourColorType.SPIRIT.getLocationString();
			case EROSION -> FourColorType.EROSION.getLocationString();
			case THE_SOUL -> FourColorType.THE_SOUL.getLocationString();
			case null -> damageSource.type().msgId();
		};
	}

	/** 获取四色伤害类型ID */
	public static String getDamageTypeId(DamageSource damageSource) {
		return getDamageTypeId(getDamageItemStack(damageSource), damageSource);
	}

	/** 获取伤害物品 */
	public static @Nullable ItemStack getDamageItemStack(DamageSource damageSource) {
		ItemStack itemStack = damageSource.getWeaponItem();
		if (itemStack == null && damageSource.getEntity() != null) {
			itemStack = damageSource.getEntity().getWeaponItem();
		}
		return itemStack;
	}

	/** 伤害计算 */
	public static void resistanceTreatment(LivingIncomingDamageEvent event, Level level, FourColorType fourColorDamageTypes) {
		float newDamageAmount = event.getAmount();
		int armorItemStackLaval = 0; // 盔甲等级
		int number = 0;
		boolean isArmorItemStackEmpty = true;
		var flag = !(event.getEntity() instanceof Abnos);
		var itor = event.getEntity().getArmorAndBodyArmorSlots().iterator();
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
		if (!isArmorItemStackEmpty) {
			armorItemStackLaval /= number;
			newDamageAmount *= getDamageMultiple(armorItemStackLaval - level.getLevelValue());
		} else {
			Level entityLaval = getEntityLevel(event.getEntity());
			newDamageAmount *= getDamageMultiple(entityLaval, level);
		}

		if (fourColorDamageTypes != null && (PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get())) {
			if (fourColorDamageConfigImpact(fourColorDamageTypes)) {
				fourColorDamageTypes = FourColorType.PHYSICS;
			}

			/// 抗性处理
			newDamageAmount *= (float) switch (fourColorDamageTypes) {
				case PHYSICS -> event.getEntity().getAttributeValue(PHYSICS_RESISTANCE);
				case SPIRIT -> event.getEntity().getAttributeValue(SPIRIT_RESISTANCE);
				case EROSION -> event.getEntity().getAttributeValue(EROSION_RESISTANCE);
				case THE_SOUL -> event.getEntity().getAttributeValue(THE_SOUL_RESISTANCE);
			};
		}

		event.setAmount(newDamageAmount);
	}

	/** 四色伤害配置影响 */
	private static boolean fourColorDamageConfigImpact(FourColorType fourColorDamageTypes) {
		if (!PmConfig.SERVER.ENABLE_THE_SOUL_DAMAGE.get() && fourColorDamageTypes.equals(FourColorType.THE_SOUL)) {
			return true;
		}
		if (!(PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() || PmConfig.COMMON.ENABLE_RATIONALITY.get() && fourColorDamageTypes.equals(FourColorType.SPIRIT))) {
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
			case 0 -> 1.0F;
			case -1 -> 1.0F;
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
	public enum FourColorType {
		/**
		 * 物理
		 */
		PHYSICS(
				PmDamageTypes.PHYSICS,
				PmTags.PmItem.PHYSICS,
				PmTags.PmDamageType.PHYSICS),
		/**
		 * 精神
		 */
		SPIRIT(
				PmDamageTypes.SPIRIT,
				PmTags.PmItem.SPIRIT,
				PmTags.PmDamageType.SPIRIT),
		/**
		 * 侵蚀
		 */
		EROSION(
				PmDamageTypes.EROSION,
				PmTags.PmItem.EROSION,
				PmTags.PmDamageType.EROSION),
		/**
		 * 灵魂
		 */
		THE_SOUL(
				PmDamageTypes.THE_SOUL,
				PmTags.PmItem.THE_SOUL,
				PmTags.PmDamageType.THE_SOUL),
		;

		private final TagKey<Item>            itemTga;
		private final TagKey<DamageType>      damageTypeTag;
		private final ResourceKey<DamageType> key;
		private final String                  location;

		FourColorType(ResourceKey<DamageType> key, TagKey<Item> itemTga, TagKey<DamageType> damageTypeTag) {
			this.key           = key;
			this.location      = key.location().toString();
			this.itemTga       = itemTga;
			this.damageTypeTag = damageTypeTag;
		}

		/** 获取四色伤害类型 */
		public static @Nullable PmDamageTool.FourColorType getFourColorDamageType(ItemStack attackItemStack, DamageSource damageSource) {
			FourColorType fourColorDamageTypes;
			if (isCloseCombatEgo(attackItemStack)) {
				fourColorDamageTypes = getType(attackItemStack.get(CURRENT_DAMAGE_TYPE));
			} else {
				DourColorDamageTypesEvent dourColorEvents = dourColorDamageType(damageSource);
				if (dourColorEvents.getDamageTypes() != null) {
					fourColorDamageTypes = dourColorEvents.getDamageTypes();
				} else {
					if (damageSource.is(PmTags.PmDamageType.PHYSICS)) {
						fourColorDamageTypes = FourColorType.PHYSICS;
					} else {
						fourColorDamageTypes = getType(damageSource);
					}
				}
			}
			return fourColorDamageTypes;
		}

		/** 获取四色伤害类型 */
		public static @Nullable PmDamageTool.FourColorType getFourColorDamageType(DamageSource damageSource) {
			return getFourColorDamageType(getDamageItemStack(damageSource), damageSource);
		}

		/** 获取四色伤害类型 */
		public static @Nullable PmDamageTool.FourColorType getFourColorDamageTypeDefault(DamageSource damageSource, ItemStack itemStack) {
			FourColorType types;
			if (damageSource instanceof PmDamageSourceMixin pmDamageSource) {
				types = pmDamageSource.getFourColorDamageTypes();
			} else {// 以防万一
				types = getFourColorDamageType(itemStack, damageSource);
			}
			return types;
		}

		/** 获取四色伤害类型 */
		public static @Nullable PmDamageTool.FourColorType getFourColorDamageTypeDefault(DamageSource damageSource){
			return getFourColorDamageTypeDefault(damageSource, getDamageItemStack(damageSource));
		}

		public static FourColorType getType(final DamageSource damageSource) {
			return Arrays.stream(FourColorType.values())
					.filter(it -> damageSource.is(it.getDamageTypeTag()))
					.findFirst()
					.orElse(null);

		}

		@CheckForNull
		public static FourColorType getType(final ResourceKey<DamageType> key) {
			return Arrays.stream(FourColorType.values())
					.filter(it -> key.equals(it.getKey()))
					.findFirst()
					.orElse(null);
		}

		@CheckForNull
		public static FourColorType getType(final String keyString) {
			return Arrays.stream(FourColorType.values())
					.filter(it -> keyString.equals(it.getLocationString()))
					.findFirst()
					.orElse(null);
		}

		/**
		 * 返回EGO伤害类型 仅物品描述用
		 */
		public static List<TagKey<Item>> egoDamageTypes(final ItemStack item) {
			return item.getTags().filter(DAMAGE_TYPE::contains).toList();
		}

		public ResourceKey<DamageType> getKey() {
			return key;
		}

		public String getLocationString() {
			return location;
		}

		public TagKey<DamageType> getDamageTypeTag() {
			return damageTypeTag;
		}

		public TagKey<Item> getItemTga() {
			return itemTga;
		}
	}

	/** 四色伤害等级 */
	public enum Level {
		ZAYIN("ZAYIN", 1, PmTags.PmItem.ZAYIN, PmTags.PmBlock.ZAYIN, PmColourTool.ZAYIN),
		TETH("TETH", 2, PmTags.PmItem.TETH, PmTags.PmBlock.TETH, PmColourTool.TETH),
		HE("HE", 3, PmTags.PmItem.HE, PmTags.PmBlock.HE, PmColourTool.HE),
		WAW("WAW", 4, PmTags.PmItem.WAW, PmTags.PmBlock.WAW, PmColourTool.WAW),
		ALEPH("ALEPH", 5, PmTags.PmItem.ALEPH, PmTags.PmBlock.ALEPH, PmColourTool.ALEPH);

		private final String        name;
		private final int           levelValue;
		private final TagKey<Item>  itemTag;
		private final TagKey<Block> blockTag;
		private final PmColourTool  colour;

		Level(String name, int levelValue, TagKey<Item> itemTag, TagKey<Block> blockTag, PmColourTool colour) {
			this.name       = name;
			this.levelValue = levelValue;
			this.itemTag    = itemTag;
			this.blockTag   = blockTag;
			this.colour     = colour;
		}

		/** 获取伤害来源等级 */
		public static @Nullable Level getDamageLevel(DamageSource damageSource, ItemStack itemStack) {
			Level level = ZAYIN;
			if (itemStack != null && !itemStack.isEmpty()) {
				level = getItemLevel(getEgoLevelTag(itemStack));
			} else {
				Entity directEntity = damageSource.getDirectEntity();
				Entity entity = damageSource.getEntity();
				if (entity instanceof LivingEntity livingEntity) {
					level = getEntityLevel(livingEntity);
				} else if (directEntity instanceof LivingEntity livingEntity) {
					level = getEntityLevel(livingEntity);
				}
			}
			return level;
		}

		/** 获取伤害来源等级 */
		public static @Nullable Level getDamageLevel(DamageSource damageSource) {
			return getDamageLevel(damageSource, getDamageItemStack(damageSource));
		}

		/** 获取伤害来源等级 */
		public static @Nullable Level getDamageLevelDefault(DamageSource damageSource, ItemStack itemStack) {
			Level types;
			if (damageSource instanceof PmDamageSourceMixin pmDamageSource) {
				types = pmDamageSource.getDamageLevel();
			} else {// 以防万一
				types = getDamageLevel(damageSource, itemStack);
			}
			return types;
		}

		/** 获取伤害来源等级 */
		public static @Nullable Level getDamageLevelDefault(DamageSource damageSource) {
			return getDamageLevelDefault(damageSource, getDamageItemStack(damageSource));
		}

		/**
		 * 返回EGO等级tga
		 */
		public static TagKey<Item> getEgoLevelTag(ItemStack item) {
			return item.getTags()
					.filter(it -> Objects.nonNull(Level.getItemLevel(it)))
					.findFirst()
					.orElse(ZAYIN.getItemLevel());
		}

		public static TagKey<Block> getEgoLevelTag(BlockState block) {
			return block.getTags()
					.filter(it -> Objects.nonNull(Level.getBlockLevel(it)))
					.findFirst()
					.orElse(ZAYIN.getBlockLevel());
		}

		@CheckForNull
		public static Level getItemLevel(TagKey<Item> tag) {
			return Arrays.stream(Level.values())
					.sorted((a, b) -> Integer.compare(b.getLevelValue(), a.getLevelValue()))
					.filter(it -> tag.equals(it.getItemLevel()))
					.findFirst()
					.orElse(null);
		}

		@CheckForNull
		public static Level getBlockLevel(TagKey<Block> tag) {
			return Arrays.stream(Level.values())
					.sorted((a, b) -> Integer.compare(b.getLevelValue(), a.getLevelValue()))
					.filter(it -> tag.equals(it.getBlockLevel()))
					.findFirst()
					.orElse(null);
		}

		public static Level getEntityLevel(LivingEntity entity) {
			return Arrays.stream(Level.values())
					.sorted((a, b) -> Integer.compare(b.getLevelValue(), a.getLevelValue()))
					.filter(it -> (int) entity.getAttributeValue(ENTITY_LEVEL) == it.getLevelValue())
					.findFirst()
					.orElse(ZAYIN);
		}

		public String getName() {
			return name;
		}

		public int getLevelValue() {
			return levelValue;
		}

		public TagKey<Item> getItemLevel() {
			return itemTag;
		}

		public TagKey<Block> getBlockLevel() {
			return blockTag;
		}

		public PmColourTool getColour() {
			return colour;
		}

		public String getColourText() {
			return colour.getColour();
		}
	}
}
