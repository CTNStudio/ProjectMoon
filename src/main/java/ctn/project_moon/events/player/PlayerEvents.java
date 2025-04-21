package ctn.project_moon.events.player;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.api.SpiritApi;
import ctn.project_moon.events.StopUsingItemEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.player.PlayerAnimEvents.*;
import static ctn.project_moon.events.player.PlayerAnimEvents.restorePlayerSpeed;

/**
 * 玩家相关事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class PlayerEvents {
    /**
     * 保存玩家属性
     */
    @SubscribeEvent
    public static void save(PlayerEvent.SaveToFile event) {
        SpiritApi.processAttributeInformation(event.getEntity());
    }

    /**
     * 加载玩家属性
     */
    @SubscribeEvent
    public static void loading(PlayerEvent.LoadFromFile event) {
        Player player = event.getEntity();
        SpiritApi.processAttributeInformation(player);
        TemporaryAttribute.resetTemporaryAttribute(player);
    }

    /**
     * 玩家死亡后重置
     */
    @SubscribeEvent
    public static void reset(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        SpiritApi.resetSpiritValue(player);
        TemporaryAttribute.resetTemporaryAttribute(player);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event){
//        if ()
    }

    @SubscribeEvent
    public static void stopUsingItemEvent(StopUsingItemEvent event){
        Player player = event.getEntity();
//        if (player.getWeaponItem()!=null){
//            return;
//        }
        completeAttack(player);
        cancelAnimationLayer(player);
        restoreItemTick(player);
        restorePlayerSpeed(player);
    }
}
