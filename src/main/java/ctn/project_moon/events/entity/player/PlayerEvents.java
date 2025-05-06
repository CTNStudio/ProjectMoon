package ctn.project_moon.events.entity.player;

import ctn.project_moon.api.TempNbtAttr;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PlayerAttr.processAttr;
import static ctn.project_moon.api.PlayerAttr.resetAttr;
import static ctn.project_moon.api.SpiritAttr.syncSpiritValue;

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
        processAttr(event.getEntity());
    }

    /**
     * 加载玩家属性
     */
    @SubscribeEvent
    public static void loading(PlayerEvent.LoadFromFile event) {
        loadAttributes(event.getEntity());
    }

    @SubscribeEvent
    public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncSpiritValue(player);
        }
    }

    /**
     * 玩家重生或维度切换后
     */
    @SubscribeEvent
    public static void reset(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if (event.isWasDeath()){
            resetAttr(player);
        }
        // 重置临时属性
        TempNbtAttr.resetTemporaryAttribute(player);
    }

    public static void loadAttributes(Player player){
        processAttr(player);
        TempNbtAttr.resetTemporaryAttribute(player);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event){
        Player player = event.getEntity();
        CompoundTag nbt = player.getPersistentData();
        if (player instanceof ServerPlayer serverPlayer) {
            syncSpiritValue(serverPlayer);
        }
    }
}
