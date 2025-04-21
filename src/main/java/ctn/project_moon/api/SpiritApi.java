package ctn.project_moon.api;

import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 理智值相关
 */
public class SpiritApi {
    public static final String SPIRIT_VALUE = createAttribute("entity.spirit_value");
    public static final String SPIRIT_RECOVERY_COUNT = createAttribute("entity.spirit_recovery_count");
    public static final String INJURY_COUNT = createAttribute("entity.injury_count");
    public static void setSpiritValue(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT_VALUE, value);
        restrictSpirit(entity);
    }

    public static void setSpiritRecoveryCount(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT_RECOVERY_COUNT, value);
    }

    public static void setInjuryCount(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(INJURY_COUNT, value);
    }

    public static void incrementSpiritValue(LivingEntity entity, float value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT_VALUE, getSpiritValue(entity) + value);
        restrictSpirit(entity);
    }

    public static void incrementSpiritRecoveryTicks(LivingEntity entity, int value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putInt(SPIRIT_RECOVERY_COUNT, getSpiritRecoveryTicks(entity) + value);
    }

    public static void incrementInjuryCount(LivingEntity entity, int value) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putInt(INJURY_COUNT, getInjuryCount(entity) + value);
    }

    public static void setMaxSpiritValue(LivingEntity entity, double value) {
        entity.getAttribute(PmAttributes.MAX_SPIRIT).setBaseValue(value);
        restrictSpirit(entity);
    }

    private static void restrictSpirit(LivingEntity entity) {
        float spirit = getSpiritValue(entity);
        double maxSpirit = getMaxSpiritValue(entity);
        double minSpirit = getMinSpiritValue(entity);
        if (spirit > maxSpirit) {
            setSpiritValue(entity, (float) maxSpirit);
        } else if (spirit < minSpirit) {
            setSpiritValue(entity, (float) minSpirit);
        }
        if (!(entity instanceof ServerPlayer serverPlayer)){
            return;
        }
//        PacketDistributor.sendToPlayer(serverPlayer, SpiritValueDelivery.create(serverPlayer));
    }

    /** 玩家死亡重置理智 */
    public static void resetSpiritValue(LivingEntity entity) {
        CompoundTag nbt = entity.getPersistentData();
        nbt.putFloat(SPIRIT_VALUE, 0);
    }

    /** 添加/保存理智属性信息 */
    public static void processAttributeInformation(LivingEntity entity) {
        CompoundTag nbt = entity.getPersistentData();
        if (!nbt.contains(SPIRIT_VALUE)) {
            nbt.putFloat(SPIRIT_VALUE, 0);
        }
        if (!nbt.contains(SPIRIT_RECOVERY_COUNT)) {
            nbt.putInt(SPIRIT_RECOVERY_COUNT, 0);
        }
        if (!nbt.contains(INJURY_COUNT)) {
            nbt.putInt(INJURY_COUNT, 0);
        }
        nbt.putFloat(SPIRIT_VALUE, getSpiritValue(nbt));
        nbt.putInt(SPIRIT_RECOVERY_COUNT, getSpiritRecoveryTicks(nbt));
        nbt.putInt(INJURY_COUNT, getInjuryCount(nbt));
    }

    public static float getSpiritValue(LivingEntity entity) {
        return entity.getPersistentData().getFloat(SPIRIT_VALUE);
    }

    public static int getSpiritRecoveryTicks(LivingEntity entity) {
        return entity.getPersistentData().getInt(SPIRIT_RECOVERY_COUNT);
    }

    public static int getInjuryCount(LivingEntity entity) {
        return entity.getPersistentData().getInt(INJURY_COUNT);
    }

    public static double getMaxSpiritValue(LivingEntity entity) {
        return entity.getAttributeValue(PmAttributes.MAX_SPIRIT);
    }

    public static double getMinSpiritValue(LivingEntity entity) {
        return -(entity.getAttributeValue(PmAttributes.MAX_SPIRIT));
    }

    public static float getSpiritValue(CompoundTag nbt) {
        return nbt.getFloat(SPIRIT_VALUE);
    }

    public static int getSpiritRecoveryTicks(CompoundTag nbt) {
        return nbt.getInt(SPIRIT_RECOVERY_COUNT);
    }

    public static int getInjuryCount(CompoundTag nbt) {
        return nbt.getInt(INJURY_COUNT);
    }

    public static @NotNull String createAttribute(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name).toString();
    }

    /** 如果受伤者没有理智，则仅减少理智 */
    public static void executeSpiritDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (isRationality(entity)) return;
        handleRationally(event, entity);
        event.setNewDamage(0);
    }

    public static boolean isRationality(LivingEntity entity) {
        return !(entity.getPersistentData().contains(SpiritApi.SPIRIT_VALUE) && PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() && PmConfig.COMMON.ENABLE_RATIONALITY.get());
    }

    /** 生物遭受精神伤害 */
    public static void handleRationally(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (isRationality(entity)) return;
        incrementSpiritValue(entity, -event.getNewDamage());
    }
}
