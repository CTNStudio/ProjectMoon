package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.api.PmApi;
import ctn.project_moon.common.item.EgoItem;
import ctn.project_moon.datagen.PmTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import javax.annotation.CheckForNull;
import java.util.Arrays;

public abstract class AbnosEntity extends Mob implements EgoItem {
    protected AbnosEntity(EntityType<? extends Mob> entityType, net.minecraft.world.level.Level level) {
        super(entityType, level);
    }

    public enum AbnosType {
        ZAYIN("zayin",1, PmTags.PmItem.ZAYIN),
        TETH("teth",2, PmTags.PmItem.TETH),
        HE("he",3, PmTags.PmItem.HE),
        WAW("waw",4, PmTags.PmItem.WAW),
        ALEPH("aleph",5, PmTags.PmItem.ALEPH);

        private final String name;
        private final int Level;
        private final TagKey<Item> tag;

        AbnosType(String name, int Level, TagKey<Item> tag) {
            this.name = name;
            this.Level = Level;
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return Level;
        }

        public TagKey<Item> getTag() {
            return tag;
        }

        public static double damageMultiple(AbnosType type, AbnosType type2) {
            return PmApi.damageMultiple(type, type2);
        }

        @CheckForNull
        public static AbnosType getTypeByTag(TagKey<Item> tag) {
            return Arrays.stream(AbnosType.values())
                    .filter(it -> tag.equals(it.getTag()))
                    .findFirst()
                    .orElse(null);
        }
    }
}
