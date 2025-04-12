package c;

import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.TranslatableEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class ClientConfigs {
    public static boolean damageIndicator = true;
    public static boolean healIndicator = true;
    private static BooleanValue DAMAGE_INDICATOR;
    private static BooleanValue HEAL_INDICATOR;

    public static void onLoad() {
        damageIndicator = DAMAGE_INDICATOR.get();
        healIndicator = HEAL_INDICATOR.get();
    }

    public static void register(ModContainer container) {
        Builder BUILDER = new Builder();

        DAMAGE_INDICATOR = BUILDER.define("damageIndicator", true);
        HEAL_INDICATOR = BUILDER.define("healIndicator", true);
        BUILDER.pop();


        container.registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
    }

    public enum GoreEffect implements TranslatableEnum {
        OFF, CONFLUENCE, CONFLUENCE_VANILLA, ALL;

        @Override
        @NotNull
        public Component getTranslatedName() {
            return Component.translatable("confluence.configuration.goreEffect." + name().toLowerCase(Locale.ROOT));
        }
    }
}
