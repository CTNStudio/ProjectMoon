package ctn.project_moon.api;

import ctn.project_moon.init.PmDamageSources;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;

public class PmApi {
    public static int colorConversion(String color) {
        return TextColor.parseColor(color).getOrThrow().getValue();
    }

    public static PmDamageSources getDamageSource(Entity entity) {
        return new PmDamageSources(entity.level().registryAccess());
    }
}
