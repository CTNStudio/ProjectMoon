package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.SPIRIT;
import static ctn.project_moon.events.SpiritEvents.getSpiritValue;

/**
 *
 */
@EventBusSubscriber(modid = MOD_ID)
public class LivingEvent {
    @SubscribeEvent
    public static void damage(LivingIncomingDamageEvent event) {
//        CompoundTag nbt = event.getEntity().getPersistentData();
//        if (nbt.contains(SPIRIT)) {
//            event.getEntity().sendSystemMessage(Component.literal("我当前的精神值为" + getSpiritValue(nbt)));
//        }
    }
}
