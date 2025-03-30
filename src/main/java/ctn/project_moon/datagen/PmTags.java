package ctn.project_moon.datagen;

import ctn.project_moon.create.PmItems;
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
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmTags {
    public static class PmBlock extends BlockTagsProvider {
        public PmBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }

        protected static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public static class PmItem extends ItemTagsProvider {
        public PmItem(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags, MOD_ID, existingFileHelper);
        }

        public static final TagKey<Item> PHYSICS = createTag("physics");
        public static final TagKey<Item> SPIRIT = createTag("spirit");
        public static final TagKey<Item> EROSION = createTag("erosion");
        public static final TagKey<Item> THE_SOUL = createTag("the_soul") ;
        public static final TagKey<Item> ZAYIN = createTag("zayin");
        public static final TagKey<Item> TETH = createTag("teth");
        public static final TagKey<Item> HE = createTag("he");
        public static final TagKey<Item> WAW = createTag("waw");
        public static final TagKey<Item> ALEPH = createTag("aleph");
        public static final TagKey<Item> EGO = createTag("ego");
        public static final TagKey<Item> EGO_CURIOS = createTag("ego_curios");
        public static final TagKey<Item> EGO_SUIT = createTag("ego_suit");
        public static final TagKey<Item> EGO_WEAPON = createTag("ego_weapon");
        public static final TagKey<Item> EGO_WEAPON_MACE = createTag("ego_weapon_mace");
        public static final TagKey<Item> EGO_WEAPON_AXE = createTag("ego_weapon_axe");
        public static final TagKey<Item> EGO_WEAPON_PISTOL = createTag("ego_weapon_pistol");
        public static final TagKey<Item> EGO_WEAPON_BOWGUN = createTag("ego_weapon_bowgun");
        public static final TagKey<Item> EGO_WEAPON_SPEAR = createTag("ego_weapon_spear");
        public static final TagKey<Item> EGO_WEAPON_RIFLE = createTag("ego_weapon_rifle");
        public static final TagKey<Item> EGO_WEAPON_FIST = createTag("ego_weapon_fist");
        public static final TagKey<Item> EGO_WEAPON_CANNON = createTag("ego_weapon_cannon");
        public static final TagKey<Item> EGO_WEAPON_HAMMER = createTag("ego_weapon_hammer");
        public static final TagKey<Item> EGO_WEAPON_KNIFE = createTag("ego_weapon_knife");

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(ZAYIN)
                    .add(PmItems.DETONATING_BATON.get());
            tag(TETH)
                    .add(PmItems.WRIST_CUTTER.get());
            tag(HE)
                    .add(PmItems.BEAR_PAWS.get());
            tag(WAW)
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get());
            tag(ALEPH)
                    .add(PmItems.PARADISE_LOST.get());
            tag(PHYSICS) // 物理
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get())
                    .add(PmItems.DETONATING_BATON.get())
                    .add(PmItems.BEAR_PAWS.get());
            tag(SPIRIT) // 精神
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get())
                    .add(PmItems.WRIST_CUTTER.get());
            tag(EROSION) // 侵蚀
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get());
            tag(THE_SOUL) // 灵魂
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get())
                    .add(PmItems.PARADISE_LOST.get());
            tag(EGO_WEAPON_MACE);
            tag(EGO_WEAPON_AXE);
            tag(EGO_WEAPON_PISTOL);
            tag(EGO_WEAPON_BOWGUN);
            tag(EGO_WEAPON_SPEAR);
            tag(EGO_WEAPON_RIFLE);
            tag(EGO_WEAPON_FIST);
            tag(EGO_WEAPON_CANNON);
            tag(EGO_WEAPON_HAMMER);
            tag(EGO_WEAPON_KNIFE);
            tag(EGO_CURIOS);
            tag(EGO_SUIT);
            tag(EGO_WEAPON).addTags(EGO_WEAPON_MACE, EGO_WEAPON_AXE, EGO_WEAPON_PISTOL, EGO_WEAPON_BOWGUN, EGO_WEAPON_SPEAR, EGO_WEAPON_RIFLE, EGO_WEAPON_FIST, EGO_WEAPON_CANNON, (EGO_WEAPON_HAMMER), EGO_WEAPON_KNIFE);
            tag(EGO).addTags(EGO_CURIOS, EGO_SUIT, EGO_WEAPON)
                    .add(PmItems.DETONATING_BATON.get())
                    .add(PmItems.WRIST_CUTTER.get())
                    .add(PmItems.BEAR_PAWS.get())
                    .add(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get())
                    .add(PmItems.PARADISE_LOST.get());
        }

        protected static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public static class PmEntity extends EntityTypeTagsProvider {
        public PmEntity(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        public static final TagKey<EntityType<?>> ABNORMALITIES = createTag("abnormalities");
        public static final TagKey<EntityType<?>> ZAYIN = createTag("zayin");
        public static final TagKey<EntityType<?>> TETH = createTag("teth");
        public static final TagKey<EntityType<?>> HE = createTag("he");
        public static final TagKey<EntityType<?>> WAW = createTag("waw");
        public static final TagKey<EntityType<?>> ALEPH = createTag("aleph");

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }

        protected static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public static class PmDamageType extends DamageTypeTagsProvider {
        public PmDamageType(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }

        protected static TagKey<DamageType> createTag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }
}
