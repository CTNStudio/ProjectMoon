package ctn.project_moon.events.entity;

import ctn.project_moon.common.RandomDamageProcessor;
import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.datagen.PmTags;
import ctn.project_moon.events.DourColorDamageTypesEvent;
import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.tool.GradeTypeTool;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.*;
import static ctn.project_moon.api.SpiritAttribute.*;
import static ctn.project_moon.common.item.Ego.getItemLevelValue;
import static ctn.project_moon.common.item.weapon.ego.CloseCombatEgo.isCloseCombatEgo;
import static ctn.project_moon.init.PmCommonHooks.dourColorDamageType;
import static ctn.project_moon.init.PmDamageTypes.Types.getType;
import static ctn.project_moon.init.PmEntityAttributes.*;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.tool.GradeTypeTool.Level.*;
import static ctn.project_moon.tool.GradeTypeTool.damageMultiple;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
    /** 即将受到伤害但还没处理 */
    @SubscribeEvent
    public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        ItemStack itemStack = damageSource.getWeaponItem();
        if (itemStack == null && damageSource.getEntity() != null) {
            itemStack = damageSource.getEntity().getWeaponItem();
        }

        // 修改生物无敌帧
        if (damageSource.getDirectEntity() instanceof SetInvulnerabilityTick entity) {
            event.setInvulnerabilityTicks(entity.getTicks());
        } else if (damageSource.getEntity() instanceof SetInvulnerabilityTick entity) {
            event.setInvulnerabilityTicks(entity.getTicks());
        } else if (itemStack != null) {
            // 随机物品伤害处理
            randomDamageLogic: {
                if (!(event.getSource().getEntity().level() instanceof ServerLevel serverLevel &&
                        itemStack.getItem() instanceof RandomDamageProcessor randomDamageitem)) {
                    break randomDamageLogic;
                }
                float damageScale = (event.getAmount() - randomDamageitem.getMaxDamage());
                event.setAmount(randomDamageitem.getDamage(serverLevel.getRandom()) + damageScale);

                if (itemStack.getItem() instanceof SetInvulnerabilityTick item) {
                    event.setInvulnerabilityTicks(item.getTicks());
                }
            }
        }

        // 获取等级
        GradeTypeTool.Level level = ZAYIN;
        if (itemStack != null && !itemStack.isEmpty()) {
            level = getItemLevel(getEgoLevelTag(itemStack));
        } else {
            Entity directEntity = damageSource.getDirectEntity();
            Entity entity = damageSource.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                level = getEntityLevel(livingEntity);
            } else if (directEntity instanceof LivingEntity livingEntity) {
                level = getEntityLevel(livingEntity);
            }
        }

        // 获取四色伤害类型
        PmDamageTypes.Types damageTypes;
        if (isCloseCombatEgo(itemStack)) {
            damageTypes = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
        } else {
            DourColorDamageTypesEvent dourColorEvents = dourColorDamageType(event.getEntity(), damageSource);
            if (dourColorEvents.getDamageTypes() != null) {
                damageTypes = dourColorEvents.getDamageTypes();
            } else {
                if (damageSource.is(PmTags.PmDamageType.PHYSICS)) {
                    damageTypes = PmDamageTypes.Types.PHYSICS;
                } else {
                    damageTypes = getType(damageSource);
                }
            }
        }

        // 根据四色伤害类型处理抗性
        switch (damageTypes) {
            case PHYSICS -> resistanceTreatment(event, level, PmDamageTypes.Types.PHYSICS);
            case SPIRIT -> resistanceTreatment(event, level, PmDamageTypes.Types.SPIRIT);
            case EROSION -> resistanceTreatment(event, level, PmDamageTypes.Types.EROSION);
            case THE_SOUL -> resistanceTreatment(event, level, PmDamageTypes.Types.THE_SOUL);
            case null -> resistanceTreatment(event, level, null);
        }
    }

    /** 伤害计算 */
    private static void resistanceTreatment(LivingIncomingDamageEvent event, GradeTypeTool.Level level, PmDamageTypes.Types damageTypes) {
        float newDamageAmount = event.getAmount();
        int armorItemStackLaval = 0; // 盔甲等级
        int number = 0;
        boolean isArmorItemStackEmpty = true;
        var flag = !(event.getEntity() instanceof Abnos);
        var itor = event.getEntity().getArmorAndBodyArmorSlots().iterator();
        ItemStack[] armorSlots = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            armorSlots[i] = flag ? itor.next() : ItemStack.EMPTY;
        }
        for (ItemStack armorItemStack : armorSlots) {
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
            GradeTypeTool.Level entityLaval = getEntityLevel(event.getEntity());
            newDamageAmount *= damageMultiple(entityLaval, level);
        }

        if (damageTypes != null && (PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get())) {
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

        event.setAmount(newDamageAmount);
    }

    /** 配置影响 */
    private static boolean configImpact(PmDamageTypes.Types damageTypes) {
        if (!PmConfig.SERVER.ENABLE_THE_SOUL_DAMAGE.get() && damageTypes.equals(PmDamageTypes.Types.THE_SOUL)) {
            return true;
        }
        if (!(PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() || PmConfig.COMMON.ENABLE_RATIONALITY.get() && damageTypes.equals(PmDamageTypes.Types.SPIRIT))) {
            return true;
        }
        return false;
    }

    /**
     * 实体死亡事件
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {
    }

    @SubscribeEvent
    public static void addSpirtAttyibute(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof LivingEntity entity && entity.getAttributes().hasAttribute(MAX_SPIRIT)){
            addSpiritAttribute(entity);
        }
    }

    /**
     * 自然恢复理智值
     */
    @SubscribeEvent
    public static void entityTickEvent(EntityTickEvent.Pre event) {
        refreshSpiritValue(event);
    }

    private static void refreshSpiritValue(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        CompoundTag nbt = entity.getPersistentData();
        if (!nbt.contains(INJURY_TICK)){
            return;
        }
        if (getInjuryCount(entity) != 0) {
            incrementInjuryCount(entity, -1);
        }
        if (!PmConfig.SERVER.ENABLE_LOW_RATIONALITY_NEGATIVE_EFFECT.get() ||
                !(nbt.contains(SPIRIT_VALUE) && nbt.contains(SPIRIT_RECOVERY_TICK))||
                getSpiritValue(entity) < 0 ||
                getInjuryCount(entity) != 0) {
            return;
        }
        incrementSpiritRecoveryTicks(entity, 1);
        if (getSpiritRecoveryTicks(entity) < (int) (20 / entity.getAttributeValue(SPIRIT_NATURAL_RECOVERY_RATE))) {
            return;
        }
        incrementSpiritValue(entity, (int) entity.getAttributeValue(SPIRIT_RECOVERY_AMOUNT));
        setSpiritRecoveryCount(entity, 0);
    }
}
