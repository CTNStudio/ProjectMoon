package ctn.project_moon.events;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.item.SetInvulnerabilityTicks;
import ctn.project_moon.init.PmDamageTypes;
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
import static ctn.project_moon.api.GradeType.Level.getEgoLevelTag;
import static ctn.project_moon.api.GradeType.damageMultiple;
import static ctn.project_moon.common.entity.abnos.AbnosEntity.getEntityLevel;
import static ctn.project_moon.common.item.EgoItem.getItemLevelValue;
import static ctn.project_moon.common.item.components.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.events.SpiritEvents.updateSpiritValue;
import static ctn.project_moon.init.PmDamageTypes.Types.getType;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
    /**
     * 判断是否为近战EGO物品
     */
    public static boolean isCloseCombatEgo(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof EgoCloseCombat;
    }
    @SubscribeEvent
    public static void a3(LivingIncomingDamageEvent event){
        ItemStack itemStack = event.getSource().getWeaponItem();
        if (itemStack == null){
            return;
        }
        if (itemStack.getItem() instanceof SetInvulnerabilityTicks item) {
            event.setInvulnerabilityTicks(item.getTicks());
        }
    }
    
    /**
     * 已处理完等待扣除事件和效果处理
     */
    @SubscribeEvent
    public static void a4(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getSource().getWeaponItem(); // 获取伤害来源的武器
        DamageSource damageSource = event.getSource();
        if(!(entity instanceof AbnosEntity)) {// 异想体只有生命
            if (isCloseCombatEgo(itemStack)) {
                switch (getType(itemStack.get(CURRENT_DAMAGE_TYPE))) {
                    case SPIRIT -> executeSpiritDamage(event, entity);
                    case EROSION -> executeErosionDamage(event, entity);
                    case THE_SOUL -> executeTheSoulDamage(event, entity);
                    case null, default -> {} // 退出
                }
                // 非近战EGO武器处理（射弹）
            } else {
                switch (getType(damageSource)) {
                    case PHYSICS -> {} // 退出
                    case SPIRIT -> executeSpiritDamage(event, entity);
                    case EROSION -> executeErosionDamage(event, entity);
                    case THE_SOUL -> executeTheSoulDamage(event, entity);
                    case null, default -> {}
                }
            }
        }
    }

    public static void executeTheSoulDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        float max = entity.getMaxHealth();
        event.setNewDamage(max * (event.getNewDamage() / 100)); // 百分百扣生命
    }

    public static void executeSpiritDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (entity.getPersistentData().contains(SpiritEvents.SPIRIT)) {
            handleRationally(event, entity);
            event.setNewDamage(0);// 仅减少理智
        }
    }

    public static void executeErosionDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if (entity.getPersistentData().contains(SpiritEvents.SPIRIT)) {
            handleRationally(event, entity);
            // 理智和生命同时减少
        }
    }

    private static void handleRationally(LivingDamageEvent.Pre event, LivingEntity entity) {
        updateSpiritValue(entity, -event.getNewDamage());
    }

    /** 已应用伤害至实体 */
    @SubscribeEvent
    public static void a5(LivingDamageEvent.Post event) {
    }

    /**
     * 死亡
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

    }

    /** 护甲处理 */
    @SubscribeEvent
    public static void armorAbsorptionEvent(ArmorAbsorptionEvent event){
        DamageSource damageSource = event.getDamageSource() ;
        ItemStack itemStack = damageSource.getWeaponItem(); // 获取伤害来源的武器
        boolean isAbnosEntity = event.getEntity() instanceof Abnos;
        if (isCloseCombatEgo(itemStack)) {
            GradeType.Level itemLevel = getItemLevel(getEgoLevelTag(itemStack));
            switch (getType(itemStack.get(CURRENT_DAMAGE_TYPE))) {
                case PHYSICS -> closeCombatEgo(event, itemLevel, PmDamageTypes.Types.PHYSICS, isAbnosEntity);
                case SPIRIT -> closeCombatEgo(event, itemLevel, PmDamageTypes.Types.SPIRIT, isAbnosEntity);
                case EROSION -> closeCombatEgo(event, itemLevel, PmDamageTypes.Types.EROSION, isAbnosEntity);
                case THE_SOUL -> closeCombatEgo(event, itemLevel, PmDamageTypes.Types.THE_SOUL, isAbnosEntity);
                case null -> {
                    event.setReturn(false);
                }
            }
        } else switch (getType(damageSource)) {
            case PHYSICS -> {}
            case SPIRIT -> {}
            case EROSION -> {}
            case THE_SOUL -> {}
            case null -> {
                event.setReturn(false);
            }
        }
    }

    private static void closeCombatEgo(ArmorAbsorptionEvent event, GradeType.Level itemLevel, PmDamageTypes.Types damageTypes, boolean isAbnosEntity) {
        if (!isAbnosEntity){
            boolean isArmorItemStackEmpty = true;
            for (ItemStack armorItemStack : event.getArmorSlots()) {
                if (!armorItemStack.isEmpty()) {
                    isArmorItemStackEmpty = false;
                    break;
                }
            }
            if (!isArmorItemStackEmpty) {
                int armorItemStackLaval = 0;
                for (ItemStack armorItemStack : event.getArmorSlots()) {
                    armorItemStackLaval += getItemLevelValue(armorItemStack);
                }
                armorItemStackLaval /= 4;
                event.setDamageAmount(event.getDamageAmount() * damageMultiple(armorItemStackLaval - itemLevel.getLevel()));
            } else {
                GradeType.Level entityLaval = getEntityLevel(event.getEntity());
                event.setDamageAmount(event.getDamageAmount() * damageMultiple(entityLaval, itemLevel));
            }
        }


    }

    private static GradeType.Level getItemLevel(TagKey<Item> egoLevelTag) {
        return GradeType.Level.getItemLevel(egoLevelTag);
    }
}
