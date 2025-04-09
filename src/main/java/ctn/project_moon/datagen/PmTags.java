package ctn.project_moon.datagen;

import ctn.project_moon.init.PmItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmTags {
    public final static Item[] ALEPH_ITEMS = {Items.SPAWNER, Items.TRIAL_SPAWNER, Items.ALLAY_SPAWN_EGG, Items.ARMADILLO_SPAWN_EGG, Items.AXOLOTL_SPAWN_EGG, Items.BAT_SPAWN_EGG, Items.BEE_SPAWN_EGG, Items.BLAZE_SPAWN_EGG, Items.BOGGED_SPAWN_EGG, Items.BREEZE_SPAWN_EGG, Items.CAMEL_SPAWN_EGG, Items.CAT_SPAWN_EGG, Items.CAVE_SPIDER_SPAWN_EGG, Items.CHICKEN_SPAWN_EGG, Items.COD_SPAWN_EGG, Items.COW_SPAWN_EGG, Items.CREEPER_SPAWN_EGG, Items.DOLPHIN_SPAWN_EGG, Items.DONKEY_SPAWN_EGG, Items.DROWNED_SPAWN_EGG, Items.ELDER_GUARDIAN_SPAWN_EGG, Items.ENDERMAN_SPAWN_EGG, Items.ENDERMITE_SPAWN_EGG, Items.EVOKER_SPAWN_EGG, Items.FOX_SPAWN_EGG, Items.FROG_SPAWN_EGG, Items.GHAST_SPAWN_EGG, Items.GLOW_SQUID_SPAWN_EGG, Items.GOAT_SPAWN_EGG, Items.GUARDIAN_SPAWN_EGG, Items.HOGLIN_SPAWN_EGG, Items.HORSE_SPAWN_EGG, Items.HUSK_SPAWN_EGG, Items.IRON_GOLEM_SPAWN_EGG, Items.LLAMA_SPAWN_EGG, Items.MAGMA_CUBE_SPAWN_EGG, Items.MOOSHROOM_SPAWN_EGG, Items.MULE_SPAWN_EGG, Items.OCELOT_SPAWN_EGG, Items.PANDA_SPAWN_EGG, Items.PARROT_SPAWN_EGG, Items.PHANTOM_SPAWN_EGG, Items.PIG_SPAWN_EGG, Items.PIGLIN_SPAWN_EGG, Items.PIGLIN_BRUTE_SPAWN_EGG, Items.PILLAGER_SPAWN_EGG, Items.POLAR_BEAR_SPAWN_EGG, Items.PUFFERFISH_SPAWN_EGG, Items.RABBIT_SPAWN_EGG, Items.RAVAGER_SPAWN_EGG, Items.SALMON_SPAWN_EGG, Items.SHEEP_SPAWN_EGG, Items.SHULKER_SPAWN_EGG, Items.SILVERFISH_SPAWN_EGG, Items.SKELETON_SPAWN_EGG, Items.SKELETON_HORSE_SPAWN_EGG, Items.SLIME_SPAWN_EGG, Items.SNIFFER_SPAWN_EGG, Items.SNOW_GOLEM_SPAWN_EGG, Items.SPIDER_SPAWN_EGG, Items.SQUID_SPAWN_EGG, Items.STRAY_SPAWN_EGG, Items.STRIDER_SPAWN_EGG, Items.TADPOLE_SPAWN_EGG, Items.TRADER_LLAMA_SPAWN_EGG, Items.TROPICAL_FISH_SPAWN_EGG, Items.TURTLE_SPAWN_EGG, Items.VEX_SPAWN_EGG, Items.VILLAGER_SPAWN_EGG, Items.VINDICATOR_SPAWN_EGG, Items.WANDERING_TRADER_SPAWN_EGG, Items.WARDEN_SPAWN_EGG, Items.WITCH_SPAWN_EGG, Items.WITHER_SKELETON_SPAWN_EGG, Items.WOLF_SPAWN_EGG, Items.ZOGLIN_SPAWN_EGG, Items.ZOMBIE_SPAWN_EGG, Items.ZOMBIE_HORSE_SPAWN_EGG, Items.ZOMBIE_VILLAGER_SPAWN_EGG, Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, Items.COMMAND_BLOCK, Items.CHAIN_COMMAND_BLOCK, Items.REPEATING_COMMAND_BLOCK, Items.COMMAND_BLOCK_MINECART, Items.JIGSAW, Items.STRUCTURE_BLOCK, Items.STRUCTURE_VOID, Items.BARRIER, Items.DEBUG_STICK, Items.LIGHT};

    public static class PmBlock extends BlockTagsProvider {
        public PmBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        protected static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    public static class PmItem extends ItemTagsProvider {
        public static final TagKey<Item> PHYSICS = createTag("physics");
        public static final TagKey<Item> SPIRIT = createTag("spirit");
        public static final TagKey<Item> EROSION = createTag("erosion");
        public static final TagKey<Item> THE_SOUL = createTag("the_soul");
        public static final TagKey<Item> ZAYIN = createTag("zayin");
        public static final TagKey<Item> TETH = createTag("teth");
        public static final TagKey<Item> HE = createTag("he");
        public static final TagKey<Item> WAW = createTag("waw");
        public static final TagKey<Item> ALEPH = createTag("aleph");
        public static final TagKey<Item> EGO = createTag("ego");
        public static final TagKey<Item> EGO_CURIOS = createTag("ego_curios");
        public static final TagKey<Item> EGO_SUIT = createTag("ego_suit");
        public static final TagKey<Item> EGO_WEAPON = createTag("ego_weapon");

        public static final TagKey<Item> EGO_CURIOS_HEADWEAR = createTag("ego_curios_headwear");
        public static final TagKey<Item> EGO_CURIOS_HEAD = createTag("ego_curios_head");
        public static final TagKey<Item> EGO_CURIOS_HINDBRAIN  = createTag("ego_curios_hindbrain");
        public static final TagKey<Item> EGO_CURIOS_EYE_AREA = createTag("ego_curios_eye_area");
        public static final TagKey<Item> EGO_CURIOS_FACE  = createTag("ego_curios_face");
        public static final TagKey<Item> EGO_CURIOS_CHEEK  = createTag("ego_curios_cheek");
        public static final TagKey<Item> EGO_CURIOS_MASK  = createTag("ego_curios_mask");
        public static final TagKey<Item> EGO_CURIOS_MOUTH  = createTag("ego_curios_mouth");
        public static final TagKey<Item> EGO_CURIOS_NECK  = createTag("ego_curios_neck");
        public static final TagKey<Item> EGO_CURIOS_CHEST = createTag("ego_curios_chest");
        public static final TagKey<Item> EGO_CURIOS_HAND  = createTag("ego_curios_hand");
        public static final TagKey<Item> EGO_CURIOS_GLOVE  = createTag("ego_curios_glove");
        public static final TagKey<Item> EGO_CURIOS_RIGHT_BACK = createTag("ego_curios_right_back");
        public static final TagKey<Item> EGO_CURIOS_LEFT_BACK  = createTag("ego_curios_left_back");

        public PmItem(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags, MOD_ID, existingFileHelper);
        }

        protected static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(ZAYIN)
                    .add(PmItems.DETONATING_BATON.get());
            tag(TETH)
                    .add(PmItems.WRIST_CUTTER.get());
            tag(HE)
                    .add(PmItems.BEAR_PAWS.get());
            tag(WAW)
                    .add(PmItems.LOVE_HATE.get());
            tag(ALEPH)
                    .add(PmItems.PARADISE_LOST.get())
                    .add(PmItems.CREATIVE_SPIRIT_TOOL.get())
                    .add(PmItems.CHAOS_KNIFE.get())
                    .add(ALEPH_ITEMS);
            tag(PHYSICS) // 物理
                    .add(PmItems.LOVE_HATE.get())
                    .add(PmItems.DETONATING_BATON.get())
                    .add(PmItems.BEAR_PAWS.get());
            tag(SPIRIT) // 精神
                    .add(PmItems.LOVE_HATE.get())
                    .add(PmItems.WRIST_CUTTER.get());
            tag(EROSION) // 侵蚀
                    .add(PmItems.LOVE_HATE.get());
            tag(THE_SOUL) // 灵魂
                    .add(PmItems.LOVE_HATE.get())
                    .add(PmItems.PARADISE_LOST.get());
            tag(EGO_CURIOS_HEADWEAR);
            tag(EGO_CURIOS_HEAD);
            tag(EGO_CURIOS_HINDBRAIN);
            tag(EGO_CURIOS_EYE_AREA);
            tag(EGO_CURIOS_FACE);
            tag(EGO_CURIOS_CHEEK);
            tag(EGO_CURIOS_MASK);
            tag(EGO_CURIOS_MOUTH);
            tag(EGO_CURIOS_NECK);
            tag(EGO_CURIOS_CHEST);
            tag(EGO_CURIOS_HAND);
            tag(EGO_CURIOS_GLOVE);
            tag(EGO_CURIOS_RIGHT_BACK);
            tag(EGO_CURIOS_LEFT_BACK);
            tag(EGO_CURIOS).addTags(EGO_CURIOS_HEADWEAR, EGO_CURIOS_CHEEK, EGO_CURIOS_HEAD, EGO_CURIOS_HINDBRAIN, EGO_CURIOS_EYE_AREA, EGO_CURIOS_FACE, EGO_CURIOS_MASK, EGO_CURIOS_MOUTH, EGO_CURIOS_NECK, EGO_CURIOS_CHEST, EGO_CURIOS_HAND, EGO_CURIOS_GLOVE, EGO_CURIOS_RIGHT_BACK, EGO_CURIOS_LEFT_BACK);
            tag(EGO_SUIT);
            tag(EGO_WEAPON)
                    .add(PmItems.DETONATING_BATON.get())
                    .add(PmItems.WRIST_CUTTER.get())
                    .add(PmItems.BEAR_PAWS.get())
                    .add(PmItems.LOVE_HATE.get())
                    .add(PmItems.PARADISE_LOST.get());
            tag(EGO).addTags(EGO_CURIOS, EGO_SUIT, EGO_WEAPON);
        }
    }

    public static class PmEntity extends EntityTypeTagsProvider {
        public static final TagKey<EntityType<?>> ABNOS = createTag("abnos");

        public PmEntity(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        protected static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(ABNOS);
        }
    }

    public static class PmDamageType extends DamageTypeTagsProvider {
        public static final TagKey<DamageType> PHYSICS = createTag("physics");
        public static final TagKey<DamageType> SPIRIT = createTag("spirit");
        public static final TagKey<DamageType> EROSION = createTag("erosion");
        public static final TagKey<DamageType> THE_SOUL = createTag("the_soul");
        public static final TagKey<DamageType> ABNOS = createTag("abnos");
        public static final TagKey<DamageType> EGO = createTag("ego");

        public PmDamageType(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        protected static TagKey<DamageType> createTag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(PHYSICS);
            tag(SPIRIT);
            tag(EROSION);
            tag(THE_SOUL);
            tag(ABNOS);
            tag(EGO);
        }
    }
}
