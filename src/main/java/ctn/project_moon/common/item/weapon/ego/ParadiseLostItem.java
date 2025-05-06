package ctn.project_moon.common.item.weapon.ego;

import com.zigythebird.playeranimatorapi.API.PlayerAnimAPI;
import com.zigythebird.playeranimatorapi.data.PlayerParts;
import ctn.project_moon.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.project_moon.common.item.PlayerAnim;
import ctn.project_moon.common.item.RequestItems;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.GuiItemModel;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.tool.PmTool;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static ctn.project_moon.api.TempNbtAttr.*;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;
import static net.minecraft.world.InteractionHand.OFF_HAND;

/** 失乐园武器 */
public class ParadiseLostItem extends SpecialEgoWeapon implements PlayerAnim , RequestItems {
    public static final String ATTACK = "player.paradise_lost.attack";
    public static final String CONTINUOUS_ATTACK = "player.paradise_lost.continuous_attack";
    public static final String END = "player.paradise_lost.end";

    public ParadiseLostItem(Weapon.Builder builder) {
        super(builder.build().component(ITEM_COLOR_USAGE_REQ, ItemColorUsageReq.empty()
            .setValue(ItemColorUsageReq.Type.FORTITUDE, ItemColorUsageReq.Rating.V)
            .setValue(ItemColorUsageReq.Type.PRUDENCE, ItemColorUsageReq.Rating.V)
            .setValue(ItemColorUsageReq.Type.TEMPERANCE, ItemColorUsageReq.Rating.V)
            .setValue(ItemColorUsageReq.Type.JUSTICE, ItemColorUsageReq.Rating.V)
        ), builder);
        setDefaultModel(new PmGeoItemModel<>("paradise_lost"));
        setGuiModel(new GuiItemModel<>("paradise_lost"));
    }

