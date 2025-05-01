package ctn.project_moon.datagen;

import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.PmCreativeModeTab;
import ctn.project_moon.init.PmItems;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.DatagenCuriosTest.*;
import static ctn.project_moon.init.PmDamageTypes.*;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static ctn.project_moon.linkage.jade.MobEntityResistance.*;


public class DatagenI18ZhCn extends LanguageProvider {
    public DatagenI18ZhCn(PackOutput output) {
        super(output, MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addTab(PmCreativeModeTab.EGO_WEAPON, "E.G.O 武器");
        addTab(PmCreativeModeTab.EGO_SUIT, "E.G.O 护甲");
        addTab(PmCreativeModeTab.EGO_CURIOS, "E.G.O 饰品");
        addTab(PmCreativeModeTab.CREATIVE_TOOL, "奇点科技");

        add(MOD_ID + ".item.geo_describe.damage_type", "伤害类型");
        add(MOD_ID + ".item.geo_describe.physics", "§f\ue001§r 物理");
        add(MOD_ID + ".item.geo_describe.spirit", "§f\ue002§r 精神");
        add(MOD_ID + ".item.geo_describe.erosion", "§f\ue003§r 侵蚀");
        add(MOD_ID + ".item.geo_describe.the_soul", "§f\ue004§r 灵魂");

        addPlayerDeathMessage(PHYSICS, "%s死于%s的造成的§4§l物理§r伤害");
        addPlayerDeathMessage(SPIRIT, "%s死于%s的造成的§l精神§r污染");
        addPlayerDeathMessage(EROSION, "%s死于%s的造成的§5§l侵蚀§r伤害");
        addPlayerDeathMessage(THE_SOUL, "%s死于%s的造成的§b§l灵魂§r伤害");
        addPlayerDeathMessage(EGO, "%s死于%s的§lEGO§r");

        addDeathMessage(PHYSICS, "%s被剁成肉沫了");
        addDeathMessage(SPIRIT, "%s精神崩溃而死");
        addDeathMessage(EROSION, "%s因腐蚀而亡");
        addDeathMessage(THE_SOUL, "%s的灵魂被超度了");
        addDeathMessage(EGO, "%s死于§lEGO§r");

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

        addCurios(HEADWEAR_CURIOS, "头饰");
        addCurios(HEAD_CURIOS, "头部");
        addCurios(HINDBRAIN_CURIOS, "后脑");
        addCurios(EYE_AREA_CURIOS, "眼部");
        addCurios(FACE_CURIOS, "面部");
        addCurios(CHEEK_CURIOS, "脸颊");
        addCurios(MASK_CURIOS, "口罩");
        addCurios(MOUTH_CURIOS, "口部");
        addCurios(NECK_CURIOS, "颈部");
        addCurios(CHEST_CURIOS, "胸部");
        addCurios(HAND_CURIOS, "手部");
        addCurios(GLOVE_CURIOS, "手套");
        addCurios(RIGHT_BACK_CURIOS, "右背");
        addCurios(LEFT_BACK_CURIOS, "左背");

        addAttribute(PHYSICS_RESISTANCE, "受物理伤害倍率");
        addAttribute(SPIRIT_RESISTANCE, "受精神伤害倍率");
        addAttribute(EROSION_RESISTANCE, "受侵蚀伤害倍率");
        addAttribute(THE_SOUL_RESISTANCE, "受灵魂伤害倍率");
        addAttribute(ENTITY_LEVEL, "生物级别");

        add(ATTRIBUTE_DESCRIPTION_KEY, "抗性（值越大越抗性越低）");
        add(PHYSICS_KEY, "物理");
        add(SPIRIT_KEY, "精神");
        add(EROSION_KEY, "侵蚀");
        add(THE_SOUL_KEY, "灵魂");

        add("config.jade.plugin_project_moon.level", "生物/方块等级");
        add("config.jade.plugin_project_moon.resistance", "实体抗性");

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
    }

    /** 生物属性翻译 */
    public void addAttribute(Holder<Attribute> attributeHolder, String name) {
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
    public <R, T extends R> void addTab(DeferredHolder<R, T> itemGroup, String name) {
        add("itemGroup." + itemGroup.getId().toString().replace(":", "."), name);
    }

    public void addCurios(String curiosIdName, String name){
        add("curios.identifier." + curiosIdName, name);
    }
}
