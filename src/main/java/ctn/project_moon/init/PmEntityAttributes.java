package ctn.project_moon.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.GradeTypeTool.Level.ALEPH;
import static ctn.project_moon.tool.GradeTypeTool.Level.ZAYIN;

public class PmEntityAttributes {
	public static final DeferredRegister<Attribute> PM_ATTRIBUTE_REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, MOD_ID);
	/// 伤害抵抗能力 越大越受到的伤害越高 1.0是不做出任何改变
	/** 物理伤害抵抗能力 */
	public static final Holder<Attribute> PHYSICS_RESISTANCE = registerRangedAttribute("generic.physics_resistance", "attribute.name.generic.physics_resistance", 1.0, -1024, 1024);
	/** 精神伤害抵抗能力 */
	public static final Holder<Attribute> SPIRIT_RESISTANCE = registerRangedAttribute("generic.spirit_resistance", "attribute.name.generic.spirit_resistance", 1.0, -1024, 1024);
	/** 侵蚀伤害抵抗能力 */
	public static final Holder<Attribute> EROSION_RESISTANCE = registerRangedAttribute("generic.erosion_resistance", "attribute.name.generic.erosion_resistance", 1.5, -1024, 1024);
	/** 灵魂伤害抵抗能力 */
	public static final Holder<Attribute> THE_SOUL_RESISTANCE = registerRangedAttribute("generic.the_soul_resistance", "attribute.name.generic.the_soul_resistance", 2.0, -1024, 1024);

	/** 实体等级 1至5级 */
	public static final Holder<Attribute> ENTITY_LEVEL = registerRangedAttribute("generic.entity_level", "attribute.name.generic.entity_level", ZAYIN.getLevelValue(), ZAYIN.getLevelValue(), ALEPH.getLevelValue());
	/** 最大理智值 */
	public static final Holder<Attribute> MAX_SPIRIT = registerRangedAttribute("generic.max_spirit", "attribute.name.generic.max_spirit", 20, 0, 4096);
	/** 理智值自然恢复效率 */
	public static final Holder<Attribute> SPIRIT_NATURAL_RECOVERY_RATE = registerRangedAttribute("generic.spirit_natural_recovery_rate", "attribute.name.generic.spirit_natural_recovery_rate", 0.5, 0, 4096);
	/** 理智值自然恢复量 */
	public static final Holder<Attribute> SPIRIT_RECOVERY_AMOUNT = registerRangedAttribute("generic.spirit_recovery_amount", "attribute.name.generic.spirit_recovery_amount", 1, 0, 4096);

	/// 四色属性能力
	/**
	 * 勇气最大点数
	 */
	public static final Holder<Attribute> MAX_FORTITUDE = registerRangedAttribute("player.max_fortitude", "attribute.name.player.max_fortitude", 100, 0, 4096);
	/**
	 * 谨慎最大点数
	 */
	public static final Holder<Attribute> MAX_PRUDENCE = registerRangedAttribute("player.max_prudence", "attribute.name.player.max_prudence", 100, 0, 4096);
	/**
	 * 自律最大点数
	 */
	public static final Holder<Attribute> MAX_TEMPERANCE = registerRangedAttribute("player.max_temperance", "attribute.name.player.max_temperance", 100, 0, 4096);
	/**
	 * 正义最大点数
	 */
	public static final Holder<Attribute> MAX_JUSTICE = registerRangedAttribute("player.max_justice", "attribute.name.player.max_justice", 100, 0, 4096);
	/** 四色属性综合评级 */
	public static final Holder<Attribute> COMPOSITE_RATING = registerRangedAttribute("player.composite_rating", "attribute.name.player.composite_rating", 1, 1, 5);
	/** 四色属性附加值(用于饰品加成等) */
	public static final Holder<Attribute> FORTITUDE_ADDITIONAL = registerRangedAttribute("player.fortitude_additional", "attribute.name.player.fortitude_additional", 0, -1024, 1024);
	public static final Holder<Attribute> PRUDENCE_ADDITIONAL = registerRangedAttribute("player.prudence_additional", "attribute.name.player.prudence_additional", 0, -1024, 1024);
	public static final Holder<Attribute> TEMPERANCE_ADDITIONAL = registerRangedAttribute("player.temperance_additional", "attribute.name.player.temperance_additional", 0, -1024, 1024);
	public static final Holder<Attribute> JUSTICE_ADDITIONAL = registerRangedAttribute("player.justice_additional", "attribute.name.player.justice_additional", 0, -1024, 1024);

	/**
	 * 情报部门激活
	 */
	public static final Holder<Attribute> ID_ACT = registerBooleanAttribute("player.intelligence_department_activation", "attribute.name.player.intelligence_department_activation", false);


	public static Holder<Attribute> registerRangedAttribute(String name, String descriptionId, double defaultValue, double min, double max) {
		return PM_ATTRIBUTE_REGISTER.register(name, () -> new RangedAttribute(MOD_ID + "." + descriptionId, defaultValue, min, max).setSyncable(true));
	}

	public static Holder<Attribute> registerBooleanAttribute(String name, String descriptionId, boolean defaultValue) {
		return PM_ATTRIBUTE_REGISTER.register(name, () -> new BooleanAttribute(MOD_ID + "." + descriptionId, defaultValue).setSyncable(true));
	}
}
