package ctn.project_moon.events.entity.player;

import ctn.project_moon.api.TempNbtAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.FourColorAttribute.*;
import static ctn.project_moon.api.PlayerAttribute.processAttribute;
import static ctn.project_moon.api.PlayerAttribute.resetAttribute;
import static ctn.project_moon.api.SpiritAttribute.syncSpiritValue;

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
		processAttribute(event.getEntity());
	}

	/**
	 * 加载玩家属性
	 */
	@SubscribeEvent
	public static void loading(PlayerEvent.LoadFromFile event) {
		loadAttribute(event.getEntity());
	}

	@SubscribeEvent
	public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncSpiritValue(player);
			syncFourColorAttribute(player);
		}
	}

	/**
	 * 玩家重生或维度切换后
	 */
	@SubscribeEvent
	public static void reset(PlayerEvent.Clone event) {
		Player player = event.getEntity();
		if (event.isWasDeath()) {
			resetAttribute(player);
		}
		// 重置临时属性
		TempNbtAttribute.resetTemporaryAttribute(player);
	}

	public static void loadAttribute(Player player) {
		processAttribute(player);
		TempNbtAttribute.resetTemporaryAttribute(player);
	}

	@SubscribeEvent
	public static void tick(PlayerTickEvent.Pre event) {
		Player player = event.getEntity();
		CompoundTag nbt = player.getPersistentData();
		if (player instanceof ServerPlayer serverPlayer) {
			syncSpiritValue(serverPlayer);
			//同步属性
			syncFourColorAttribute(player);
			fortitudeRelated(player);
			prudenceRelated(player);
			temperanceRelated(player);
			justiceRelated(player);
			renewPlayerCompositeRatting(player);
		}
	}
}
