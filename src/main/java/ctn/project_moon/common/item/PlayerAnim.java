package ctn.project_moon.common.item;

import com.zigythebird.playeranimatorapi.API.PlayerAnimAPI;
import com.zigythebird.playeranimatorapi.API.PlayerAnimAPIClient;
import com.zigythebird.playeranimatorapi.data.PlayerParts;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.TempNbtAttribute.PLAYER_ATTACK;
import static ctn.project_moon.api.TempNbtAttribute.PLAYER_USE_ITEM;

public interface PlayerAnim {
	/** 强制打断 */
	void forcedInterruption(Level level, Player player);

	/**
	 * 停止动画
	 */
	static void stopAnimation(Level level, Player player, String animName) {
		if (level instanceof ServerLevel serverLevel) {
			PlayerAnimAPI.stopPlayerAnim(serverLevel, player, getAnimationID(animName));
			return;
		}
		if (player instanceof AbstractClientPlayer clientPlayer) {
			PlayerAnimAPIClient.stopPlayerAnim(clientPlayer, getAnimationID(animName));
		}
	}

	/**
	 * 播放动画
	 */
	static void playAnimation(Level level, Player player, String animName) {
		if (level instanceof ServerLevel serverLevel) {
			PlayerAnimAPI.playPlayerAnim(serverLevel, player, getAnimationID(animName));
			return;
		}
		if (player instanceof AbstractClientPlayer clientPlayer) {
			PlayerAnimAPIClient.playPlayerAnim(clientPlayer, getAnimationID(animName));
		}
	}

	/** 进入攻击状态 */
	default void enterAttackState(Level level, Player player) {
		enterAttackState(level, player, null);
	}

	/**
	 * 进入攻击状态并播放动画
	 */
	default void enterAttackState(Level level, Player player, @Nullable String attackAnimName) {
		CompoundTag nbt = player.getPersistentData();
		nbt.putBoolean(PLAYER_USE_ITEM, true);
		nbt.putBoolean(PLAYER_ATTACK, true);
		if (attackAnimName == null) {
			return;
		}
		if (level instanceof ServerLevel serverLevel) {
			PlayerAnimAPI.playPlayerAnim(serverLevel, player, getAnimationID(attackAnimName), PlayerParts.allExceptHeadRot(), null, 2000);
		}
	}

	static @NotNull ResourceLocation getAnimationID(String animName) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, animName);
	}
}
