package ctn.project_moon.api;

import ctn.project_moon.init.PmEntityAttributes;
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

    public static void addFourColorAttribute(LivingEntity entity) {
        CompoundTag nbt = entity.getPersistentData();
        if (!nbt.contains(BASE_FORTITUDE)) setFortitude(entity, 20);//TODO:无用
        if (!nbt.contains(BASE_PRUDENCE)) setPrudence(entity, 20);//TODO:无用
        if (!nbt.contains(BASE_TEMPERANCE)) setBaseTemperance(entity, 100);
        if (!nbt.contains(BASE_JUSTICE)) setBaseJustice(entity, 100);
    }

    public static void defaultValue(LivingEntity entity){
        CompoundTag nbt = entity.getPersistentData();
        setFortitude(entity, 20);
        setPrudence(entity, 20);
        setBaseTemperance(entity, 1);
        setBaseJustice(entity, 1);
    }

    /**
     *获取综合评级
     */
    public static int getCompositeRatting(LivingEntity entity){
        int wholeLevel;
        wholeLevel = getFortitudeLevel(entity) + getPrudenceLevel(entity) + getTemperanceLevel(entity) + getJusticeLevel(entity);
        if(wholeLevel < 4){return 1;}
        if(wholeLevel < 15){return wholeLevel / 3;}
        if(wholeLevel == 15){return 4;}
        return 5;
    }
    public static void renewPlayerCompositeRatting(LivingEntity entity){
        Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.COMPOSITE_RATING)).setBaseValue(getCompositeRatting(entity));
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
    /** 更新四色属性 */
    public static void renewFourColorAttribute(ServerPlayer player) {
        renewFortitude(player);
        renewPrudence(player);
        renewTemperanceAttribute(player);
        renewJusticeAttribute(player);
    }

    //勇气
    public static double getBaseFortitude(LivingEntity entity){
        return entity.getPersistentData().getDouble(BASE_FORTITUDE);
    }
    public static double getFortitude(LivingEntity entity){
        double result = getBaseFortitude(entity);
        if(entity.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL) != null){
            result += Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL)).getValue();
        }
        return result;
    }
    //获取勇气等级
    public static int getFortitudeLevel(LivingEntity entity){
        double fortitude = getFortitude(entity);
        return getColorLevel(fortitude);
    }
    public static void setFortitude(LivingEntity entity, int value){
        entity.getPersistentData().putInt(BASE_FORTITUDE, value);
    }
    //勇气加血(仅计算额外加成)
    public static void renewFortitude(ServerPlayer player){
        if(player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL) != null){
            double addMaxHealth = Objects.requireNonNull(player.getAttribute(PmEntityAttributes.FORTITUDE_ADDITIONAL)).getValue();
            AttributeModifier addMaxHealthModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fortitude"),
                     addMaxHealth, AttributeModifier.Operation.ADD_VALUE);
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).addOrUpdateTransientModifier(addMaxHealthModifier);
        }
    }

    //谨慎
    public static double getBasePrudence(LivingEntity entity){
        return entity.getPersistentData().getDouble(BASE_PRUDENCE);
    }
    public static double getPrudence(LivingEntity entity){
        double result = getBasePrudence(entity);
        if(entity.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL) != null){
            result += Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL)).getValue();
        }
        return result;
    }
    //获取谨慎等级
    public static int getPrudenceLevel(LivingEntity entity){
        double prudence = getPrudence(entity);
        return getColorLevel(prudence);
    }
    public static void setPrudence(LivingEntity entity, int value){
        entity.getPersistentData().putInt(BASE_PRUDENCE, value);
    }
    public static void renewPrudence(ServerPlayer player){
        if(player.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL) != null){
            double addMaxSpirit = Objects.requireNonNull(player.getAttribute(PmEntityAttributes.PRUDENCE_ADDITIONAL)).getValue();
            AttributeModifier addMaxSpiritModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prudence"),
                    addMaxSpirit, AttributeModifier.Operation.ADD_VALUE);
            Objects.requireNonNull(player.getAttribute(PmEntityAttributes.MAX_SPIRIT)).addOrUpdateTransientModifier(addMaxSpiritModifier);
        }
    }

    //自律
    //TODO:自律相关（目前只有加挖掘速度)
    public static double getBaseTemperance(LivingEntity entity){
        return entity.getPersistentData().getDouble(BASE_TEMPERANCE);
    }
    public static double getTemperance(LivingEntity entity){
        double result = getBaseTemperance(entity);
        if(entity.getAttribute(PmEntityAttributes.TEMPERANCE_ADDITIONAL) != null){
            result += Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.TEMPERANCE_ADDITIONAL)).getValue();
        }
        return result;
    }
    //获取自律等级
    public static int getTemperanceLevel(LivingEntity entity){
        double temperance = getTemperance(entity);
        return getColorLevel(temperance);
    }
    public static void setBaseTemperance(LivingEntity entity, int value){
        entity.getPersistentData().putInt(BASE_TEMPERANCE, value);
        if (entity instanceof ServerPlayer player){
            renewTemperanceAttribute(player);
        }
    }
    public static void addBaseTemperance(LivingEntity entity, int addValue){
        setBaseTemperance(entity, (int) getBaseTemperance(entity) + addValue);
    }
    //更新自律对玩家属性的加成(附加值更改时也记得调用)
    public static void renewTemperanceAttribute(ServerPlayer player){
        //获取玩家的自律值
        double temperance = getTemperance(player);
        //创建自律加成
        AttributeModifier blockBreakSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"),
                temperance * 0.02, AttributeModifier.Operation.ADD_VALUE);
        //将自律加成添加到玩家的移动速度属性中
        Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED)).addOrUpdateTransientModifier(blockBreakSpeedModifier);
    }

    //正义
    public static double getBaseJustice(LivingEntity entity){
        return entity.getPersistentData().getDouble(BASE_JUSTICE);
    }
    public static double getJustice(LivingEntity entity){
        double result = getBaseJustice(entity);
        if(entity.getAttribute(PmEntityAttributes.JUSTICE_ADDITIONAL) != null){
            result += Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.JUSTICE_ADDITIONAL)).getValue();
        }
        return result;
    }
    //获取正义等级
    public static int getJusticeLevel(LivingEntity entity){
        double justice = getJustice(entity);
        return getColorLevel(justice);
    }
    public static void setBaseJustice(LivingEntity entity, int value){
        entity.getPersistentData().putInt(BASE_JUSTICE, value);
        if (entity instanceof ServerPlayer player){
            renewJusticeAttribute(player);
        }
    }
    public static void addBaseJustice(LivingEntity entity, int addValue){
        setBaseJustice(entity, (int) getBaseJustice(entity) + addValue);
    }
    //更新正义对玩家属性的加成(附加值更改时也记得调用)
    public static void renewJusticeAttribute(ServerPlayer player){
        //获取玩家的正义值
        double justice = getJustice(player);
        //创建正义加成
        AttributeModifier movementSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"),
                justice * 0.001, AttributeModifier.Operation.ADD_VALUE);
        AttributeModifier attackSpeedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed"),
                justice * 0.01, AttributeModifier.Operation.ADD_VALUE);
        //将正义加成添加到玩家的移动与攻击速度属性中
        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addOrUpdateTransientModifier(movementSpeedModifier);
        Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).addOrUpdateTransientModifier(attackSpeedModifier);
    }

    //事件处理方法
    public static void fortitudeRelated(Player player){
        if(player instanceof ServerPlayer){
            player.getPersistentData().putInt(BASE_FORTITUDE, (int) Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue());
        }
    }
    public static void prudenceRelated(Player player){
        if(player instanceof ServerPlayer){
            setPrudence(player, (int) SpiritAttribute.getMaxSpiritValue(player));
        }
    }
    public static void temperanceRelated(Player player){
        if(player instanceof ServerPlayer ){
            if (!player.getPersistentData().contains(BASE_TEMPERANCE))
                setBaseTemperance(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
            if(!Objects.requireNonNull(player.getAttribute(Attributes.BLOCK_BREAK_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"temperance_add_block_break_speed"))){
                renewTemperanceAttribute((ServerPlayer) player);
            }
        }
    }
    public static void justiceRelated(Player player){
        if(player instanceof ServerPlayer){
            if(!player.getPersistentData().contains(BASE_JUSTICE))
                setBaseJustice(player, 1);//TODO:此处先于之前的初始化，故在此初始化,看看是否需要修改
            if(!Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_attack_speed")) ||
                    !Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID,"justice_add_movement_speed"))){
                renewJusticeAttribute((ServerPlayer) player);
            }
        }
    }
}
