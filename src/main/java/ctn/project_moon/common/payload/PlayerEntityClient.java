package ctn.project_moon.common.payload;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/** 本地客户端数据 */
@OnlyIn(Dist.CLIENT)
public class PlayerEntityClient {
	public static float spiritValue = 0;
	public static float maxSpiritBalue = 20;

	public static boolean isPlayerUseItem = false;
	public static boolean isPlayerAttack = false;
	public static boolean isPlayerSwitchItems = true;
	public static boolean isPlayerRotatingPerspective = true;
	public static boolean isPlayerMoved = true;
	public static int playerUseItemTick = 0;
	public static int playerUseTick = 0;
}
