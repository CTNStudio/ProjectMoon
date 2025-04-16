package ctn.project_moon.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 *
 */
@EventBusSubscriber(modid = MOD_ID)
public class LivingEvent {
    @SubscribeEvent
    public static void damage(LivingIncomingDamageEvent event) {
//        CompoundTag nbt = event.getEntity().getPersistentData();
//        if (nbt.contains(SPIRIT_VALUE)) {
//            event.getEntity().sendSystemMessage(Component.literal("我当前的精神值为" + getSpiritValue(nbt)));
//        }
    }
}
