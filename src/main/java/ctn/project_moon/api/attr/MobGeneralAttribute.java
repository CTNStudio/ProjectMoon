package ctn.project_moon.api.attr;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import static ctn.project_moon.PmMain.MOD_ID;

public class MobGeneralAttribute {
	private static final String PREFIX                    = MOD_ID + ":mob_attr.";
	/**
	 * 理智值 类型 Double
	 */
	public static final  String RATIONALITY_VALUE         = PREFIX + "entity.rationality_value";
	/**
	 * 理智恢复计时 类型 Int
	 */
	public static final  String RATIONALITY_RECOVERY_TICK = PREFIX + "entity.rationality_recovery_tick";
	/**
	 * 上次受伤时间 类型 Int
	 */
	public static final  String INJURY_TICK               = PREFIX + "entity.injury_tick";
	
	/**
	 * 添加理智相关属性
	 */
	public static void addRationalityAttribute(LivingEntity entity) {
		CompoundTag nbt = entity.getPersistentData();
		if (!nbt.contains(RATIONALITY_VALUE)) nbt.putDouble(RATIONALITY_VALUE, 0);
		if (!nbt.contains(RATIONALITY_RECOVERY_TICK)) nbt.putInt(RATIONALITY_RECOVERY_TICK, 0);
		if (!nbt.contains(INJURY_TICK)) nbt.putInt(INJURY_TICK, 0);
	}
}
