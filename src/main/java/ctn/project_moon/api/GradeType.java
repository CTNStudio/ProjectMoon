package ctn.project_moon.api;

import ctn.project_moon.datagen.PmTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;

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
        ZAYIN("ZAYIN", 1, PmTags.PmItem.ZAYIN),
        TETH("TETH", 2, PmTags.PmItem.TETH),
        HE("HE", 3, PmTags.PmItem.HE),
        WAW("WAW", 4, PmTags.PmItem.WAW),
        ALEPH("ALEPH", 5, PmTags.PmItem.ALEPH);

        private final String name;
        private final int level;
        private final TagKey<Item> itemTag;

        Level(String name, int Level, TagKey<Item> itemTag) {
            this.name = name;
            this.level = Level;
            this.itemTag = itemTag;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public TagKey<Item> getItemTag() {
            return itemTag;
        }

        /** 返回EGO等级tga */
        public static TagKey<Item> getEgoLevelTag(ItemStack item){
            return item.getTags()
                    .filter(it -> Objects.nonNull(Level.getItemTag(it)))
                    .findFirst()
                    .orElse(ZAYIN.getItemTag());
        }

        public static Level getItemTag(TagKey<Item> tag) {
            return Arrays.stream(Level.values())
                    .sorted((a, b) -> Integer.compare(b.getLevel(), a.getLevel()))
                    .filter(it -> tag.equals(it.getItemTag()))
                    .findFirst()
                    .orElse(ZAYIN);
        }
    }
}