    private final int NORMAL_ATTACK_TICK = 8;
    private final int CHARGING_ATTACK_TICK = 10;

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 666;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = super.use(level, player, hand).getObject();
        CompoundTag nbt = player.getPersistentData();
        // 玩家移动、下方方块没有实体方块顶部、在使用中时不执行
        if (hand == OFF_HAND ||
                Minecraft.getInstance().player == null ||
                !player.onGround() ||
                nbt.getBoolean(PLAYER_USE_ITEM) ||
                nbt.getBoolean(PLAYER_ATTACK) ||
                isJumpCancellation(level, player)) {
            return InteractionResultHolder.fail(itemstack);
        }
        enterAttackState(level, player, ATTACK);
        nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
        nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
        player.startUsingItem(hand);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player) || !player.onGround() || isJumpCancellation(level, player)) {
            return;
        }
        PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
        PmTool.incrementNbt(player, PLAYER_USE_TICK, 1);
        PmTool.incrementNbt(player, ITEM_TICK, 1);
        CompoundTag nbt = player.getPersistentData();
        if (!nbt.getBoolean(CANNOT_PLAYER_SWITCH_ITEMS)){
            nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
        }
        if (!nbt.getBoolean(CANNOT_PLAYER_MOVED)){
            nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
        }
        if (nbt.getInt(PLAYER_USE_TICK) < NORMAL_ATTACK_TICK) {
            return;
        }
        if (nbt.getInt(PLAYER_USE_TICK) == NORMAL_ATTACK_TICK) {
            if (level instanceof  ServerLevel serverLevel){
                PlayerAnimAPI.playPlayerAnim(serverLevel, player, PlayerAnim.getAnimationID(CONTINUOUS_ATTACK),
                        PlayerParts.allExceptHeadRot(), null, 2000);
            }
            nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, true);
            chargingAttack(level, player);
            nbt.putInt(ITEM_TICK, 0);
        }
        if (nbt.getInt(ITEM_TICK) >= CHARGING_ATTACK_TICK){
            chargingAttack(level, player);
            nbt.putInt(ITEM_TICK, 0);
        }
    }

    private boolean isJumpCancellation(Level level, Player player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (minecraft.player.input.jumping) {
                forcedInterruption(level, player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {

    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        if (!(entity instanceof Player player)) {
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(PLAYER_USE_ITEM, false);
        nbt.putInt(ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
        if (nbt.getInt(PLAYER_USE_ITEM_TICK) < NORMAL_ATTACK_TICK){
            return;
        }
        if (player.level() instanceof ServerLevel serverLevel){
//            PlayerAnim.stopAnimation(serverLevel, player, CONTINUOUS_ATTACK);
            PlayerAnimAPI.playPlayerAnim(serverLevel, player, PlayerAnim.getAnimationID(END),
                    PlayerParts.allExceptHeadRot(), null, 3000);
        }
        nbt.putBoolean(PLAYER_ATTACK, false);
        nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
        nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
        nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        super.onStopUsing(stack, entity, count);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!isSelected || !(entity instanceof Player player) || player.isUsingItem()) {
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        if(nbt.getBoolean(PLAYER_USE_ITEM)){
            return;
        }
        if (nbt.getBoolean(PLAYER_ATTACK)) {
            if (!player.onGround()) return;
            Minecraft minecraft = Minecraft.getInstance();
            // 因为不知名BUG因此这么写
            if (minecraft != null && minecraft.player != null && minecraft.player.input != null) {
                if (minecraft.player.input.jumping) {
                    forcedInterruption(level, player);
                    return;
                }
            }
            PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
            if (nbt.getInt(PLAYER_USE_ITEM_TICK) == NORMAL_ATTACK_TICK) {
                 normalAttack(level, player);
            }
            if (nbt.getInt(PLAYER_USE_ITEM_TICK) == 10){
                nbt.putBoolean(PLAYER_ATTACK, false);
                nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
                nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
                nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
                nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
            }
        }
    }

    // TODO 待优化：尖刺会锁定创造玩家，会被各种各样的方块拦住，有时会同时锁定同一个生物攻击
    /** 召唤一个 */
    public static void normalAttack(Level level, LivingEntity entity){
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        final Vec3 position = entity.getEyePosition();
        double x = 0;
        int y = 0;
        double z = 0;
        for (int scale = 0; scale <= 30; scale++) {
            Vec3 vec3 = position.add(entity.getLookAngle().scale(scale));
            x = vec3.x;
            y =(int) vec3.y;
            z = vec3.z;
            double v = 2;
            AABB aabb = new AABB(x - v, y - v, z - v, x + v, y + v, z + v);
            List<LivingEntity> entityList = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()) && livingEntity.isAlive());
            int i = entityList.size();
            if (i > 0) {
                LivingEntity livingEntity = entityList.get(entity.level().getRandom().nextInt(i));
                if (livingEntity != null) {
                    x = livingEntity.position().x;
                    y = livingEntity.blockPosition().getY();
                    z = livingEntity.position().z;
                    break;
                }
            } else if (!serverLevel.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                break;
            }
            while (serverLevel.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                y--;
                if (y < -64) {
                    y = (int) vec3.y;
                    break;
                }
            }
        }
        serverLevel.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(serverLevel, x, y, z, 1, entity));
    }

    /** 召唤多个 */
    public static void chargingAttack(Level level, LivingEntity entity){
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        double x = entity.position().x;
        int y = entity.blockPosition().getY();
        double z = entity.position().z;
        double v = 8;
        AABB aabb = new AABB(x - v, y - 3, z - v, x + v, y + 3, z + v);
        List<LivingEntity> entityList = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()) && livingEntity.isAlive());
        int i = entityList.size();
        if (i > 0) {
            for (LivingEntity livingEntity : entityList){
                x = livingEntity.position().x;
                y = livingEntity.blockPosition().getY();
                z = livingEntity.position().z;
                while (serverLevel.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                    y--;
                    if (y < -64) {
                        y = (int) livingEntity.position().y;
                        break;
                    }
                }
                serverLevel.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(serverLevel, x, y, z, i, entity));
            }
        }
    }

    /**
     * 強制中断
     */
    @Override
    public void forcedInterruption(Level level, Player player) {
        resetTemporaryAttribute(player);
        PlayerAnim.stopAnimation(level, player, ATTACK);
        PlayerAnim.stopAnimation(level, player, CONTINUOUS_ATTACK);
        PlayerAnim.stopAnimation(level, player, END);
        player.releaseUsingItem();
    }

    /**
     * 使用物品时触发
     */
    @Override
    public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 攻击时触发
     */
    @Override
    public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 在手上时触发
     */
    @Override
    public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 物品在背包里时触发
     */
    @Override
    public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }

    /**
     * 在装备槽里时触发，如盔甲，饰品
     */
    @Override
    public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {

    }
}
