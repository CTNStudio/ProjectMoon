package ctn.project_moon.events.entity;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.datagen.PmTags;
import ctn.project_moon.events.ArmorAbsorptionEvent;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.*;
import static ctn.project_moon.api.GradeType.damageMultiple;
import static ctn.project_moon.common.item.Ego.getItemLevelValue;
import static ctn.project_moon.common.item.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.common.item.weapon.ego.CloseCombatEgo.isCloseCombatEgo;
import static ctn.project_moon.init.PmAttributes.*;
import static ctn.project_moon.init.PmDamageTypes.Types.getType;

@EventBusSubscriber(modid = MOD_ID)
public class ArmorResistanceEvents {
    /** ！-> 原版护甲处理前 ->已处理完（防御等等） */
    @SubscribeEvent
    public static void armorAbsorptionEvent(ArmorAbsorptionEvent.Pre event){
        DamageSource damageSource = event.getDamageSource();
        ItemStack itemStack = damageSource.getWeaponItem(); // 获取伤害来源的武器
        GradeType.Level level = ZAYIN;
        PmDamageTypes.Types damageTypes;

        // 根据伤害类型和武器类型设置等级和伤害类型
        if (isCloseCombatEgo(itemStack)) {
            level = getItemLevel(getEgoLevelTag(itemStack));
            damageTypes = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
        } else {
            if (damageSource.is(PmTags.PmDamageType.PHYSICS)){
                if (itemStack != null && !itemStack.isEmpty()) {
                    level = getItemLevel(getEgoLevelTag(itemStack));
                }else if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
                    level = getEntityLevel(livingEntity);
                }
                damageTypes = PmDamageTypes.Types.PHYSICS;
            } else {
                if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
                    level = getEntityLevel(livingEntity);
                }
                damageTypes = getType(damageSource);
            }
        }

        // 根据伤害类型处理抗性
        if (damageTypes != null) {
            switch (damageTypes) {
                case PHYSICS -> resistanceTreatment(event, level, PmDamageTypes.Types.PHYSICS);
                case SPIRIT -> resistanceTreatment(event, level, PmDamageTypes.Types.SPIRIT);
                case EROSION -> resistanceTreatment(event, level, PmDamageTypes.Types.EROSION);
                case THE_SOUL -> resistanceTreatment(event, level, PmDamageTypes.Types.THE_SOUL);
            }
        }
    }

    /** 伤害计算 */
    private static void resistanceTreatment(ArmorAbsorptionEvent event, GradeType.Level level, PmDamageTypes.Types damageTypes) {
        float newDamageAmount = event.getDamageAmount();
        int armorItemStackLaval = 0; // 盔甲等级
        int number = 0;
        boolean isArmorItemStackEmpty = true;
        for (ItemStack armorItemStack : event.getArmorSlots()) {
            if (armorItemStack != null && !armorItemStack.isEmpty()) {
                isArmorItemStackEmpty = false;
                armorItemStackLaval += getItemLevelValue(armorItemStack);
                number++;
            }
        }

        /// 等级处理
        /// 判断实体是否有护甲如果没有就用实体的等级
        if (!isArmorItemStackEmpty) {
            armorItemStackLaval /= number;
            newDamageAmount *= damageMultiple(armorItemStackLaval - level.getLevelValue());
        } else {
            GradeType.Level entityLaval = getEntityLevel(event.getEntity());
            newDamageAmount *= damageMultiple(entityLaval, level);
        }
        if(PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get()) {
            if (configImpact(damageTypes)) {
                damageTypes = PmDamageTypes.Types.PHYSICS;
            }

            /// 抗性处理
            newDamageAmount *= (float) switch (damageTypes) {
                case PHYSICS -> event.getEntity().getAttributeValue(PHYSICS_RESISTANCE);
                case SPIRIT -> event.getEntity().getAttributeValue(SPIRIT_RESISTANCE);
                case EROSION -> event.getEntity().getAttributeValue(EROSION_RESISTANCE);
                case THE_SOUL -> event.getEntity().getAttributeValue(THE_SOUL_RESISTANCE);
            };
        }
        event.setNewDamageAmount(newDamageAmount);
    }

    private static boolean configImpact(PmDamageTypes.Types damageTypes) {
        if (!PmConfig.SERVER.ENABLE_THE_SOUL_DAMAGE.get() && damageTypes.equals(PmDamageTypes.Types.THE_SOUL)) {
            return true;
        }
        if (!(PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() || PmConfig.COMMON.ENABLE_RATIONALITY.get() && damageTypes.equals(PmDamageTypes.Types.SPIRIT))) {
            return true;
        }
        return false;
    }
}
