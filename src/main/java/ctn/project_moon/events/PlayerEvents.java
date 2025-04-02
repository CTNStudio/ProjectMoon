package ctn.project_moon.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.*;

@EventBusSubscriber(modid = MOD_ID)
public class PlayerEvents {
    /** 保存玩家属性 */
    @SubscribeEvent
    public static void saveToAttribute(PlayerEvent.SaveToFile event) {
        processAttributeInformation(event);
    }

    /** 加载玩家属性 */
    @SubscribeEvent
    public static void loadFromAttribute(PlayerEvent.LoadFromFile event) {
        processAttributeInformation(event);
    }

    private static void processAttributeInformation(PlayerEvent event) {
        CompoundTag npt = event.getEntity().getPersistentData();
        if (!npt.contains(SPIRIT)) {
            npt.putFloat(SPIRIT, DEFAULT_SPIRIT_VALUE);
        }
        if (!npt.contains(MAX_SPIRIT)) {
            npt.putFloat(MAX_SPIRIT, DEFAULT_MAX_SPIRIT_VALUE);
        }
        if (!npt.contains(MIN_SPIRIT)) {
            npt.putFloat(MIN_SPIRIT, DEFAULT_MIN_SPIRIT_VALUE);
        }
        npt.putFloat(SPIRIT, getSpiritValue(npt));
        npt.putFloat(MAX_SPIRIT, getMaxSpiritValue(npt));
        npt.putFloat(MIN_SPIRIT, getMinSpiritValue(npt));
    }

    /** 玩家死亡后重置精神值 */
    @SubscribeEvent
    public static void resetSpiritValue(PlayerEvent.PlayerRespawnEvent event) {
        CompoundTag npt = event.getEntity().getPersistentData();
        npt.putFloat(SPIRIT, DEFAULT_SPIRIT_VALUE);
    }


    private static int timepiece;
    @SubscribeEvent
    public static void refreshSpiritValue(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getPersistentData().contains(SPIRIT)) {
            timepiece++;
            if (timepiece < 20) {
                return;
            }
            timepiece = 0;
            if (getSpiritValue(livingEntity) < 0) {
                return;
            }
            updateSpiritValue(livingEntity, 1);
            livingEntity.sendSystemMessage(Component.literal("我当前的精神值为" + getSpiritValue(livingEntity)));
        }
    }
}
