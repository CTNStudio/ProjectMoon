package ctn.project_moon.events.entity.player;

import ctn.project_moon.common.payload.data.TempNbtAttrData;
import ctn.project_moon.tool.SpiritTool;
import ctn.project_moon.api.TempNbtAttr;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static ctn.project_moon.PmMain.MOD_ID;

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
        loadAttributes(event.getEntity());
    }

    /**
     * 玩家重生或维度切换后重置临时属性
     */
    @SubscribeEvent
    public static void reset(PlayerEvent.Clone event) {
        resetAfterDeath(event.getEntity());
    }

    public static void resetAfterDeath(Player player) {
        SpiritTool.resetSpiritValue(player);
        TempNbtAttr.resetTemporaryAttribute(player);
    }

    public static void loadAttributes(Player player){
        SpiritTool.processAttributeInformation(player);
        TempNbtAttr.resetTemporaryAttribute(player);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event){}
}
