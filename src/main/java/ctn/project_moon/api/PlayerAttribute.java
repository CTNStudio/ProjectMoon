package ctn.project_moon.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_VALUE;
import static ctn.project_moon.api.MobGeneralAttribute.addSpiritAttribute;

public class PlayerAttribute {
    private static final String PREFIX = MOD_ID + ":player_attr.";
    /** 勇气点数 类型Int */
    public static final String FORTITUDE = PREFIX + "fortitude";
    /** 谨慎点数 类型Int */
    public static final String PRUDENCE = PREFIX + "prudence";
    /**
     * 自律点数 类型Int
     */
    public static final String TEMPERANCE = PREFIX + "temperance";
    /** 正义点数 类型Int */
    public static final String JUSTICE = PREFIX + "justice";

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

    public static void addFourColorAttribute(LivingEntity entity) {
        CompoundTag nbt = entity.getPersistentData();
        if (!nbt.contains(FORTITUDE)) nbt.putInt(FORTITUDE, 20);
        if (!nbt.contains(PRUDENCE)) nbt.putInt(PRUDENCE, 20);
        if (!nbt.contains(TEMPERANCE)) nbt.putInt(TEMPERANCE, 1);
        if (!nbt.contains(JUSTICE)) nbt.putInt(JUSTICE, 1);
    }

    public static void defaultValue(LivingEntity entity){
        CompoundTag nbt = entity.getPersistentData();
        nbt.putInt(FORTITUDE, 20);
        nbt.putInt(PRUDENCE, 20);
        nbt.putInt(TEMPERANCE, 1);
        nbt.putInt(JUSTICE, 1);
    }
}
