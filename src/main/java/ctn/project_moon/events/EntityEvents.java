package ctn.project_moon.events;

import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.item.EgoItem;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.item.components.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.events.SpiritEvents.*;

/** 实体事件 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
    @SubscribeEvent
    public static void a1(EntityInvulnerabilityCheckEvent event) {
    }

    @SubscribeEvent
    public static void a2(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof AbnosEntity)) {
            ItemStack itemStack = event.getSource().getWeaponItem();
            if (itemStack == null){
                return;
            }
            Item item = itemStack.getItem();
            if (!(item instanceof EgoItem)) {
                return;
            }
            if (!(item instanceof EgoCloseCombat)) {
                return;
            }
            DamageContainer container = event.getContainer();
            String damageTypeString = itemStack.get(CURRENT_DAMAGE_TYPE);
            switch (PmDamageTypes.Types.getType(damageTypeString)) {
                case PHYSICS -> {}
                case SPIRIT -> {
                    if (entity.getPersistentData().contains(SPIRIT)){
                        container.setNewDamage(0);
                        return;
                    }
                }
                case EROSION -> {
                    if (entity.getPersistentData().contains(SPIRIT)){
                        updateSpiritValue(entity, - event.getAmount());
                        return;
                    }
                }
                case THE_SOUL -> {
                    if (!(entity instanceof AbnosEntity)){
                        float max = entity.getMaxHealth();
                        container.setNewDamage(max * (event.getAmount() / 100));
                    }
                }
                case null, default -> {}
            }
            return;
        }
    }

    @SubscribeEvent
    public static void a3(LivingShieldBlockEvent event) {
//        System.out.println(3);
    }

    /** 已处理完等待扣除事件 */
    @SubscribeEvent
    public static void a4(LivingDamageEvent.Pre event) {
//        System.out.println(4);
    }

    @SubscribeEvent
    public static void a5(LivingDamageEvent.Post event) {
//        System.out.println(5);
    }

    private static void extracted(String x) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(x));
        }
    }

    /**
     * 死亡
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

    }
}
