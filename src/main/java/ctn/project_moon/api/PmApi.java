package ctn.project_moon.api;

import ctn.project_moon.common.entitys.mob.abnormalities.AbnormalitiesTypes;
import net.minecraft.network.chat.TextColor;

import static com.mojang.text2speech.Narrator.LOGGER;

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

    public static double damageMultiple(AbnormalitiesTypes type, AbnormalitiesTypes type2) {
        try {
            return damageMultiple(type.getLevel() - type2.getLevel());
        } catch (IllegalArgumentException e) {
            LOGGER.info("Abnormalities difference error");
            throw new IllegalArgumentException("Abnormalities difference error");
        }
    }

    public static int colorConversion(String color){
        return TextColor.parseColor(color).getOrThrow().getValue();
    }
}
