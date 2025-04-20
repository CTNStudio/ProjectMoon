package ctn.project_moon.events.player;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.events.SpiritEvents;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.*;
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
        SpiritEvents.processAttributeInformation(event.getEntity());
    }

    /**
     * 加载玩家属性
     */
    @SubscribeEvent
    public static void loading(PlayerEvent.LoadFromFile event) {
        Player player = event.getEntity();
        SpiritEvents.processAttributeInformation(player);
        TemporaryAttribute.resetTemporaryAttribute(player);
    }

    /**
     * 玩家死亡后重置
     */
    @SubscribeEvent
    public static void reset(PlayerEvent.PlayerRespawnEvent event) {
        Player player = getEntity(event);
        SpiritEvents.resetSpiritValue(player);
        TemporaryAttribute.resetTemporaryAttribute(player);
    }

    private static @NotNull Player getEntity(PlayerEvent.PlayerRespawnEvent event) {
        return event.getEntity();
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event){
        Player player = event.getEntity();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (minecraft.player.input.jumping) {
                cancelAnimation(player);
                restorePlayerSpeed(player);
                return;
            }
        }
        CompoundTag nbt = player.getPersistentData();
        if (!(player.getWeaponItem().getItem() instanceof AnimAttackItem item)) {
            cancelAnimation(player);
            restorePlayerSpeed(player);
            return;
        }
        if (!nbt.getBoolean(TemporaryAttribute.PLAYER_SPECIAL_WEAPON_ATTACK)) {
            return;
        }
        nbt.putInt(TemporaryAttribute.PLAYER_USE_ITEM_TICK, nbt.getInt(TemporaryAttribute.PLAYER_USE_ITEM_TICK) + 1);
        if (nbt.getInt(TemporaryAttribute.PLAYER_USE_ITEM_TICK) >= item.triggerTick()) {
            item.trigger(player.level(), player, player.getUseItem());
            restorePlayerSpeed(player);
        }
    }

    public static void cancelAnimation(Player player) {
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(0);
        }
    }
}
