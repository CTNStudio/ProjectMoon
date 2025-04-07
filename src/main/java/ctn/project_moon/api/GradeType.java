package ctn.project_moon.api;

import ctn.project_moon.datagen.PmTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static ctn.project_moon.PmMain.LOGGER;

public class GradeType {
    /**
     * 返回EGO之间的伤害倍数
     */
    public static double damageMultiple(Level laval, Level laval2) {
        return damageMultiple(leveDifferenceValue(laval, laval2));
    }

    /**
     * 返回之间的等级差值
     */
    public static int leveDifferenceValue(Level level, Level level2) {
        return level.getLevel() - level2.getLevel();
    }

    public static double damageMultiple(int i) {
        return switch (i) {
            case 4 -> 0.4;
            case 3 -> 0.6;
            case 2 -> 0.7;
            case 1 -> 0.8;
            case 0 -> 1.0;
            case -1 -> 1.0;
            case -2 -> 1.2;
            case -3 -> 1.5;
            case -4 -> 2.0;
            default -> {
                LOGGER.info("EgoItem difference error");
                throw new IllegalArgumentException("EgoItem difference error");
            }
        };
    }

    public enum Level {
        ZAYIN("zayin", 1, PmTags.PmItem.ZAYIN),
        TETH("teth", 2, PmTags.PmItem.TETH),
        HE("he", 3, PmTags.PmItem.HE),
        WAW("waw", 4, PmTags.PmItem.WAW),
        ALEPH("aleph", 5, PmTags.PmItem.ALEPH);

        private final String name;
        private final int Level;
        private final TagKey<Item> itemTag;

        Level(String name, int Level, TagKey<Item> itemTag) {
            this.name = name;
            this.Level = Level;
            this.itemTag = itemTag;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return Level;
        }

        public TagKey<Item> getItemTag() {
            return itemTag;
        }
    }
}
