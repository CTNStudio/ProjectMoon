package ctn.project_moon.datagen;

import ctn.project_moon.init.PmItems;
import ctn.project_moon.init.PmTab;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.CuriosTestProvider.*;
import static ctn.project_moon.init.PmDamageTypes.*;


public class I18ZhCnDatagen extends LanguageProvider {
    public I18ZhCnDatagen(PackOutput output) {
        super(output, MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addTab(PmTab.EGO_WEAPON, "E.G.O 武器");
        addTab(PmTab.EGO_SUIT, "E.G.O 护甲");
        addTab(PmTab.EGO_CURIOS, "E.G.O 饰品");
        addTab(PmTab.CREATIVE_TOOL, "奇点科技");

        add(MOD_ID + ".item.geo_describe.damage_type", "伤害类型");
        add(MOD_ID + ".item.geo_describe.physics", " 物理");
        add(MOD_ID + ".item.geo_describe.spirit", " 精神");
        add(MOD_ID + ".item.geo_describe.erosion", " 侵蚀");
        add(MOD_ID + ".item.geo_describe.the_soul", " 灵魂");

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
