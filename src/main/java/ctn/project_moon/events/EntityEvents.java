package ctn.project_moon.events;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.common.item.RandomDamageItem;
import ctn.project_moon.common.item.SetInvulnerabilityTicks;
import ctn.project_moon.common.item.weapon.ego.CloseCombatEgo;
import ctn.project_moon.datagen.PmTags;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.ZAYIN;
import static ctn.project_moon.api.GradeType.Level.getEgoLevelTag;
import static ctn.project_moon.api.GradeType.damageMultiple;
import static ctn.project_moon.common.entity.abnos.AbnosEntity.getEntityLevel;
import static ctn.project_moon.common.item.Ego.getItemLevelValue;
import static ctn.project_moon.events.SpiritEvents.updateSpiritValue;
import static ctn.project_moon.init.PmAttributes.*;
import static ctn.project_moon.init.PmDamageTypes.Types.getType;
import static ctn.project_moon.init.PmDataComponents.CURRENT_DAMAGE_TYPE;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
    /**
     * 判断是否为近战EGO物品
     */
    public static boolean isCloseCombatEgo(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof CloseCombatEgo;
    }

    /** 即将受到伤害但还没处理 */
    @SubscribeEvent
    public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event){
        ItemStack itemStack = event.getSource().getWeaponItem();
        if (itemStack == null){
            return;
        }

        randomDamage(event, itemStack);

        // 修改生物无敌帧
        if (itemStack.getItem() instanceof SetInvulnerabilityTicks item) {
            event.setInvulnerabilityTicks(item.getTicks());
        }
    }

    /** 随机伤害处理
     * <p>
     * ps:不支持小数（懒）
     * */
    private static void randomDamage(LivingIncomingDamageEvent event, ItemStack itemStack) {
        if (!(event.getSource().getEntity().level() instanceof ServerLevel serverLevel && itemStack.getItem() instanceof RandomDamageItem item)) {
            return;
        }
        int damageScale = (int) (event.getAmount() - item.getMaxDamage());
        int maxDamage = damageScale + item.getMaxDamage();
        int minDamage = damageScale + item.getMinDamage();
        if (minDamage <= maxDamage) {
            event.setAmount(serverLevel.random.nextInt(minDamage, maxDamage + 1));
        }
    }

    /**
     * 已处理完等待扣除事件和效果处理
     */
    @SubscribeEvent
    public static void completedProcessingOfWaitingDeductionEvent(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getSource().getWeaponItem();
        DamageSource damageSource = event.getSource();

        /// 异想体只有生命
        /// 所以不参与这些效果
        if(!(entity instanceof AbnosEntity)) {
            PmDamageTypes.Types types;
            if (isCloseCombatEgo(itemStack)) {
                types = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
                // 非近战EGO武器处理（射弹）
            } else {
                types = getType(damageSource);
            }
            switch (types) {
                case SPIRIT -> executeSpiritDamage(event, entity);
                case EROSION -> executeErosionDamage(event, entity);
                case THE_SOUL -> executeTheSoulDamage(event, entity);
                case null, default -> {}
            }
        }
    }

    /** 原版护甲处理前 */
    @SubscribeEvent
    public static void armorAbsorptionEvent(ArmorAbsorptionEvent.Pre event){
        DamageSource damageSource = event.getDamageSource() ;
        ItemStack itemStack = damageSource.getWeaponItem(); // 获取伤害来源的武器
        GradeType.Level level = ZAYIN;
        PmDamageTypes.Types damageTypes;

        // 根据伤害类型和武器类型设置等级和伤害类型
        if (damageSource.is(PmTags.PmDamageType.PHYSICS)){
            if (itemStack != null && !itemStack.isEmpty()) {
                level = getItemLevel(getEgoLevelTag(itemStack));
            }
            damageTypes = PmDamageTypes.Types.PHYSICS;
        } else if (isCloseCombatEgo(itemStack)) {
            level = getItemLevel(getEgoLevelTag(itemStack));
            damageTypes = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
        } else {
            if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity){
                level = getEntityLevel(livingEntity);
            }
            damageTypes = getType(damageSource);
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

    /** 百分百扣生命 */
    public static void executeTheSoulDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        float max = entity.getMaxHealth();
        event.setNewDamage(max * (event.getNewDamage() / 100));
    }

    /** 如果受伤者没有理智，则仅减少理智 */
    public static void executeSpiritDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (entity.getPersistentData().contains(SpiritEvents.SPIRIT)) {
            handleRationally(event, entity);
            event.setNewDamage(0);
        }
    }

    /** 如果受伤者没有理智，则理智和生命同时减少 */
    public static void executeErosionDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (entity.getPersistentData().contains(SpiritEvents.SPIRIT)) {
            handleRationally(event, entity);
        }
    }

    private static void handleRationally(LivingDamageEvent.Pre event, LivingEntity entity) {
        updateSpiritValue(entity, -event.getNewDamage());
    }

    /** 已应用伤害至实体事件 */
    @SubscribeEvent
    public static void appliedDamageToEntityEvent(LivingDamageEvent.Post event) {
//        LivingEntity entity = event.getEntity();
//        if (!(entity.level() instanceof ServerLevel serverLevel)) {
//            return;
//        }
//        Vec3 pos = entity.position();
//        double x = pos.x;
//        double y = entity.getBoundingBoxForCulling().maxY;
//        double z = pos.z;
//        Component text = Component.literal(String.valueOf(event.getNewDamage()));
//        serverLevel.sendParticles(new DamageParticle.Options(text), x, y, z, 1, 0, 0, 0,10);
//        serverLevel.sendParticles(new c.DamageIndicatorOptions(
//                text,
//                false,
//                c.DamageIndicatorOptions.Type.DAMAGE),
//                x,
//                y,
//                z,
//                1,
//                0.1F,
//                0.1F,
//                0.1F,
//                1);
    }

    /**
     * 死亡
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

    }

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

        // 判断实体是否有护甲如果没有就用实体的等级
        if (!isArmorItemStackEmpty) {
            armorItemStackLaval /= number;
            newDamageAmount *= damageMultiple(armorItemStackLaval - level.getLevelValue());
        } else {
            GradeType.Level entityLaval = getEntityLevel(event.getEntity());
            newDamageAmount *= damageMultiple(entityLaval, level);
        }

        // 获取抗性
        newDamageAmount *= (float) switch (damageTypes){
            case PHYSICS -> event.getEntity().getAttributeValue(PHYSICS_RESISTANCE);
            case SPIRIT -> event.getEntity().getAttributeValue(SPIRIT_RESISTANCE);
            case EROSION -> event.getEntity().getAttributeValue(EROSION_RESISTANCE);
            case THE_SOUL -> event.getEntity().getAttributeValue(THE_SOUL_RESISTANCE);
        };
        event.setNewDamageAmount(newDamageAmount);
    }

    private static GradeType.Level getItemLevel(TagKey<Item> egoLevelTag) {
        return GradeType.Level.getItemLevel(egoLevelTag);
    }
}
