package ctn.project_moon.events;

import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.item.EgoItem.getEgoLevelTag;
import static ctn.project_moon.common.item.components.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.events.SpiritEvents.updateSpiritValue;
import static ctn.project_moon.init.PmDamageTypes.*;

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
    
    /**
     * 已处理完等待扣除事件和效果处理
     */
    @SubscribeEvent
    public static void a4(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getSource().getWeaponItem(); // 获取伤害来源的武器
        DamageSource damageSource = event.getSource();
        System.out.println(event.getNewDamage());
        if(!(entity instanceof AbnosEntity)) {// 异想体只有生命
            if (isCloseCombatEgo(itemStack)) {
                switch (PmDamageTypes.Types.getType(itemStack.get(CURRENT_DAMAGE_TYPE))) {
                    case SPIRIT -> executeSpiritDamage(event, entity);
                    case EROSION -> executeErosionDamage(event, entity);
                    case THE_SOUL -> executeTheSoulDamage(event, entity);
                    case null, default -> {/* 退出 */}
                }
                // 非近战EGO武器处理（射弹）
            } else if (damageSource.is(PmDamageTypes.SPIRIT)) {
                executeSpiritDamage(event, entity);
            } else if (damageSource.is(EROSION)) {
                executeErosionDamage(event, entity);
            } else if (damageSource.is(THE_SOUL)) {
                executeTheSoulDamage(event, entity);
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
        System.out.println(event.getOriginalDamage());
        System.out.println(event.getNewDamage());
        System.out.println(event.getBlockedDamage());
        System.out.println(event.getPostAttackInvulnerabilityTicks());
    }

    /**
     * 死亡
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

    }


    @SubscribeEvent
    public static void armorAbsorptionEvent(ArmorAbsorptionEvent event){
        DamageSource damageSource = event.getDamageSource() ;
        ItemStack itemStack = damageSource.getWeaponItem(); // 获取伤害来源的武器
        if (itemStack != null) {
            TagKey<Item> egoLevelTag = getEgoLevelTag(itemStack);
            if (!(event.getEntity() instanceof Abnos) && isCloseCombatEgo(itemStack)) {
                switch (PmDamageTypes.Types.getType(itemStack.get(CURRENT_DAMAGE_TYPE))) {
                    case PHYSICS -> {

                    }
                    case SPIRIT -> {

                    }
                    case EROSION -> {

                    }
                    case THE_SOUL -> {

                    }
                    case null, default -> event.setReturn(false);
                }
            }
        }
        if (damageSource.is(PHYSICS)) {
        } else if (damageSource.is(SPIRIT)) {
        } else if (damageSource.is(EROSION)) {
        } else if (damageSource.is(THE_SOUL)) {
        }
    }
}
