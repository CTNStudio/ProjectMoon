package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static ctn.project_moon.events.PlayerEvents.DEFAULT_SPIRIT_VALUE;
import static ctn.project_moon.events.PlayerEvents.getSpiritValue;

public class LivingEvent {
    public static void damage(LivingIncomingDamageEvent event){
        CompoundTag npt = event.getEntity().getPersistentData();
        if (npt.contains("Spirit")) {
            event.getEntity().sendSystemMessage(Component.literal("我当前的精神值为" + getSpiritValue(npt)));
        }
    }
}
