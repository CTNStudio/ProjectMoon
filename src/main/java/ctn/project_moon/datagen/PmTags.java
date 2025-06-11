package ctn.project_moon.datagen;

import ctn.project_moon.init.PmDamageTypes;
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
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;
import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.minecraft.tags.DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS;

public class PmTags {
	public static class PmBlock extends BlockTagsProvider {

		public PmBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @CheckForNull ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, MOD_ID, existingFileHelper);
		}

		protected static TagKey<Block> createTag(String name) {
			return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider capability) {
		}
	}

	public static class PmItem extends ItemTagsProvider {
		public static final TagKey<Item> EGO        = createTag("ego");
		public static final TagKey<Item> EGO_CURIOS = createTag("ego_curios");
		public static final TagKey<Item> EGO_SUIT   = createTag("ego_suit");
		public static final TagKey<Item> EGO_WEAPON = createTag("ego_weapon");

		public static final TagKey<Item> EGO_CURIOS_HEADWEAR   = createTag("ego_curios_headwear");
		public static final TagKey<Item> EGO_CURIOS_HEAD       = createTag("ego_curios_head");
		public static final TagKey<Item> EGO_CURIOS_HINDBRAIN  = createTag("ego_curios_hindbrain");
		public static final TagKey<Item> EGO_CURIOS_EYE_AREA   = createTag("ego_curios_eye_area");
		public static final TagKey<Item> EGO_CURIOS_FACE       = createTag("ego_curios_face");
		public static final TagKey<Item> EGO_CURIOS_CHEEK      = createTag("ego_curios_cheek");
		public static final TagKey<Item> EGO_CURIOS_MASK       = createTag("ego_curios_mask");
		public static final TagKey<Item> EGO_CURIOS_MOUTH      = createTag("ego_curios_mouth");
		public static final TagKey<Item> EGO_CURIOS_NECK       = createTag("ego_curios_neck");
		public static final TagKey<Item> EGO_CURIOS_CHEST      = createTag("ego_curios_chest");
		public static final TagKey<Item> EGO_CURIOS_HAND       = createTag("ego_curios_hand");
		public static final TagKey<Item> EGO_CURIOS_GLOVE      = createTag("ego_curios_glove");
		public static final TagKey<Item> EGO_CURIOS_RIGHT_BACK = createTag("ego_curios_right_back");
		public static final TagKey<Item> EGO_CURIOS_LEFT_BACK  = createTag("ego_curios_left_back");

		public PmItem(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags, @CheckForNull ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, blockTags, MOD_ID, existingFileHelper);
		}

		protected static TagKey<Item> createTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider capability) {
			tag(EGO_CURIOS_HEADWEAR);
			tag(EGO_CURIOS_HEAD);
			tag(EGO_CURIOS_HINDBRAIN);
			tag(EGO_CURIOS_EYE_AREA);
			tag(EGO_CURIOS_FACE);
			tag(EGO_CURIOS_CHEEK);
			tag(EGO_CURIOS_MASK);
			tag(EGO_CURIOS_MOUTH)
					.add(PmItems.MAGIC_BULLET_PIPE.get());
			tag(EGO_CURIOS_NECK);
			tag(EGO_CURIOS_CHEST);
			tag(EGO_CURIOS_HAND);
			tag(EGO_CURIOS_GLOVE);
			tag(EGO_CURIOS_RIGHT_BACK)
					.add(PmItems.PARADISE_LOST_WINGS.get());
			tag(EGO_CURIOS_LEFT_BACK);
			tag(EGO_CURIOS)
					.addTags(EGO_CURIOS_HEADWEAR, EGO_CURIOS_CHEEK, EGO_CURIOS_HEAD, EGO_CURIOS_HINDBRAIN, EGO_CURIOS_EYE_AREA, EGO_CURIOS_FACE, EGO_CURIOS_MASK, EGO_CURIOS_MOUTH, EGO_CURIOS_NECK, EGO_CURIOS_CHEST, EGO_CURIOS_HAND, EGO_CURIOS_GLOVE, EGO_CURIOS_RIGHT_BACK, EGO_CURIOS_LEFT_BACK);
			tag(EGO_SUIT);
			tag(EGO_WEAPON)
					.add(PmItems.CHAOS_SWORD.get())
					.add(PmItems.WRIST_CUTTER.get())
					.add(PmItems.BEAR_PAWS.get())
					.add(PmItems.LOVE_HATE.get())
					.add(PmItems.MAGIC_BULLET.get())
					.add(PmItems.PARADISE_LOST.get());
			tag(EGO)
					.addTags(EGO_CURIOS, EGO_SUIT, EGO_WEAPON);
		}
	}

	public static class PmEntity extends EntityTypeTagsProvider {
		public static final TagKey<EntityType<?>> ABNOS = createTag("abnos");

		public PmEntity(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @CheckForNull ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, MOD_ID, existingFileHelper);
		}

		protected static TagKey<EntityType<?>> createTag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider capability) {
			tag(ABNOS);
		}
	}

	public static class PmDamageType extends DamageTypeTagsProvider {
		public static final  TagKey<DamageType>            ABNOS                = createTag("abnos");
		public static final  TagKey<DamageType>            EGO                  = createTag("ego");

		public PmDamageType(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @CheckForNull ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, MOD_ID, existingFileHelper);
		}

		protected static TagKey<DamageType> createTag(String name) {
			return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider capability) {
			tag(ABNOS)
					.addOptional(PmDamageTypes.ABNOS.location());
			tag(EGO)
					.addOptional(PmDamageTypes.EGO.location());
			tag(ALWAYS_HURTS_ENDER_DRAGONS)
					.addTags(ABNOS, EGO);
		}
	}
}
