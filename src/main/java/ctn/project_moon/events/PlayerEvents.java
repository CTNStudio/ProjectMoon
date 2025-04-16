package ctn.project_moon.events;

import ctn.project_moon.config.PmConfig;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.*;

/**
 * 玩家相关事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class PlayerEvents {
    /**
     * 保存玩家属性
     */
    @SubscribeEvent
    public static void saveToAttribute(PlayerEvent.SaveToFile event) {
        processAttributeInformation(event.getEntity());
    }

    /**
     * 加载玩家属性
     */
    @SubscribeEvent
    public static void loadFromAttribute(PlayerEvent.LoadFromFile event) {
        processAttributeInformation(event.getEntity());
    }

    /**
     * 玩家死亡后重置精神值
     */
    @SubscribeEvent
    public static void resetSpiritValue(PlayerEvent.PlayerRespawnEvent event) {
        SpiritEvents.resetSpiritValue(event.getEntity());
    }
}
