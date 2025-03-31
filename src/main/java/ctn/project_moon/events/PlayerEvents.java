package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerEvents {
    public static final float DEFAULT_SPIRIT_VALUE = 20;

    public static void saveToSpiritValue(PlayerEvent.SaveToFile event) {
        CompoundTag npt = event.getEntity().getPersistentData();
        npt.putFloat("Spirit", getSpiritValue(npt));
    }

    public static void loadFromSpiritValue(PlayerEvent.LoadFromFile event) {
        CompoundTag npt = event.getEntity().getPersistentData();
        if (!npt.contains("Spirit")) {
            npt.putFloat("Spirit", DEFAULT_SPIRIT_VALUE);
        }
        npt.putFloat("Spirit", getSpiritValue(npt));
    }

    public static void resetSpiritValue(PlayerEvent.PlayerRespawnEvent event) {
        CompoundTag npt = event.getEntity().getPersistentData();
        npt.putFloat("Spirit", DEFAULT_SPIRIT_VALUE);
    }

    public static float getSpiritValue(CompoundTag npt) {
        return npt.getFloat("Spirit");
    }
}
