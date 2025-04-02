package ctn.project_moon.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {

    /**
     * 预留给受伤类型判断
     */
    @SubscribeEvent
    public static void LivingDamageEvents(LivingDamageEvent.Pre event) {
    }

    /**
     * 预留给死亡类型判断
     */
    @SubscribeEvent
    public static void LivingDeathEvent(LivingDeathEvent event) {

    }
}
