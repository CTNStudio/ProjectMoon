package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.*;

/**
 * 玩家相关事件
 */
public class PlayerEvents {

    /**
     * 玩家属性相关事件
     */
    @EventBusSubscriber(modid = MOD_ID)
    public static class PlayerAttribute {
        /**
         * 保存玩家属性
         */
        @SubscribeEvent
        public static void saveToAttribute(PlayerEvent.SaveToFile event) {
            processAttributeInformation(event);
        }

        /**
         * 加载玩家属性
         */
        @SubscribeEvent
        public static void loadFromAttribute(PlayerEvent.LoadFromFile event) {
            processAttributeInformation(event);
        }

        /**
         * 玩家死亡后重置精神值
         */
        @SubscribeEvent
        public static void resetSpiritValue(PlayerEvent.PlayerRespawnEvent event) {
            CompoundTag nbt = event.getEntity().getPersistentData();
            nbt.putFloat(SPIRIT, DEFAULT_SPIRIT_VALUE);
        }

        private static void processAttributeInformation(PlayerEvent event) {
            CompoundTag nbt = event.getEntity().getPersistentData();

            if (!nbt.contains(SPIRIT)) {
                nbt.putFloat(SPIRIT, DEFAULT_SPIRIT_VALUE);
            }
            if (!nbt.contains(MAX_SPIRIT)) {
                nbt.putFloat(MAX_SPIRIT, DEFAULT_MAX_SPIRIT_VALUE);
            }
            if (!nbt.contains(MIN_SPIRIT)) {
                nbt.putFloat(MIN_SPIRIT, DEFAULT_MIN_SPIRIT_VALUE);
            }
            nbt.putFloat(SPIRIT, getSpiritValue(nbt));
            nbt.putFloat(MAX_SPIRIT, getMaxSpiritValue(nbt));
            nbt.putFloat(MIN_SPIRIT, getMinSpiritValue(nbt));
        }
    }

    private static int timepiece;

    /**
     * 刷新精神值
     */
    @SubscribeEvent
    public static void refreshSpiritValue(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getPersistentData().contains(SPIRIT)) {
            timepiece++;
            if (timepiece < 20) {
                return;
            }
            timepiece = 0;
            if (getSpiritValue(livingEntity) < 0) {
            }
//            updateSpiritValue(livingEntity, 1);
        }
    }
}
