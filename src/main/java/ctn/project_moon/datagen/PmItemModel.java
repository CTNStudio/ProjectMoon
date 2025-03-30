package ctn.project_moon.datagen;

import ctn.project_moon.create.PmItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;
import static net.minecraft.resources.ResourceLocation.parse;


public class PmItemModel extends ItemModelProvider {
    public PmItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(PmItems.EGO_SUIT_ICON.get());
        basicItem(PmItems.EGO_CURIOS_ICON.get());
        basicItem(PmItems.EGO_WEAPON_ICON.get());
    }

    /** 用与给geo模型生成的 */
    public void geoItem(Item item) {
        getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(parse("builtin/entity")));
    }

    public ItemModelBuilder basicItem(Item item, String name) {
        return basicItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), name);
    }

    public ItemModelBuilder basicItem(ResourceLocation item, String name) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile(fromNamespaceAndPath(MOD_ID, "models/item/"+ name)))
                .texture("layer0", fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }
}
