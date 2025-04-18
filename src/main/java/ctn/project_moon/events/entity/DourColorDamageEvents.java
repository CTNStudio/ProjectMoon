package ctn.project_moon.events.entity;

import ctn.project_moon.client.particles.DamageParticle;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.item.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.common.item.weapon.ego.CloseCombatEgo.isCloseCombatEgo;
import static ctn.project_moon.events.SpiritEvents.*;
import static ctn.project_moon.init.PmDamageTypes.Types.getType;

@EventBusSubscriber(modid = MOD_ID)
public class DourColorDamageEvents {
    /**
     * 处理伤害效果
     */
    @SubscribeEvent
    public static void dealingWithDamageEffects(LivingDamageEvent.Pre event) {
        if(!PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get()){
            return;
        }
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getSource().getWeaponItem();
        DamageSource damageSource = event.getSource();
        PmDamageTypes.Types types;
        if (isCloseCombatEgo(itemStack)) {
            types = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
        } else {
            types = getType(damageSource);
        }
        switch (types) {
            case SPIRIT -> executeSpiritDamage(event, entity);
            case EROSION -> executeErosionDamage(event, entity);
            case THE_SOUL -> {
                if (doesTheOrganismSufferFromTheSoulDamage(entity)){
                    break;
                }
                executeTheSoulDamage(event, entity);
                return;
            }
            case null, default -> {}
        }
        reply(event, entity);
    }

    /** 灵魂伤害判断 */
    private static boolean doesTheOrganismSufferFromTheSoulDamage(LivingEntity entity){
        if (!PmConfig.SERVER.THE_SOUL_AFFECT_ABOMINATIONS.get() && entity instanceof AbnosEntity) {
            return true;
        }
        if (!PmConfig.SERVER.THE_SOUL_AFFECT_PLAYERS.get() && entity instanceof Player) {
            return true;
        }
        if (!PmConfig.SERVER.THE_SOUL_AFFECT_ENTITIES.get() && !(entity instanceof AbnosEntity || entity instanceof Player)) {
            return true;
        }
        return false;
    }

    /** 扣除生命 */
    public static boolean reply(LivingDamageEvent.Pre event, LivingEntity entity) {
        float newDamage = event.getNewDamage();
        if (newDamage <= 0) {
            entity.heal(-newDamage);
            event.getContainer().setPostAttackInvulnerabilityTicks(0);
            return true;
        }
        return false;
    }

    /** 百分比扣生命 */
    public static void executeTheSoulDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        if(reply(event, entity)) return;
        float max = entity.getMaxHealth();
        event.setNewDamage(max * (event.getNewDamage() / 100));
    }

    /** 如果受伤者没有理智，则理智和生命同时减少 */
    public static void executeErosionDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
        handleRationally(event, entity);
    }

    /** 已应用伤害至实体事件 */
    @SubscribeEvent
    public static void appliedDamageToEntityEvent(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        CompoundTag nbt = entity.getPersistentData();
        if (nbt.contains(INJURY_COUNT)){
            setInjuryCount(entity, 200);
            if (nbt.contains(SPIRIT_RECOVERY_COUNT)){
                setSpiritRecoveryCount(entity, 0);
            }
        }

        var world = entity.level();
        if (world.isClientSide()) {
            return;
        }

        Vec3 pos = entity.position();
        double x = pos.x;
        double y = entity.getBoundingBoxForCulling().maxY;
        double z = pos.z;
        Component text = Component.literal(String.valueOf(event.getNewDamage()));
        ((ServerLevel) world)
                .sendParticles(new DamageParticle.Options(text), x, y, z, 1, 0.1, 0.0, 0.1, 0.2);
  }
}
