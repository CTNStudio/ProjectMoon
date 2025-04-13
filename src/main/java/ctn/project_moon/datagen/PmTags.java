package ctn.project_moon.datagen;

import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.init.PmItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.minecraft.world.damagesource.DamageTypes.*;
import static net.minecraft.world.damagesource.DamageTypes.ARROW;
import static net.minecraft.world.damagesource.DamageTypes.CACTUS;
import static net.minecraft.world.damagesource.DamageTypes.TRIDENT;
import static net.minecraft.world.damagesource.DamageTypes.WIND_CHARGE;
import static net.minecraft.world.item.Items.*;

public class PmTags {
    public final static Item[] ALEPH_ITEMS = {SPAWNER, TRIAL_SPAWNER, ALLAY_SPAWN_EGG, ARMADILLO_SPAWN_EGG, AXOLOTL_SPAWN_EGG, BAT_SPAWN_EGG, BEE_SPAWN_EGG, BLAZE_SPAWN_EGG, BOGGED_SPAWN_EGG, BREEZE_SPAWN_EGG, CAMEL_SPAWN_EGG, CAT_SPAWN_EGG, CAVE_SPIDER_SPAWN_EGG, CHICKEN_SPAWN_EGG, COD_SPAWN_EGG, COW_SPAWN_EGG, CREEPER_SPAWN_EGG, DOLPHIN_SPAWN_EGG, DONKEY_SPAWN_EGG, DROWNED_SPAWN_EGG, ELDER_GUARDIAN_SPAWN_EGG, ENDERMAN_SPAWN_EGG, ENDERMITE_SPAWN_EGG, EVOKER_SPAWN_EGG, FOX_SPAWN_EGG, FROG_SPAWN_EGG, GHAST_SPAWN_EGG, GLOW_SQUID_SPAWN_EGG, GOAT_SPAWN_EGG, GUARDIAN_SPAWN_EGG, HOGLIN_SPAWN_EGG, HORSE_SPAWN_EGG, HUSK_SPAWN_EGG, IRON_GOLEM_SPAWN_EGG, LLAMA_SPAWN_EGG, MAGMA_CUBE_SPAWN_EGG, MOOSHROOM_SPAWN_EGG, MULE_SPAWN_EGG, OCELOT_SPAWN_EGG, PANDA_SPAWN_EGG, PARROT_SPAWN_EGG, PHANTOM_SPAWN_EGG, PIG_SPAWN_EGG, PIGLIN_SPAWN_EGG, PIGLIN_BRUTE_SPAWN_EGG, PILLAGER_SPAWN_EGG, POLAR_BEAR_SPAWN_EGG, PUFFERFISH_SPAWN_EGG, RABBIT_SPAWN_EGG, RAVAGER_SPAWN_EGG, SALMON_SPAWN_EGG, SHEEP_SPAWN_EGG, SHULKER_SPAWN_EGG, SILVERFISH_SPAWN_EGG, SKELETON_SPAWN_EGG, SKELETON_HORSE_SPAWN_EGG, SLIME_SPAWN_EGG, SNIFFER_SPAWN_EGG, SNOW_GOLEM_SPAWN_EGG, SPIDER_SPAWN_EGG, SQUID_SPAWN_EGG, STRAY_SPAWN_EGG, STRIDER_SPAWN_EGG, TADPOLE_SPAWN_EGG, TRADER_LLAMA_SPAWN_EGG, TROPICAL_FISH_SPAWN_EGG, TURTLE_SPAWN_EGG, VEX_SPAWN_EGG, VILLAGER_SPAWN_EGG, VINDICATOR_SPAWN_EGG, WANDERING_TRADER_SPAWN_EGG, WARDEN_SPAWN_EGG, WITCH_SPAWN_EGG, WITHER_SKELETON_SPAWN_EGG, WOLF_SPAWN_EGG, ZOGLIN_SPAWN_EGG, ZOMBIE_SPAWN_EGG, ZOMBIE_HORSE_SPAWN_EGG, ZOMBIE_VILLAGER_SPAWN_EGG, ZOMBIFIED_PIGLIN_SPAWN_EGG, COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK, COMMAND_BLOCK_MINECART, JIGSAW, STRUCTURE_BLOCK, STRUCTURE_VOID, BARRIER, DEBUG_STICK, LIGHT};

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
                    .add(PmItems.DETONATING_BATON.get())
                    .add(PmItems.SUIT.get())
                    .add(PmItems.DRESS_PANTS.get())
                    .add(PmItems.LOAFERS.get());
            tag(TETH)
                    .add(PmItems.WRIST_CUTTER.get());
            tag(HE)
                    .add(PmItems.BEAR_PAWS.get());
            tag(WAW)
                    .add(PmItems.LOVE_HATE.get());
            tag(ALEPH)
                    .add(PmItems.PARADISE_LOST.get())
                    .add(PmItems.CREATIVE_SPIRIT_TOOL.get())
                    .add(PmItems.CHAOS_SWORD.get())
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
                    .add(PmItems.CHAOS_SWORD.get())
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
        public static final List<ResourceKey<DamageType>> keys = List.of(CRAMMING, FALLING_ANVIL, FALLING_BLOCK, FALLING_STALACTITE, FIREWORKS, FLY_INTO_WALL, MOB_ATTACK, MOB_ATTACK_NO_AGGRO, MOB_PROJECTILE, PLAYER_ATTACK, SPIT, STING, SWEET_BERRY_BUSH, THORNS, THROWN, TRIDENT, UNATTRIBUTED_FIREBALL, WITHER_SKULL, WIND_CHARGE, ARROW, CACTUS, BAD_RESPAWN_POINT, FALL, FIREBALL, FLY_INTO_WALL);
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
            tag(PHYSICS)
                    .addOptional(PmDamageTypes.PHYSICS.location())
                    .addAll(keys);
            tag(SPIRIT)
                    .addOptional(PmDamageTypes.SPIRIT.location());
            tag(EROSION)
                    .addOptional(PmDamageTypes.EROSION.location());
            tag(THE_SOUL)
                    .addOptional(PmDamageTypes.THE_SOUL.location());
            tag(ABNOS)
                    .addOptional(PmDamageTypes.ABNOS.location());
            tag(EGO)
                    .addOptional(PmDamageTypes.EGO.location());
        }
    }
}
