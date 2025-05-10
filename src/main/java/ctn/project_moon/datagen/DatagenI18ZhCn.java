package ctn.project_moon.datagen;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.client.gui.widget.PlayerAttributeButton;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.*;
import ctn.project_moon.linkage.jade.MobEntityResistance;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.commands.PmCommands.ATTRIBUTE_TO_SET;


public class DatagenI18ZhCn extends LanguageProvider {
	public DatagenI18ZhCn(PackOutput output) {
		super(output, MOD_ID, "zh_cn");
	}

	@Override
	protected void addTranslations() {
		add(PmCreativeModeTab.EGO_WEAPON, "E.G.O 武器");
		add(PmCreativeModeTab.EGO_SUIT, "E.G.O 护甲");
		add(PmCreativeModeTab.EGO_CURIOS, "E.G.O 饰品");
		add(PmCreativeModeTab.CREATIVE_TOOL, "奇点科技");

		add(MOD_ID + ".item_tooltip.geo_describe.damage_type", "伤害类型");
		add(MOD_ID + ".item_tooltip.geo_describe.physics", "§f\ue001§r 物理");
		add(MOD_ID + ".item_tooltip.geo_describe.spirit", "§f\ue002§r 精神");
		add(MOD_ID + ".item_tooltip.geo_describe.erosion", "§f\ue003§r 侵蚀");
		add(MOD_ID + ".item_tooltip.geo_describe.the_soul", "§f\ue004§r 灵魂");
		add(MOD_ID + ".item_tooltip.press_the_key", "按下 %s 查看详细");

		addPlayerDeathMessage(PmDamageTypes.PHYSICS, "%s死于%s的造成的物理伤害");
		addPlayerDeathMessage(PmDamageTypes.SPIRIT, "%s死于%s的造成的精神污染");
		addPlayerDeathMessage(PmDamageTypes.EROSION, "%s死于%s的造成的侵蚀伤害");
		addPlayerDeathMessage(PmDamageTypes.THE_SOUL, "%s死于%s的造成的灵魂伤害");
		addPlayerDeathMessage(PmDamageTypes.EGO, "%s死于%s的§lEGO");
		addDeathMessage(PmDamageTypes.PHYSICS, "%s被剁成肉沫了");
		addDeathMessage(PmDamageTypes.SPIRIT, "%s精神崩溃而死");
		addDeathMessage(PmDamageTypes.EROSION, "%s因腐蚀而亡");
		addDeathMessage(PmDamageTypes.THE_SOUL, "%s的灵魂被超度了");
		addDeathMessage(PmDamageTypes.EGO, "%s死于§lEGO");

		addItem(PmItems.CREATIVE_SPIRIT_TOOL, "精神控制工具");
		addItem(PmItems.CHAOS_SWORD, "混沌剑");
		addItem(PmItems.DETONATING_BATON, "镇暴棍");
		addItem(PmItems.WRIST_CUTTER, "割腕者");
		addItem(PmItems.BEAR_PAWS, "熊熊抱");
		addItem(PmItems.LOVE_HATE, "以爱与恨之名");
		addItem(PmItems.PARADISE_LOST, "失乐园");

		addItem(PmItems.SUIT, "西装");
		addItem(PmItems.DRESS_PANTS, "西裤");
		addItem(PmItems.LOAFERS, "便鞋");

		addCurios(DatagenCuriosTest.HEADWEAR_CURIOS, "头饰");
		addCurios(DatagenCuriosTest.HEAD_CURIOS, "头部");
		addCurios(DatagenCuriosTest.HINDBRAIN_CURIOS, "后脑");
		addCurios(DatagenCuriosTest.EYE_AREA_CURIOS, "眼部");
		addCurios(DatagenCuriosTest.FACE_CURIOS, "面部");
		addCurios(DatagenCuriosTest.CHEEK_CURIOS, "脸颊");
		addCurios(DatagenCuriosTest.MASK_CURIOS, "口罩");
		addCurios(DatagenCuriosTest.MOUTH_CURIOS, "口部");
		addCurios(DatagenCuriosTest.NECK_CURIOS, "颈部");
		addCurios(DatagenCuriosTest.CHEST_CURIOS, "胸部");
		addCurios(DatagenCuriosTest.HAND_CURIOS, "手部");
		addCurios(DatagenCuriosTest.GLOVE_CURIOS, "手套");
		addCurios(DatagenCuriosTest.RIGHT_BACK_CURIOS, "右背");
		addCurios(DatagenCuriosTest.LEFT_BACK_CURIOS, "左背");

		add(PmEntityAttributes.PHYSICS_RESISTANCE, "受物理伤害倍率");
		add(PmEntityAttributes.SPIRIT_RESISTANCE, "受精神伤害倍率");
		add(PmEntityAttributes.EROSION_RESISTANCE, "受侵蚀伤害倍率");
		add(PmEntityAttributes.THE_SOUL_RESISTANCE, "受灵魂伤害倍率");
		add(PmEntityAttributes.ENTITY_LEVEL, "生物等级");
		add(PmEntityAttributes.MAX_FORTITUDE, "勇气最大点数");
		add(PmEntityAttributes.MAX_PRUDENCE, "谨慎最大点数");
		add(PmEntityAttributes.MAX_TEMPERANCE, "自律最大点数");
		add(PmEntityAttributes.MAX_JUSTICE, "正义最大点数");
		add(PmEntityAttributes.COMPOSITE_RATING, "综合评级");

		add(MobEntityResistance.ATTRIBUTE_DESCRIPTION_KEY, "抵抗能力");
		add(MobEntityResistance.PHYSICS_KEY, "物理");
		add(MobEntityResistance.SPIRIT_KEY, "精神");
		add(MobEntityResistance.EROSION_KEY, "侵蚀");
		add(MobEntityResistance.THE_SOUL_KEY, "灵魂");

		addEntityType(PmEntity.TRAINING_RABBITS, "教学兔兔");
		addEntityType(PmEntity.PARADISE_LOST_SPIKEWEED, "失乐园尖刺");

		add("config.jade.plugin_project_moon.level", "生物/方块等级");
		add("config.jade.plugin_project_moon.resistance", "实体抗性");

		add(ItemColorUsageReq.USE_CONDITION, "使用条件");
		add(ItemColorUsageReq.All, "无");
		add(ItemColorUsageReq.REQUIREMENT, "只能为");
		add(ItemColorUsageReq.INTERVAL, "只能为 %d 至 %d ");
		add(ItemColorUsageReq.NOT_TO_EXCEED, "不能高于");
		add(ItemColorUsageReq.NOT_LOWER_THAN, "至少为");
		add(FourColorAttribute.Type.FORTITUDE.getSerializedName(), "勇气");
		add(FourColorAttribute.Type.PRUDENCE.getSerializedName(), "谨慎");
		add(FourColorAttribute.Type.TEMPERANCE.getSerializedName(), "自律");
		add(FourColorAttribute.Type.JUSTICE.getSerializedName(), "正义");
		add(FourColorAttribute.Type.COMPOSITE_RATING.getSerializedName(), "综合评级");
		add(FourColorAttribute.Rating.I.getIdName(), "I");
		add(FourColorAttribute.Rating.II.getIdName(), "II");
		add(FourColorAttribute.Rating.III.getIdName(), "III");
		add(FourColorAttribute.Rating.IV.getIdName(), "IV");
		add(FourColorAttribute.Rating.V.getIdName(), "V");
		add(FourColorAttribute.Rating.EX.getIdName(), "EX");

		add(PmItemDataComponents.MODE_BOOLEAN, "模式开关");
		add(PmItemDataComponents.IS_RESTRAIN, "抑制器影响");
		add(PmItemDataComponents.CURRENT_DAMAGE_TYPE, "伤害类型");

		add(PmConfig.translationKey("enable_four_color_damage"), "四色伤害（物理、精神、侵蚀、灵魂）");
		add(PmConfig.commentKey("enable_four_color_damage"), "同时也会禁用对应的抗性，以及对应的效果");
		add(PmConfig.translationKey("enable_spirit_damage"), "精神伤害");
		add(PmConfig.commentKey("enable_spirit_damage"), "不会受到精神伤害但仍然会被EGO扣除理智值");
		add(PmConfig.translationKey("enable_rationality"), "理智值");
		add(PmConfig.commentKey("enable_rationality"), "禁用理智相关的所有的并判断为物理属性");
		add(PmConfig.translationKey("enable_natural_rationality_spirit"), "自然恢复理智值");
		add(PmConfig.commentKey("enable_natural_rationality_spirit"), "生物是否可以自然恢复理智值");
		add(PmConfig.translationKey("enable_low_rationality_negative_effect"), "玩家低理智负面效果");
		add(PmConfig.commentKey("enable_low_rationality_negative_effect"), "关闭这个玩家将不会在低理智的时候获得负面效果以及EGO侵蚀，或低理智触发的任何事情");
		add(PmConfig.translationKey("enable_the_soul_damage"), "灵魂伤害");
		add(PmConfig.commentKey("enable_the_soul_damage"), "禁用灵魂相关的所有的并判断为物理属性");
		add(PmConfig.translationKey("the_soul_affect_abominations"), "灵魂伤害对异想体生效");
		add(PmConfig.commentKey("the_soul_affect_abominations"), "开启将时灵魂伤害将会对异想体造成百分比伤害");
		add(PmConfig.translationKey("the_soul_affect_players"), "灵魂伤害对玩家生效");
		add(PmConfig.commentKey("the_soul_affect_players"), "关闭将时灵魂伤害将不会对玩家造成百分比伤害");
		add(PmConfig.translationKey("the_soul_affect_entities"), "灵魂伤害对非异想体的生物生效");
		add(PmConfig.commentKey("the_soul_affect_entities"), "开启将时灵魂伤害将会对非异想体的生物造成百分比伤害");
		add(PmConfig.translationKey("enable_low_rationality_filter"), "玩家低理智滤镜");
		add(PmConfig.commentKey("enable_low_rationality_filter"), "低理智的滤镜");
		add(PmConfig.translationKey("enable_four_color_damage_filter"), "遭受四色伤害滤镜");
		add(PmConfig.commentKey("enable_four_color_damage_filter"), "遭受四色伤害时会有的滤镜");
		add("project_moon.configuration.title", "月亮计划配置");
		add("project_moon.configuration.section.project.moon.server.toml", "服务端设置");
		add("project_moon.configuration.section.project.moon.server.toml.title", "服务端设置 · 这些仅会修改游戏内容不会修改视觉效果");
		add("project_moon.configuration.section.project.moon.common.toml", "通用/双端设置");
		add("project_moon.configuration.section.project.moon.common.toml.title", "通用/双端设置 · 这些会修改游戏内容和修改视觉效果");
		add("project_moon.configuration.section.project.moon.client.toml", "客户端设置");
		add("project_moon.configuration.section.project.moon.client.toml.title", "客户端设置 · 这些仅会修改视觉效果不会修改游戏内容");

		add(PlayerAttributeButton.MESSAGE, "打开月亮计划玩家属性面板");

		add(ATTRIBUTE_TO_SET + FourColorAttribute.Type.JUSTICE.getName(), "更改玩家正义点数为 %d");
		add(ATTRIBUTE_TO_SET + FourColorAttribute.Type.COMPOSITE_RATING.getName(), "更改玩家综合评级为 %d");
		add(ATTRIBUTE_TO_SET + FourColorAttribute.Type.FORTITUDE.getName(), "更改玩家勇气 为 %d");
		add(ATTRIBUTE_TO_SET + FourColorAttribute.Type.TEMPERANCE.getName(), "更改自律正义点数为 %d");
		add(ATTRIBUTE_TO_SET + FourColorAttribute.Type.PRUDENCE.getName(), "更改玩家谨慎点数为 %d");
	}

	public <T> void add(Supplier<DataComponentType<T>> dataComponentType, String name) {
		add(dataComponentType.get().toString(), name);
	}

	/** 生物属性翻译 */
	public void add(Holder<Attribute> attributeHolder, String name) {
		add(attributeHolder.value().getDescriptionId(), name);
	}

	/**
	 * 死亡消息翻译
	 */
	public void addDeathMessage(ResourceKey<DamageType> damageType, String name) {
		add("death.attack." + damageType.location().getPath(), name);
	}

	/**
	 * 玩家死亡消息翻译
	 */
	public void addPlayerDeathMessage(ResourceKey<DamageType> damageType, String name) {
		add("death.attack." + damageType.location().getPath() + ".player", name);
	}

	/**
	 * 创造模式物品栏名称翻译
	 */
	public <R, T extends R> void add(DeferredHolder<R, T> itemGroup, String name) {
		add("itemGroup." + itemGroup.getId().toString().replace(":", "."), name);
	}

	public void addCurios(String curiosIdName, String name) {
		add("curios.identifier." + curiosIdName, name);
	}
}
