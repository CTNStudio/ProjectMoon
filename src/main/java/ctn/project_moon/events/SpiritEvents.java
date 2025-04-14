package ctn.project_moon.events;

import ctn.project_moon.common.payload.SpiritValueDelivery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 理智值相关
 */
public class SpiritEvents {
    public static final String SPIRIT = createAttribute("spirit");
    public static final String MAX_SPIRIT = createAttribute("max_spirit");
    public static final String MIN_SPIRIT = createAttribute("min_spirit");
    public static final float DEFAULT_SPIRIT_VALUE = 0;
    public static final float DEFAULT_MAX_SPIRIT_VALUE = 20;
    public static final float DEFAULT_MIN_SPIRIT_VALUE = DEFAULT_MAX_SPIRIT_VALUE * -1;

    public static void setSpiritValue(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT, value);
        restrictSpirit(entity);
    }

    public static void updateSpiritValue(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT, getSpiritValue(entity) + value);
        restrictSpirit(entity);
    }

    public static void setMaxSpiritValue(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(MAX_SPIRIT, value);
        nbt.putFloat(MIN_SPIRIT, value * -1);
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
        if (!(entity instanceof ServerPlayer serverPlayer)){
            return;
        }
        PacketDistributor.sendToPlayer(serverPlayer, SpiritValueDelivery.create(serverPlayer));
    }

    public static float getSpiritValue(LivingEntity entity) {
        return entity.getPersistentData().getFloat(SPIRIT);
    }

    public static float getMaxSpiritValue(LivingEntity entity) {
        return entity.getPersistentData().getFloat(MAX_SPIRIT);
    }

    public static float getMinSpiritValue(LivingEntity entity) {
        return entity.getPersistentData().getFloat(MIN_SPIRIT);
    }

    public static float getSpiritValue(CompoundTag nbt) {
        return nbt.getFloat(SPIRIT);
    }

    public static float getMaxSpiritValue(CompoundTag nbt) {
        return nbt.getFloat(MAX_SPIRIT);
    }

    public static float getMinSpiritValue(CompoundTag nbt) {
        return nbt.getFloat(MIN_SPIRIT);
    }

    public static @NotNull String createAttribute(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name + "_value").toString();
    }
}
