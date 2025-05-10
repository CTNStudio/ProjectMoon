package ctn.project_moon.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

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
        if (!nbt.contains(FORTITUDE)) setFortitude(entity, 20);
        if (!nbt.contains(PRUDENCE)) setPrudence(entity, 20);
        if (!nbt.contains(TEMPERANCE)) setTemperance(entity, 100);
        if (!nbt.contains(JUSTICE)) setJustice(entity, 100);
    }

    public static void defaultValue(LivingEntity entity){
        CompoundTag nbt = entity.getPersistentData();
        setFortitude(entity, 20);
        setPrudence(entity, 20);
        setTemperance(entity, 1);
        setJustice(entity, 1);
    }

    /**
     *获取玩家员工等级
     */
    public static int getEmployeeLevel(LivingEntity entity){
        int wholeLevel;
        wholeLevel = getFortitudeLevel(entity) + getPrudenceLevel(entity) + getTemperanceLevel(entity) + getJusticeLevel(entity);
        if(wholeLevel < 15){return wholeLevel / 3;}
        if(wholeLevel == 15){return 4;}
        return 5;
    }

    /**
     *等级计算方法
     */
    private static int getColorLevel(double value){
        if (value < 30){return 1;}
        if (value < 45){return 2;}
        if (value < 65){return 3;}
        if (value < 85){return 4;}
        if (value <= 100){return 5;}
        return 6;
    }

    //勇气
    public static double getFortitude(LivingEntity entity){
        return entity.getPersistentData().getDouble(FORTITUDE);
    }
    //获取勇气等级
    public static int getFortitudeLevel(LivingEntity entity){
        double fortitude = getFortitude(entity);
        return getColorLevel(fortitude);
    }
    public static void setFortitude(LivingEntity entity, int value){
        entity.getPersistentData().putInt(FORTITUDE, value);
    }

    //谨慎
    public static double getPrudence(LivingEntity entity){
        return entity.getPersistentData().getDouble(PRUDENCE);
    }
    //获取谨慎等级
    public static int getPrudenceLevel(LivingEntity entity){
        double prudence = getPrudence(entity);
        return getColorLevel(prudence);
    }
    public static void setPrudence(LivingEntity entity, int value){
        entity.getPersistentData().putInt(PRUDENCE, value);
    }

    //自律
    //TODO:自律相关（目前只有加挖掘速度)
    public static double getTemperance(LivingEntity entity){
        return entity.getPersistentData().getDouble(TEMPERANCE);
    }
    //获取自律等级
    public static int getTemperanceLevel(LivingEntity entity){
        double temperance = getTemperance(entity);
        return getColorLevel(temperance);
    }
    public static void setTemperance(LivingEntity entity, int value){
        entity.getPersistentData().putInt(TEMPERANCE, value);
        if (entity instanceof ServerPlayer player){
            renewTemperanceAttribute(player);
        }
    }
    public static void addTemperance(LivingEntity entity, int addValue){
        setTemperance(entity, (int)getTemperance(entity) + addValue);
    }
    //更新自律对玩家属性的加成
    public static void renewTemperanceAttribute(ServerPlayer player){
        double temperance = getTemperance(player);
        if (temperance < 0.0){
            temperance = 0.0;
        }
        if (Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"))){
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"));
        }
        AttributeModifier blockBreakSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"),
                temperance * 0.02, AttributeModifier.Operation.ADD_VALUE);
        Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED)).addTransientModifier(blockBreakSpeedModifier);
    }

    //正义
    public static double getJustice(LivingEntity entity){
        return entity.getPersistentData().getDouble(JUSTICE);
    }
    //获取正义等级
    public static int getJusticeLevel(LivingEntity entity){
        double justice = getJustice(entity);
        return getColorLevel(justice);
    }
    public static void setJustice(LivingEntity entity, int value){
        entity.getPersistentData().putInt(JUSTICE, value);
        if (entity instanceof ServerPlayer player){
            renewJusticeAttribute(player);
        }
    }
    public static void addJustice(LivingEntity entity, int addValue){
        setJustice(entity, (int)getJustice(entity) + addValue);
    }
    //更新正义对玩家属性的加成
    public static void renewJusticeAttribute(ServerPlayer player){
        double justice = getJustice(player);
        if (justice < 0.0){
            justice = 0.0;
        }
        if (Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"))){
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"));
        }
        if (Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed"))){
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).removeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed"));
        }
        AttributeModifier movementSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"),
                justice * 0.001, AttributeModifier.Operation.ADD_VALUE);
        AttributeModifier attackSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed"),
                justice * 0.01, AttributeModifier.Operation.ADD_VALUE);
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(movementSpeedModifier);
        Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).addTransientModifier(attackSpeedModifier);
    }

    //事件处理方法
    public static void fortitudeRelated(Player player){
        if(player instanceof ServerPlayer){
            player.getPersistentData().putInt(FORTITUDE, (int) Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue());
        }
    }
    public static void prudenceRelated(Player player){
        if(player instanceof ServerPlayer){
            setPrudence(player, (int) SpiritAttribute.getMaxSpiritValue(player));
        }
    }
    public static void temperanceRelated(Player player){
        if(player instanceof ServerPlayer ){
            if (!player.getPersistentData().contains(TEMPERANCE))
                setTemperance(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
            if(!Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"))){
                renewTemperanceAttribute((ServerPlayer) player);
            }
        }
    }
    public static void justiceRelated(Player player){
        if(player instanceof ServerPlayer){
            if(!player.getPersistentData().contains(JUSTICE))
                setJustice(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
            if(!Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed")) ||
                    !Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"))){
                renewJusticeAttribute((ServerPlayer) player);
            }
        }
    }
}
