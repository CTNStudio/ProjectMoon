package ctn.project_moon.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.FourColorAttribute.addFourColorAttribute;
import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_VALUE;
import static ctn.project_moon.api.MobGeneralAttribute.addSpiritAttribute;

public class PlayerAttribute {
	public static final String PREFIX = MOD_ID + ":player_attr.";
	/** 勇气点数 类型Int */
	public static final String BASE_FORTITUDE = PREFIX + "fortitude";
	/** 谨慎点数 类型Int */
	public static final String BASE_PRUDENCE = PREFIX + "prudence";
	/**
	 * 自律点数 类型Int
	 */
	public static final String BASE_TEMPERANCE = PREFIX + "temperance";
	/** 正义点数 类型Int */
	public static final String BASE_JUSTICE = PREFIX + "justice";

	/** 玩家死亡重置属性 */
	public static void resetAttribute(LivingEntity entity) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(SPIRIT_VALUE, 0);
	}

	/** 玩家添加/保存属性信息 */
	public static void processAttribute(LivingEntity entity) {
		addSpiritAttribute(entity);
		addFourColorAttribute(entity);
	}
}
