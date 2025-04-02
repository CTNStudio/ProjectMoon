package ctn.project_moon.api;

import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.init.PmDamageSources;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;

import static ctn.project_moon.PmMain.LOGGER;

public class PmApi {

    public static double damageMultiple(int i) {
        return switch (i) {
            case 4 -> 0.4;
            case 3 -> 0.6;
            case 2 -> 0.7;
            case 1 -> 0.8;
            case 0 -> 1.0;
            case -1 -> 1.0;
            case -2 -> 1.2;
            case -3 -> 1.5;
            case -4 -> 2.0;
            default -> {
                LOGGER.info("EgoItem difference error");
                throw new IllegalArgumentException("EgoItem difference error");
            }
        };
    }

    public static double damageMultiple(AbnosEntity.AbnosType type, AbnosEntity.AbnosType type2) {
        try {
            return damageMultiple(type.getLevel() - type2.getLevel());
        } catch (IllegalArgumentException e) {
            LOGGER.info("Abnos difference error");
            throw new IllegalArgumentException("Abnos difference error");
        }
    }

    public static int colorConversion(String color){
        return TextColor.parseColor(color).getOrThrow().getValue();
    }

    public static PmDamageSources getDamageSource(Entity entity) {
        return new PmDamageSources(entity.level().registryAccess());
    }
}
