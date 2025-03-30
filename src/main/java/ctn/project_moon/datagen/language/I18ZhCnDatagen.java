package ctn.project_moon.datagen.language;

import ctn.project_moon.create.PmItems;
import ctn.project_moon.create.PmTab;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import static ctn.project_moon.PmMain.MOD_ID;


public class I18ZhCnDatagen extends LanguageProvider {
    public I18ZhCnDatagen(PackOutput output) {
        super(output, MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addTab(PmTab.EGO_WEAPON, "E.G.O 武器");
        addTab(PmTab.EGO_SUIT, "E.G.O 护甲");
        addTab(PmTab.EGO_CURIOS, "E.G.O 饰品");
        add(MOD_ID + ".item.geo_describe.damage_type", "伤害类型");
        add(MOD_ID + ".item.geo_describe.physics", "    物理");
        add(MOD_ID + ".item.geo_describe.spirit", "    精神");
        add(MOD_ID + ".item.geo_describe.erosion", "   侵蚀");
        add(MOD_ID + ".item.geo_describe.the_soul", "    灵魂");
        addItem(PmItems.DETONATING_BATON, "镇暴棍");
        addItem(PmItems.WRIST_CUTTER, "割腕者");
        addItem(PmItems.BEAR_PAWS, "熊熊抱");
        addItem(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE, "以爱与恨之名");
        addItem(PmItems.PARADISE_LOST, "失乐园");
    }

    /**
     * 创造模式物品栏名称翻译
     */
    public <R, T extends R> void addTab(DeferredHolder<R, T> itemGroup, String name) {
        String itemGroupName = "itemGroup." + itemGroup.getId().toString().replace(":", ".");
        add(itemGroupName, name);
    }
}
