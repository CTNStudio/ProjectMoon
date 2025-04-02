package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

public class SpiritEvents {
    public static final String SPIRIT = createAttribute("spirit");
    public static final String MAX_SPIRIT = createAttribute("max_spirit");
    public static final String MIN_SPIRIT = createAttribute("min_spirit");
    public static final float DEFAULT_SPIRIT_VALUE = 0;
    public static final float DEFAULT_MAX_SPIRIT_VALUE = 20;
    public static final float DEFAULT_MIN_SPIRIT_VALUE = DEFAULT_MAX_SPIRIT_VALUE * -1;

    public static void setSpiritValue(LivingEntity entity, float value){
        CompoundTag npt = entity.getPersistentData();
        npt.putFloat(SPIRIT, value);
        restrictSpirit(entity);
    }

    public static void updateSpiritValue(LivingEntity entity, float value){
        CompoundTag npt = entity.getPersistentData();
        npt.putFloat(SPIRIT, getSpiritValue(entity) + value);
        restrictSpirit(entity);
    }

    public static void setMaxSpiritValue(LivingEntity entity, float value){
        CompoundTag npt = entity.getPersistentData();
        npt.putFloat(MAX_SPIRIT, value);
        npt.putFloat(MIN_SPIRIT, value * -1);
        restrictSpirit(entity);
    }

    private static void restrictSpirit(LivingEntity entity) {
        float spirit = getSpiritValue(entity);
        float maxSpirit = getMaxSpiritValue(entity);
        float minSpirit = getMinSpiritValue(entity);
        if (spirit > maxSpirit) {
            setSpiritValue(entity, maxSpirit);
        } else if (spirit < minSpirit) {
            setSpiritValue(entity, minSpirit);
        }
    }

    public static float getSpiritValue(LivingEntity entity){
        return entity.getPersistentData().getFloat(SPIRIT);
    }

    public static float getMaxSpiritValue(LivingEntity entity){
        return entity.getPersistentData().getFloat(MAX_SPIRIT);
    }

    public static float getMinSpiritValue(LivingEntity entity){
        return entity.getPersistentData().getFloat(MIN_SPIRIT);
    }

    public static float getSpiritValue(CompoundTag npt) {
        return npt.getFloat(SPIRIT);
    }

    public static float getMaxSpiritValue(CompoundTag npt) {
        return npt.getFloat(MAX_SPIRIT);
    }

    public static float getMinSpiritValue(CompoundTag npt) {
        return npt.getFloat(MIN_SPIRIT);
    }

    private static @NotNull String createAttribute(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name + "_value").toString();
    }
}
