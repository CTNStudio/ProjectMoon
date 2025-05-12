package ctn.project_moon.api;

import com.mojang.serialization.Codec;
import ctn.project_moon.common.payload.data.FourColorData;
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
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.IntFunction;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PlayerAttribute.*;

/*
 * 四色属性系统
 *
 * 这个类实现了游戏中的四色属性机制，包括勇气（Fortitude）、谨慎（Prudence）、自律（Temperance）和正义（Justice），
 * 每种属性都会影响玩家的不同能力。
 */
public class FourColorAttribute {
	/** 同步四色基础属性 */
	public static void syncFourColorAttribute(Player player){
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
		if (!nbt.contains(BASE_FORTITUDE)) setFortitude(entity, 20);//TODO:无用
		if (!nbt.contains(BASE_PRUDENCE)) setPrudence(entity, 20);//TODO:无用
		if (!nbt.contains(BASE_TEMPERANCE)) setBaseTemperance(entity, 1);
		if (!nbt.contains(BASE_JUSTICE)) setBaseJustice(entity, 1);
	}

	/** 添加四色属性 */
	public static void fourColorDefaultValue(LivingEntity entity) {
		setFortitude(entity, 20);
		setPrudence(entity, 20);
		setBaseTemperance(entity, 1);
		setBaseJustice(entity, 1);
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
		Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.COMPOSITE_RATING)).setBaseValue(getCompositeRatting(entity));
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

	/**
	 * 更新四色属性
	 * <p>
	 * 刷新服务器玩家的所有四色属性加成效果。
	 *
	 * @param player 服务器玩家
	 */
	public static void renewFourColorAttribute(Player player) {
		syncFourColorAttribute(player);
		renewFortitude(player);
		renewPrudence(player);
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
		entity.getPersistentData().putInt(BASE_FORTITUDE, value- (int)entity.getAttributeValue(PmEntityAttributes.FORTITUDE_ADDITIONAL));
	}

	public static void setBaseFortitude(LivingEntity entity, int value) {
		Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(value);
	}

	/**
	 * 勇气加成
	 */
	public static void renewFortitude(Player player) {
		if (player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL) != null) {
			double addMaxHealth = Objects.requireNonNull(player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL)).getValue();
			AttributeModifier addMaxHealthModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fortitude"),
					addMaxHealth, AttributeModifier.Operation.ADD_VALUE);
			Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).addOrUpdateTransientModifier(addMaxHealthModifier);
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

	public static void setPrudence(LivingEntity entity, int value) {
		entity.getPersistentData().putInt(BASE_PRUDENCE, value - (int)entity.getAttributeValue(PmEntityAttributes.PRUDENCE_ADDITIONAL));
	}

	public static void setBasePrudence(LivingEntity entity, int value) {
		Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.MAX_SPIRIT)).setBaseValue(value);
	}

	/**
	 * 谨慎加成
	 */
	public static void renewPrudence(Player player) {
		if (player.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL) != null) {
			double addMaxSpirit = Objects.requireNonNull(player.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL)).getValue();
			AttributeModifier addMaxSpiritModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prudence"),
					addMaxSpirit, AttributeModifier.Operation.ADD_VALUE);
			Objects.requireNonNull(player.getAttribute(PmEntityAttributes.MAX_SPIRIT)).addOrUpdateTransientModifier(addMaxSpiritModifier);
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
	 * 应用自律值带来的挖掘速度加成。
	 *
	 * @param player 服务器玩家
	 */
	public static void renewTemperanceAttribute(Player player) {
		//获取玩家的自律值
		double temperance = getTemperance(player);
		//创建自律加成
		AttributeModifier blockBreakSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "temperance_add_block_break_speed"),
				temperance * 0.02, AttributeModifier.Operation.ADD_VALUE);
		//将自律加成添加到玩家的移动速度属性中
		Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED)).addOrUpdateTransientModifier(blockBreakSpeedModifier);
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
		AttributeModifier movementSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "justice_add_movement_speed"),
				justice * 0.001, AttributeModifier.Operation.ADD_VALUE);
		AttributeModifier attackSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "justice_add_attack_speed"),
				justice * 0.01, AttributeModifier.Operation.ADD_VALUE);
		//将正义加成添加到玩家的移动与攻击速度属性中
		Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addOrUpdateTransientModifier(movementSpeedModifier);
		Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).addOrUpdateTransientModifier(attackSpeedModifier);
	}

	/** 获取基础加附加值的值 */
	private static int getValue(int baseValue, LivingEntity entity, Holder<Attribute> additional) {
		int result = baseValue;
		if (entity.getAttribute(additional) != null) {
			result += (int) Objects.requireNonNull(entity.getAttribute(additional)).getValue();
		}
		return result;
	}

	/// 事件处理方法

	public static void fortitudeRelated(Player player) {
		if (player instanceof ServerPlayer) {
			setFortitude(player,(int) Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue());
		}
	}

	public static void prudenceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			setPrudence(player, (int) SpiritAttribute.getMaxSpiritValue(player));
		}
	}

	public static void temperanceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			if (!player.getPersistentData().contains(BASE_TEMPERANCE))
				setBaseTemperance(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
			if (!Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED))
					.hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "temperance_add_block_break_speed"))) {
				renewTemperanceAttribute(player);
			}
		}
	}

	public static void justiceRelated(Player player) {
		if (player instanceof ServerPlayer) {
			if (!player.getPersistentData().contains(BASE_JUSTICE))
				setBaseJustice(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
			if (!Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED))
					.hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "justice_add_attack_speed")) ||
					!Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED))
							.hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "justice_add_movement_speed"))) {
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

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
		private static final IntFunction<Type> BY_ID = ByIdMap.continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, type -> type.id);
		private final String idName;
		private final String name;
		private final int id;

		Type(int id, String idName, String name) {
			this.idName = idName;
			this.id = id;
			this.name = name;
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
		private final int rating;
		private final int minValue;

		Rating(String idName, String name, int rating, int minValue) {
			this.idName = idName;
			this.name = name;
			this.rating = rating;
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
}
