package ctn.project_moon.tool;

import ctn.project_moon.datagen.PmTags;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.Objects;

import static ctn.project_moon.PmMain.LOGGER;

public class GradeTypeTool {
    /**
     * 返回实体或物品的伤害倍数
     */
    public static float damageMultiple(Level laval, Level laval2) {
        return damageMultiple(leveDifferenceValue(laval, laval2));
    }

    /**
     * 返回实体或物品之间的等级差值
     */
    public static int leveDifferenceValue(Level level, Level level2) {
        return level.getLevelValue() - level2.getLevelValue();
    }

    public static float damageMultiple(int i) {
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

    public enum Level {
        ZAYIN("ZAYIN", 1, PmTags.PmItem.ZAYIN, PmTags.PmBlock.ZAYIN, PmColourTool.ZAYIN),
        TETH("TETH", 2, PmTags.PmItem.TETH, PmTags.PmBlock.TETH, PmColourTool.TETH),
        HE("HE", 3, PmTags.PmItem.HE, PmTags.PmBlock.HE, PmColourTool.HE),
        WAW("WAW", 4, PmTags.PmItem.WAW, PmTags.PmBlock.WAW, PmColourTool.WAW),
        ALEPH("ALEPH", 5, PmTags.PmItem.ALEPH, PmTags.PmBlock.ALEPH, PmColourTool.ALEPH);

        private final String name;
        private final int levelValue;
        private final TagKey<Item> itemTag;
        private final TagKey<Block> blockTag;
        private final PmColourTool colour;

        Level(String name, int levelValue, TagKey<Item> itemTag, TagKey<Block> blockTag, PmColourTool colour) {
            this.name = name;
            this.levelValue = levelValue;
            this.itemTag = itemTag;
            this.blockTag = blockTag;
            this.colour = colour;
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
                    .filter(it -> (int) entity.getAttributeValue(PmAttributes.ENTITY_LEVEL) == it.getLevelValue())
                    .findFirst()
                    .orElse(ZAYIN);
        }

        public PmColourTool getColour() {
            return colour;
        }

        public String getColourText() {
            return colour.getColour();
        }
    }
}
