package ctn.project_moon.common.client.az_animators.item;


import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzItemAnimator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

public abstract class PmAzItemAnimator extends AzItemAnimator {
    private final ResourceLocation ANIMATIONS;

    public PmAzItemAnimator(String path){
        ANIMATIONS = ResourceLocation.fromNamespaceAndPath(MOD_ID, jsonFilePath(path));
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ItemStack> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ANIMATIONS;
    }

    public String jsonFilePath(String path) {
        return "animations/item/" + path + ".json";
    }
}
