package ctn.project_moon.events.entity;

import ctn.project_moon.common.item.weapon.RandomDamageItem;
import ctn.project_moon.common.item.weapon.SetInvulnerabilityTicks;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.events.SpiritEvents.*;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
    /** 即将受到伤害但还没处理 */
    @SubscribeEvent
    public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event){
        ItemStack itemStack = event.getSource().getWeaponItem();
        if (itemStack == null){
            return;
        }

        /// 随机伤害处理
        /// ps:不支持小数（懒）
        {
            if (!(event.getSource().getEntity().level() instanceof ServerLevel serverLevel &&
                    itemStack.getItem() instanceof RandomDamageItem item)) {
                return;
            }
            // 获取增幅
            int damageScale = (int) (event.getAmount() - item.getMaxDamage());
            int maxDamage = damageScale + item.getMaxDamage();
            int minDamage = damageScale + item.getMinDamage();
            if (minDamage < maxDamage) {
                event.setAmount(serverLevel.random.nextInt(minDamage, maxDamage + 1));
            }
        }

        // 修改生物无敌帧
        if (itemStack.getItem() instanceof SetInvulnerabilityTicks item) {
            event.setInvulnerabilityTicks(item.getTicks());
        }
    }

    /**
     * 实体死亡事件
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

    }

    @SubscribeEvent
    public static void addSpirtAttyibute(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof LivingEntity entity && entity.getAttributes().hasAttribute(PmAttributes.MAX_SPIRIT)){
            processAttributeInformation(entity);
        }
    }

    /**
     * 自然恢复理智值
     */
    @SubscribeEvent
    public static void refreshSpiritValue(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        CompoundTag nbt = entity.getPersistentData();
        if (!nbt.contains(INJURY_COUNT)){
            return;
        }
        if (getInjuryCount(entity) != 0) {
            incrementInjuryCount(entity, -1);
        }
        if (!PmConfig.SERVER.ENABLE_LOW_RATIONALITY_NEGATIVE_EFFECT.get()){
            return;
        }
        if (!(nbt.contains(SPIRIT_VALUE) && nbt.contains(SPIRIT_RECOVERY_COUNT))) {
            return;
        }
        if (getSpiritValue(entity) < 0 || getInjuryCount(entity) != 0) {
            return;
        }
        incrementSpiritRecoveryTicks(entity, 1);
        int ticks =  getSpiritRecoveryTicks(entity);
        if (ticks < (int) (20 / entity.getAttributeValue(PmAttributes.SPIRIT_NATURAL_RECOVERY_RATE))) {
            return;
        }
        incrementSpiritValue(entity, (int) entity.getAttributeValue(PmAttributes.SPIRIT_RECOVERY_AMOUNT));
        setSpiritRecoveryCount(entity, 0);
    }
}
