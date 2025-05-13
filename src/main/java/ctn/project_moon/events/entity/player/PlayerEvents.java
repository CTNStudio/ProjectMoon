package ctn.project_moon.events.entity.player;

import ctn.project_moon.api.TempNbtAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
	 * 加载玩家-此时客户端玩家未创建
	 */
	@SubscribeEvent
	public static void loading(PlayerEvent.LoadFromFile event) {
		Player player = event.getEntity();
		TempNbtAttribute.resetTemporaryAttribute(player);
		processAttribute(player);
	}

	/** 登录到世界-此时客户端玩家已创建 */
	@SubscribeEvent
	public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncSpiritValue(player);
			syncFourColorAttribute(player);
			prudenceRelated(player);
			temperanceRelated(player);
			justiceRelated(player);
		}
	}

	/**
	 * 玩家重生或维度切换后
	 */
	@SubscribeEvent
	public static void reset(PlayerEvent.Clone event) {
		Player player = event.getEntity();
		TempNbtAttribute.resetTemporaryAttribute(player);

		// 如果玩家是因为死亡...
		if (event.isWasDeath()) {
			syncFourColorAttribute(player);
			resetAttribute(player);
			setBasePrudence(player, getBasePrudence(event.getOriginal()));
			setBaseTemperance(player, getBaseTemperance(event.getOriginal()));
			setBaseJustice(player, getBaseJustice(event.getOriginal()));
			renewFourColorAttribute(player);
		}
		// 重置临时属性
	}

	@SubscribeEvent
	public static void tick(PlayerTickEvent.Pre event) {
		Player player = event.getEntity();
		CompoundTag nbt = player.getPersistentData();
		if (player instanceof ServerPlayer serverPlayer) {
			syncSpiritValue(serverPlayer);
			//同步属性

			//TODO：改事件
			if (getFortitude(player) != (int) player.getAttributeValue(Attributes.MAX_HEALTH)) {
				fortitudeRelated(player);
			}

			// TODO 不应该在这更新
			renewPlayerCompositeRatting(player);
		}
	}
}
