package ctn.project_moon.api;

import ctn.project_moon.init.PmDamageSources;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class PmApi {
    public static int colorConversion(String color) {
        return TextColor.parseColor(color).getOrThrow().getValue();
    }

    public static PmDamageSources getDamageSource(Entity entity) {
        return new PmDamageSources(entity.level().registryAccess());
    }

    public static @NotNull MutableComponent createColorText(String text, String color) {
        return Component.literal(text).withColor(PmApi.colorConversion(color));
    }

    public static @NotNull MutableComponent i18ColorText(String text, String color) {
        return Component.translatable(text).withColor(PmApi.colorConversion(color));
    }
}
