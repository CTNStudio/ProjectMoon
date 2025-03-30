package ctn.project_moon.common.az_renderer.item;

import mod.azure.azurelib.rewrite.animation.AzAnimator;
import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public abstract class PmAzItemRenderer extends AzItemRenderer {
    public PmAzItemRenderer(ResourceLocation model, ResourceLocation texture, Supplier<@Nullable AzAnimator<ItemStack>> animatorProvider) {
        super(AzItemRendererConfig.builder(model, texture).setAnimatorProvider(animatorProvider).useNewOffset(true).build());
    }

    public static ResourceLocation modelPath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/item/" + path + ".geo.json");
    }

    public static ResourceLocation texturePath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/geo/" + path + ".png");
    }
}
