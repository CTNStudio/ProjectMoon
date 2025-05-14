package ctn.project_moon.datagen;

import ctn.project_moon.init.PmItemPropertyEvents;
import ctn.project_moon.init.PmItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;
import static net.minecraft.resources.ResourceLocation.parse;


public class DatagenItemModel extends ItemModelProvider {
	public DatagenItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(PmItems.EGO_SUIT_ICON.get());
		basicItem(PmItems.EGO_CURIOS_ICON.get());
		basicItem(PmItems.EGO_WEAPON_ICON.get());
		basicItem(PmItems.CREATIVE_TOOL_ICON.get());
		basicItem(PmItems.PARADISE_LOST_WINGS.get());
		basicItem(PmItems.MAGIC_BULLET_PIPE.get());
		basicItem(PmItems.MAGIC_BULLET_CHESTPLATE.get());
		basicItem(PmItems.MAGIC_BULLET_LEGGINGS.get());
		basicItem(PmItems.MAGIC_BULLET_BOOTS.get());

		LinkedHashMap<Float, String> creativeSpiritTool = new LinkedHashMap<>();
		creativeSpiritTool.put(0F, "add");
		creativeSpiritTool.put(1F, "decrease");
		createModelFile(PmItems.CREATIVE_SPIRIT_TOOL.get(), creativeSpiritTool, PmItemPropertyEvents.MODE_BOOLEAN);
		LinkedHashMap<Float, String> chaosKnife = new LinkedHashMap<>();
		chaosKnife.put(0F, "physics");
		chaosKnife.put(0.1F, "spirit");
		chaosKnife.put(0.2F, "erosion");
		chaosKnife.put(0.3F, "the_soul");
		createModelFile(PmItems.CHAOS_SWORD.get(), chaosKnife, getParent("item/handheld"), PmItemPropertyEvents.CURRENT_DAMAGE_TYPE);
	}

	private ModelFile.@NotNull UncheckedModelFile getParent(String name) {
		return new ModelFile.UncheckedModelFile(ResourceLocation.withDefaultNamespace(name));
	}

	public void createModelFile(Item item, Map<Float, String> texture, ResourceLocation... predicates) {
		var mod = basicItem(item);
		var predicate = predicates[0];
		Iterator<Float> iteratorKey = texture.keySet().iterator();
		Float key;
		String value;
		for (int i = 0; i < texture.size(); i++) {
			key = iteratorKey.next();
			value = texture.get(key);
			if (predicates.length > 1) {
				predicate = predicates[i];
			}
			mod.override().model(createModelFile(item, value)).predicate(predicate, key).end();
			specialItem(item, value);
		}
	}

	public void createModelFile(Item item, Map<Float, String> texture, ModelFile parent, ResourceLocation... predicates) {
		var mod = basicItem(item).parent(parent);
		var predicate = predicates[0];
		Iterator<Float> iteratorKey = texture.keySet().iterator();
		Float key;
		String value;
		for (int i = 0; i < texture.size(); i++) {
			key = iteratorKey.next();
			value = texture.get(key);
			if (predicates.length > 1) {
				predicate = predicates[i];
			}
			mod.override().model(createModelFile(item, value)).predicate(predicate, key).end();
			specialItem(item, value).parent(parent);
		}
	}

	public ModelFile.UncheckedModelFile createModelFile(Item item, String name) {
		return new ModelFile.UncheckedModelFile(getItemResourceLocation(item, name).withPrefix("item/"));
	}

	public ItemModelBuilder specialItem(Item item, String name) {
		return basicItem(getItemResourceLocation(item, name));
	}

	public ItemModelBuilder createModelItem(Item item, ModelFile parent) {
		ResourceLocation resourceLocation = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
		return getBuilder(item.toString())
				.parent(parent)
				.texture("layer0", fromNamespaceAndPath(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath()));
	}

	private @NotNull ResourceLocation getItemResourceLocation(Item item, String name) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).withSuffix("_" + name);
	}

	/**
	 * 用与给geo模型生成的
	 */
	public void geoItem(Item item) {
		getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(parse("builtin/entity")));
	}

	public ItemModelBuilder basicItem(Item item, String name) {
		return basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), name);
	}

	public ItemModelBuilder basicItem(ResourceLocation item, String name) {
		return getBuilder(item.toString())
				.parent(customModelFile("models/item/" + name))
				.texture("layer0", fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
	}

	public ModelFile customModelFile(String name) {
		return new ModelFile.UncheckedModelFile(fromNamespaceAndPath(MOD_ID, name));
	}
}
