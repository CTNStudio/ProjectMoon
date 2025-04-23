package ctn.project_moon.events.player;

import ctn.project_moon.tool.PlayerAnimTool;
import ctn.project_moon.tool.PmTool;
import ctn.project_moon.tool.SpiritTool;
import ctn.project_moon.api.TemporaryNbtAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.TemporaryNbtAttribute.*;

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
        SpiritTool.processAttributeInformation(event.getEntity());
    }

    /**
     * 加载玩家属性
     */
    @SubscribeEvent
    public static void loading(PlayerEvent.LoadFromFile event) {
        Player player = event.getEntity();
        SpiritTool.processAttributeInformation(player);
        TemporaryNbtAttribute.resetTemporaryAttribute(player);
    }

    /**
     * 玩家死亡后重置
     */
    @SubscribeEvent
    public static void reset(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        SpiritTool.resetSpiritValue(player);
        TemporaryNbtAttribute.resetTemporaryAttribute(player);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event){
    }

    @SubscribeEvent
    public static void stopUsingItemEvent(StopUsingItemEvent event){
        Player player = event.getEntity();
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(IS_PLAYER_USE_ITEM, false);
        nbt.putBoolean(IS_PLAYER_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        PmTool.incrementNbt(nbt, PLAYER_USE_TICK, 0);
        PlayerAnimTool.cancelAnimationLayer(player);
        PlayerAnimTool.restorePlayerSpeed(player);
    }
}
