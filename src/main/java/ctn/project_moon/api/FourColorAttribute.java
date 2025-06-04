package ctn.project_moon.api;

import com.mojang.serialization.Codec;
import ctn.project_moon.common.payload.data.FourColorData;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.PmEntityAttributes;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.IntFunction;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PlayerAttribute.*;

/**
 * 四色属性系统
 * <p>
 * 属性机制，包括勇气（Fortitude）、谨慎（Prudence）、自律（Temperance）和正义（Justice），
 * 每种属性都会影响玩家的不同能力。
 */
public class FourColorAttribute {
	// 仅做原始标记
	private static final int    PRUDENCE_INITIAL_VALUE       = 20;
	private static final int    FORTITUDE_INITIAL_VALUE      = 20;
	private static final int    TEMPERANCE_INITIAL_VALUE     = 1;
	private static final int    JUSTICE_INITIAL_VALUE        = 1;
	private static final float  VANILLA_FLYING_SPEED         = 0.05f;
	private static final double TEMPERANCE_BLOCK_BREAK_SPEED = 0.02;
	private static final double TEMPERANCE_KNOCKBACK_SPEED   = 0.015;
	private static final double JUSTICE_MOVEMENT_SPEED       = 0.001;
	private static final double JUSTICE_ATTACK_SPEED         = 0.01;
	private static final double JUSTICE_SWIM_SPEED           = 0.01;
	private static final double JUSTICE_FLIGHT_SPEED         = 0.00013;

	public static final String TEMPERANCE_ADD_KNOCKBACK         = "temperance_add_knockback";
	public static final String TEMPERANCE_ADD_BLOCK_BREAK_SPEED = "temperance_add_block_break_speed";
	public static final String JUSTICE_ADD_MOVEMENT_SPEED       = "justice_add_movement_speed";
	public static final String JUSTICE_ADD_ATTACK_SPEED         = "justice_add_attack_speed";
	public static final String JUSTICE_ADD_SWIM_SPEED           = "justice_add_swim_speed";
	public static final String PRUDENCE_ADD_MAX_HEALTH          = "prudence_add_max_health";
	public static final String PRUDENCE_ADD_MAX_SPIRIT          = "prudence_add_max_spirit";
	public static final String FORTITUDE_ADD_MAX_HEALTH         = "fortitude_add_max_health";


	/**
	 * 更新四色属性
	 * <p>
	 * 当玩家重生，进入世界（更换维度），更改四色属性调用
	 * TODO 补充
	 */
	public static void updateColorAttributes(Player player) {
		if (! (player instanceof ServerPlayer serverPlayer)) {
			return;
		}
		// 重新计算四色属性 例如限制 TODO 填写你的限制处理


		// 更新加成
		renewFourColorAttribute(serverPlayer);

		// 同步属性
		syncFourColorAttribute(serverPlayer);
	}

