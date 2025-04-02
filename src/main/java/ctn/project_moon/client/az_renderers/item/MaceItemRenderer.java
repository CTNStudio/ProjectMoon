package ctn.project_moon.client.az_renderers.item;

import mod.azure.azurelib.rewrite.animation.AzAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class MaceItemRenderer extends PmAzItemRenderer {
    public MaceItemRenderer(ResourceLocation model, ResourceLocation texture, Supplier<@Nullable AzAnimator<ItemStack>> animatorProvider) {
        super(model, texture, animatorProvider);
    }

    public static ResourceLocation modelPath(String path){
        return PmAzItemRenderer.modelPath("mace/" + path);
    }

    public static ResourceLocation texturePath(String path){
        return PmAzItemRenderer.texturePath("mace/" + path);
    }
}
