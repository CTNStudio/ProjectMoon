package ctn.project_moon.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/** 实体事件 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityDamageAndDeathEvents {

    /** 事件 */
    @EventBusSubscriber(modid = MOD_ID)
    public static class DamageAndDeathEvents {

        /**
         * 受伤前
         */
        @SubscribeEvent
        public static void damageEvents(LivingDamageEvent.Pre event) {

        }

        /**
         * 死亡
         */
        @SubscribeEvent
        public static void deathEvent(LivingDeathEvent event) {

        }
    }
}