	/** 同步四色基础属性 */
	public static void syncFourColorAttribute(Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			PacketDistributor.sendToPlayer(serverPlayer, new FourColorData(
					serverPlayer.getPersistentData().getInt(BASE_FORTITUDE),
					serverPlayer.getPersistentData().getInt(BASE_PRUDENCE),
					serverPlayer.getPersistentData().getInt(BASE_TEMPERANCE),
					serverPlayer.getPersistentData().getInt(BASE_JUSTICE)
			));
		}
	}

	/**
	 * 添加四色属性到实体上
	 * <p>
	 * 如果实体不具有某些基础属性，则设置默认值。
	 *
	 * @param entity 生物实体
	 */
	public static void addFourColorAttribute(LivingEntity entity) {
		CompoundTag nbt = entity.getPersistentData();
		if (! nbt.contains(BASE_FORTITUDE)) setFortitude(entity, PmConfig.SERVER.FORTITUDE_INITIAL_VALUE.get());//TODO:无用
		if (! nbt.contains(BASE_PRUDENCE)) setBasePrudence(entity, PmConfig.SERVER.PRUDENCE_INITIAL_VALUE.get());//TODO:无用
		if (! nbt.contains(BASE_TEMPERANCE)) setBaseTemperance(entity, PmConfig.SERVER.TEMPERANCE_INITIAL_VALUE.get());
		if (! nbt.contains(BASE_JUSTICE)) setBaseJustice(entity, PmConfig.SERVER.JUSTICE_INITIAL_VALUE.get());
	}

	/** 添加四色属性 */
	public static void fourColorDefaultValue(LivingEntity entity) {
		setFortitude(entity, PmConfig.SERVER.FORTITUDE_INITIAL_VALUE.get());
		setBasePrudence(entity, PmConfig.SERVER.PRUDENCE_INITIAL_VALUE.get());
		setBaseTemperance(entity, PmConfig.SERVER.TEMPERANCE_INITIAL_VALUE.get());
		setBaseJustice(entity, PmConfig.SERVER.JUSTICE_INITIAL_VALUE.get());
	}

	/**
	 * 获取综合评级
	 * <p>
	 * 计算并返回四个基本属性评级之和的综合评价。
	 *
	 * @param entity 生物实体
	 * @return 综合评级
	 */
	public static int getCompositeRatting(LivingEntity entity) {
		int rating;
		rating = (getFortitudeRating(entity) + getPrudenceRating(entity) + getTemperanceRating(entity) + getJusticeRating(entity));
		if (rating >= 16) {
			return 5;
		}
		return Mth.clamp(rating / 3, 1, 4);
	}

	/**
	 * 更新玩家的综合评级
	 * <p>
	 * 根据当前属性值更新玩家的综合评级。
	 *
	 * @param entity 生物实体
	 */
	public static void renewPlayerCompositeRatting(LivingEntity entity) {
		entity.getAttribute(PmEntityAttributes.COMPOSITE_RATING).setBaseValue(getCompositeRatting(entity));
	}

	/**
	 * 更新四色属性
	 * <p>
	 * 刷新服务器玩家的所有四色属性加成效果。
	 *
	 * @param player 服务器玩家
	 */
	public static void renewFourColorAttribute(Player player) {
		syncFourColorAttribute(player);
		renewFortitudeAttribute(player);
		renewPrudenceAttribute(player);
		renewTemperanceAttribute(player);
		renewJusticeAttribute(player);
	}

	/** 获取基础勇气值 */
	public static int getBaseFortitude(LivingEntity entity) {
		return entity.getPersistentData().getInt(BASE_FORTITUDE);
	}

	/** 获取勇气值 */
	public static int getFortitude(LivingEntity entity) {
		return getValue(getBaseFortitude(entity), entity, PmEntityAttributes.FORTITUDE_ADDITIONAL);
	}

	/// 勇气

	/** 获取勇气评级 */
	public static int getFortitudeRating(LivingEntity entity) {
		return getColorAttributeRating(getFortitude(entity));
	}

	public static void setFortitude(LivingEntity entity, int value) {
		entity.getPersistentData().putInt(BASE_FORTITUDE, value - (int) entity.getAttributeValue(PmEntityAttributes.FORTITUDE_ADDITIONAL));
	}

	public static void setBaseFortitude(LivingEntity entity, int value) {
		entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(value);
	}

	/**
	 * 勇气加成
	 */
	public static void renewFortitudeAttribute(Player player) {
		if (player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL) != null) {
			double addMaxHealth = player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL).getValue();
			AttributeModifier addMaxHealthModifier = newAttributeModifierAddValue(FORTITUDE_ADD_MAX_HEALTH, addMaxHealth);
			addOrUpdateTransientModifier(player, Attributes.MAX_HEALTH, addMaxHealthModifier);
		}
	}

	/** 获取基础谨慎值 */
	public static int getBasePrudence(LivingEntity entity) {
		return (int) entity.getPersistentData().getDouble(BASE_PRUDENCE);
	}

	/** 获取谨慎值 */
	public static int getPrudence(LivingEntity entity) {
		return getValue(getBasePrudence(entity), entity, PmEntityAttributes.PRUDENCE_ADDITIONAL);
	}

	/// 谨慎

	/** 获取谨慎评级 */
	public static int getPrudenceRating(LivingEntity entity) {
		return getColorAttributeRating(getPrudence(entity));
	}

	public static void setBasePrudence(LivingEntity entity, int value) {
		entity.getPersistentData().putInt(BASE_PRUDENCE, value);
		if (entity instanceof ServerPlayer player) {
			renewPrudenceAttribute(player);
		}
	}

	/**
	 * 谨慎加成
	 */
	public static void renewPrudenceAttribute(Player player) {
		if (player.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL) != null) {
			double addMaxSpirit = getPrudence(player) - PmConfig.SERVER.PRUDENCE_INITIAL_VALUE.get();//减去初始值20
			AttributeModifier addMaxSpiritModifier = newAttributeModifierAddValue(PRUDENCE_ADD_MAX_SPIRIT, addMaxSpirit);
			addOrUpdateTransientModifier(player, PmEntityAttributes.MAX_SPIRIT, addMaxSpiritModifier);
		}
	}

	/** 获取基础自律值 */
	public static int getBaseTemperance(LivingEntity entity) {
		return (int) entity.getPersistentData().getDouble(BASE_TEMPERANCE);
	}

	/** 获取自律值 */
	public static int getTemperance(LivingEntity entity) {
		return getValue(getBaseTemperance(entity), entity, PmEntityAttributes.TEMPERANCE_ADDITIONAL);
	}

	//TODO:自律相关（目前只有加挖掘速度)

	/// 自律

	/** 获取自律评级 */
	public static int getTemperanceRating(LivingEntity entity) {
		return getColorAttributeRating(getTemperance(entity));
	}

	/** 设置基础自律值 */
	public static void setBaseTemperance(LivingEntity entity, int value) {
		entity.getPersistentData().putInt(BASE_TEMPERANCE, value);
		if (entity instanceof ServerPlayer player) {
			renewTemperanceAttribute(player);
		}
	}

	/** 增加基础自律值 */
	public static void addBaseTemperance(LivingEntity entity, int addValue) {
		setBaseTemperance(entity, getBaseTemperance(entity) + addValue);
	}

	/**
	 * 更新自律对玩家属性的加成(附加值更改时也记得调用)
	 * <p>
	 * 应用自律值带来的挖掘速度加成与击退。
	 *
	 * @param player 服务器玩家
	 */
	public static void renewTemperanceAttribute(Player player) {
		//获取玩家的自律值
		int temperance = getTemperance(player);
		//创建自律加成
		AttributeModifier blockBreakSpeedModifier = newAttributeModifierAddValue(TEMPERANCE_ADD_BLOCK_BREAK_SPEED, temperance * PmConfig.SERVER.TEMPERANCE_BLOCK_BREAK_SPEED.get());
		AttributeModifier knockbackModifier = newAttributeModifierAddValue(TEMPERANCE_ADD_KNOCKBACK, temperance * PmConfig.SERVER.TEMPERANCE_KNOCKBACK_SPEED.get());
		//将自律加成添加到玩家的挖掘速度与击退属性中
		addOrUpdateTransientModifier(player, Attributes.BLOCK_BREAK_SPEED, blockBreakSpeedModifier);
		addOrUpdateTransientModifier(player, Attributes.ATTACK_KNOCKBACK, knockbackModifier);
	}


	/** 获取基础正义值 */
	public static int getBaseJustice(LivingEntity entity) {
		return (int) entity.getPersistentData().getDouble(BASE_JUSTICE);
	}

	/** 获取正义值 */
	public static int getJustice(LivingEntity entity) {
		return getValue(getBaseJustice(entity), entity, PmEntityAttributes.JUSTICE_ADDITIONAL);
	}

	/// 正义

	/** 获取正义评级 */
	public static int getJusticeRating(LivingEntity entity) {
		return getColorAttributeRating(getJustice(entity));
	}

	/** 设置基础正义值 */
	public static void setBaseJustice(LivingEntity entity, int value) {
		entity.getPersistentData().putInt(BASE_JUSTICE, value);
		if (entity instanceof ServerPlayer player) {
			renewJusticeAttribute(player);
		}
	}

	/** 增加基础正义值 */
	public static void addBaseJustice(LivingEntity entity, int addValue) {
		setBaseJustice(entity, getBaseJustice(entity) + addValue);
	}

	/**
	 * 更新正义对玩家属性的加成(附加值更改时也记得调用)
	 * <p>
	 * 应用正义值带来的移动速度和攻击速度加成。
	 *
	 * @param player 服务器玩家
	 */
	public static void renewJusticeAttribute(Player player) {
		//获取玩家的正义值
		double justice = getJustice(player);
		//创建正义加成
		AttributeModifier movementSpeedModifier = newAttributeModifierAddValue(JUSTICE_ADD_MOVEMENT_SPEED, justice * PmConfig.SERVER.JUSTICE_MOVEMENT_SPEED.get());
		AttributeModifier attackSpeedModifier = newAttributeModifierAddValue(JUSTICE_ADD_ATTACK_SPEED, justice * PmConfig.SERVER.JUSTICE_ATTACK_SPEED.get());
		AttributeModifier swimSpeedModifier = newAttributeModifierAddValue(JUSTICE_ADD_SWIM_SPEED, justice * PmConfig.SERVER.JUSTICE_SWIM_SPEED.get());
		//将正义加成添加到玩家的移动与攻击速度属性中
		addOrUpdateTransientModifier(player, Attributes.MOVEMENT_SPEED, movementSpeedModifier);
		addOrUpdateTransientModifier(player, Attributes.ATTACK_SPEED, attackSpeedModifier);
		addOrUpdateTransientModifier(player, NeoForgeMod.SWIM_SPEED, swimSpeedModifier);

		//飞行速度
		player.getAbilities().setFlyingSpeed((float) (PmConfig.SERVER.VANILLA_FLYING_SPEED.get() + (float) (justice * PmConfig.SERVER.JUSTICE_FLIGHT_SPEED.get())));
	}

	/// 事件处理方法

	/**
	 * 使玩家勇气基础值与最大生命值-附加值相同。
	 * @param player
	 */
	public static void fortitudeRelated(Player player) {
		if (player instanceof ServerPlayer) {
			setFortitude(player, (int) player.getAttribute(Attributes.MAX_HEALTH).getValue());
		}
	}

	/**
	 * load时调用谨慎更新
	 * @param player
	 */
	public static void prudenceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			if (! player.getPersistentData().contains(BASE_PRUDENCE)) {
				setBasePrudence(player, PmConfig.SERVER.PRUDENCE_INITIAL_VALUE.get());//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
			}
			if (! hasModifier(player, Attributes.MAX_HEALTH, PRUDENCE_ADD_MAX_HEALTH)) {
				renewPrudenceAttribute(player);
			}
		}
	}

	/**
	 * load时调用自律更新
	 * @param player
	 */
	public static void temperanceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			if (! player.getPersistentData().contains(BASE_TEMPERANCE)) {
				setBaseTemperance(player, PmConfig.SERVER.TEMPERANCE_INITIAL_VALUE.get());//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
			}
			if (! hasModifier(player, Attributes.BLOCK_BREAK_SPEED, TEMPERANCE_ADD_BLOCK_BREAK_SPEED) ||
			    ! hasModifier(player, Attributes.ATTACK_KNOCKBACK, TEMPERANCE_ADD_KNOCKBACK)) {
				renewTemperanceAttribute(player);
			}
		}
	}

	/**
	 * load时调用正义更新
	 * @param player
	 */
	public static void justiceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			if (! player.getPersistentData().contains(BASE_JUSTICE)) {
				setBaseJustice(player, PmConfig.SERVER.JUSTICE_INITIAL_VALUE.get());//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
			}
			if (! hasModifier(player, Attributes.ATTACK_SPEED, JUSTICE_ADD_ATTACK_SPEED) ||
			    ! hasModifier(player, Attributes.MOVEMENT_SPEED, JUSTICE_ADD_MOVEMENT_SPEED) ||
			    ! hasModifier(player, NeoForgeMod.SWIM_SPEED, JUSTICE_ADD_SWIM_SPEED)) {
				renewJusticeAttribute(player);
			}
		}
	}

	/** 四色属性类型枚举 */
	public enum Type implements StringRepresentable {
		/** 勇气 - 影响最大生命值 */
		FORTITUDE(0, PREFIX + "fortitude", "fortitude"),
		/** 谨慎 - 影响最大精神 */
		PRUDENCE(1, PREFIX + "prudence", "prudence"),
		/** 自律 - 影响挖掘速度 TODO 补充 */
		TEMPERANCE(2, PREFIX + "temperance", "temperance"),
		/** 正义 - 影响移动速度和攻击速度 */
		JUSTICE(3, PREFIX + "justice", "justice"),
		/** 综合评级 - 衡量所有属性的整体表现 */
		COMPOSITE_RATING(4, PREFIX + "composite_rating", "composite_rating");

		public static final  Codec<Type>                CODEC        = StringRepresentable.fromEnum(Type::values);
		private static final IntFunction<Type>          BY_ID        = ByIdMap.continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final  StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, type -> type.id);
		private final        String                     idName;
		private final        String                     name;
		private final        int                        id;

		Type(int id, String idName, String name) {
			this.idName = idName;
			this.id     = id;
			this.name   = name;
		}

		@Override
		public @NotNull String getSerializedName() {
			return idName;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 评级枚举
	 * <p>
	 * 定义了每种属性可能达到的不同等级，从I到EX，
	 * 每个等级都有对应的最小数值阈值。
	 */
	public enum Rating {
		EX(PREFIX + "ex", "ex", 6, 101),
		V(PREFIX + "five", "five", 5, 85),
		IV(PREFIX + "four", "four", 4, 65),
		III(PREFIX + "three", "three", 3, 45),
		II(PREFIX + "two", "two", 2, 30),
		I(PREFIX + "one", "one", 1, 1),
		;

		private final String idName;
		private final String name;
		private final int    rating;
		private final int    minValue;

		Rating(String idName, String name, int rating, int minValue) {
			this.idName   = idName;
			this.name     = name;
			this.rating   = rating;
			this.minValue = minValue;
		}

		/**
		 * 获取给定数值对应的评级
		 *
		 * @param value 数值
		 * @return 对应的评级
		 */
		public static Rating getRating(int value) {
			Rating[] values = Rating.values();
			for (Rating rating : values) {
				if (value >= rating.minValue) {
					return rating;
				}
			}
			return I;
		}

		public int getRating() {
			return rating;
		}

		public String getIdName() {
			return idName;
		}

		public int getMinValue() {
			return minValue;
		}

		public String getName() {
			return name;
		}
	}

	private static @NotNull ResourceLocation getResourceLocation(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	/** 添加或更新临时属性 */
	private static void addOrUpdateTransientModifier(Player player, Holder<Attribute> attribute, AttributeModifier movementSpeedModifier) {
		Objects.requireNonNull(player.getAttribute(attribute)).addOrUpdateTransientModifier(movementSpeedModifier);
	}

	/** 获取基础加附加值的值 */
	private static int getValue(int baseValue, LivingEntity entity, Holder<Attribute> additional) {
		int result = baseValue;
		if (entity.getAttribute(additional) != null) {
			result += (int) Objects.requireNonNull(entity.getAttribute(additional)).getValue();
		}
		return result;
	}

	/**
	 * 评级计算方法
	 * <p>
	 * 根据属性值计算相应的评级。
	 *
	 * @param value 属性值
	 * @return 评级
	 */
	private static int getColorAttributeRating(int value) {
		if (value < 30) {
			return 1;
		}
		if (value < 45) {
			return 2;
		}
		if (value < 65) {
			return 3;
		}
		if (value < 85) {
			return 4;
		}
		if (value <= 100) {
			return 5;
		}
		return 6;
	}

	/** 检查玩家是否有指定属性的指定附加值 */
	private static boolean hasModifier(Player player, Holder<Attribute> attribute, String name) {
		return Objects.requireNonNull(player.getAttribute(attribute)).hasModifier(getResourceLocation(name));
	}

	private static @NotNull AttributeModifier newAttributeModifierAddValue(String name, double value) {
		return new AttributeModifier(getResourceLocation(name), value, AttributeModifier.Operation.ADD_VALUE);
	}
}
