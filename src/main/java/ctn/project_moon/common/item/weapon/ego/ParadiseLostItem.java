package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.events.player.PlayerAnimEvents;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

import static ctn.project_moon.api.TemporaryAttribute.*;
import static ctn.project_moon.common.item.AnimItem.createAnim;
import static ctn.project_moon.events.player.PlayerAnimEvents.*;
import static net.minecraft.world.level.block.Block.canSupportCenter;

public class ParadiseLostItem extends SpecialEgoWeapon implements AnimAttackItem {
    public ParadiseLostItem(Weapon.Builder builder) {
        super(builder);
        setDefaultModel(new PmGeoItemModel<>("paradise_lost"));
    }

    @Override
    public int getTriggerTick(){
        return 8;
    }

    // TODO 仍然有问题：玩家在移动或跳跃之后长按无法触发动画
    // TODO 其他玩家无法看到动画
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getWeaponItem();
        if (Minecraft.getInstance().player != null && PlayerAnimEvents.getInput(Minecraft.getInstance().player.input)) {
            return InteractionResultHolder.fail(itemstack);
        }
        if (!canSupportCenter(level, player.getOnPos(), Direction.DOWN)) {
            return InteractionResultHolder.fail(itemstack);
        }
        CompoundTag nbt = player.getPersistentData();
        if (nbt.getBoolean(PLAYER_IS_USE_ITEM)){
            return InteractionResultHolder.fail(itemstack);
        }
        createAnim(player, "animation.project_moon.paradise_lost.attack", 0);
        nbt.putFloat(PLAYER_RECORD_SPEED, player.getSpeed());
        nbt.putBoolean(PLAYER_IS_USE_ITEM, true);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
        player.startUsingItem(hand);
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player)){
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        if (!(stack.getItem() instanceof AnimAttackItem item)){
            return;
        }
        nbt.putInt(PLAYER_ITEM_TICK, nbt.getInt(PLAYER_ITEM_TICK) + 1);
        nbt.putInt(PLAYER_USE_TICK, nbt.getInt(PLAYER_USE_TICK) + 1);
        if (!(nbt.getInt(PLAYER_ITEM_TICK) >= item.getTriggerTick())){
            return;
        }
        if (nbt.getBoolean(PLAYER_IS_USE_ITEM)){
            createAnim(player, "animation.project_moon.paradise_lost.attack1", 1);
            nbt.putBoolean(PLAYER_IS_USE_ITEM, false);
            if (level instanceof ServerLevel serverLevel) {
                chargingAttack(serverLevel, player);
            }
            restoreItemTick(player);
            return;
        }
        if (jumpCancellation(level, player)) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (minecraft.player.input.jumping) {
                restore(player);
                return;
            }
        }
        if (level instanceof ServerLevel serverLevel){
            chargingAttack(serverLevel, player);
        }
        restoreItemTick(player);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        restore(player);
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {
        if (!(entity instanceof Player player)) {
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        if (!(stack.getItem() instanceof AnimAttackItem item)) {
            return;
        }
        if (!(nbt.getInt(PLAYER_USE_TICK) >= item.getTriggerTick())){
            return;
        }
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(1);
            createAnim(player, "animation.project_moon.paradise_lost.attack2", 2);
        }
        restoreItemTick(player);
        restorePlayerSpeed(player);
        restoreUseTick(player);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 666;
    }

    /** 恢复 */
    private static void restore(Player player) {
        completeAttack(player);
        cancelAnimationLayer(player);
        restoreItemTick(player);
        restorePlayerSpeed(player);
        player.releaseUsingItem();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!isSelected){
            return;
        }
        if (!(entity instanceof Player player)){
            return;
        }
        if (player.isUsingItem()) {
            return;
        }
        if (jumpCancellation(level, player)) return;
        Minecraft minecraft = Minecraft.getInstance();
        // 因为不知名BUG因此这么写
        if (minecraft != null && minecraft.player != null && minecraft.player.input != null) {
            if (minecraft.player.input.jumping) {
                restore(player);
                return;
            }
        }
        CompoundTag nbt = player.getPersistentData();
        if (!(stack.getItem() instanceof AnimAttackItem item)){
            return;
        }
        if (!nbt.getBoolean(PLAYER_IS_USE_ITEM)){
            return;
        }
        nbt.putInt(PLAYER_ITEM_TICK, nbt.getInt(PLAYER_ITEM_TICK) + 1);
        if (!(nbt.getInt(PLAYER_ITEM_TICK) >= item.getTriggerTick())){
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            normalAttack(serverLevel, player);
        }
        restoreStatus(player);
    }

    private static void restoreStatus(Player player) {
        restorePlayerSpeed(player);
        restoreItemTick(player);
        completeAttack(player);
        restoreUseTick(player);
        removeLayer1(player);
    }

    /** 跳跃取消 */
    private static boolean jumpCancellation(Level level, Player player) {
        if (!canSupportCenter(level, player.getOnPos(), Direction.DOWN)) {
            restore(player);
            return true;
        }
        return false;
    }

    /** 召唤一个 */
    public static void normalAttack(ServerLevel level, LivingEntity entity){
        final Vec3 position = entity.getEyePosition();
        double x = 0;
        int y = 0;
        double z = 0;
        for (int scale = 0; scale <= 30; scale++) {
            Vec3 vec3 = position.add(entity.getLookAngle().scale(scale));
            x = vec3.x;
            y =(int) vec3.y;
            z = vec3.z;
            double v = 1;
            AABB aabb = new AABB(x - v, y - v, z - v, x + v, y + v, z + v);
            List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()));
            int i = entityList.size();
            if (i > 0) {
                LivingEntity livingEntity = entityList.get(entity.level().getRandom().nextInt(i));
                if (livingEntity != null) {
                    x = livingEntity.position().x;
                    y = livingEntity.blockPosition().getY();
                    z = livingEntity.position().z;
                    break;
                }
            } else if (!level.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                break;
            }
            while (level.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                y--;
                if (y < -64) {
                    y = (int) vec3.y;
                    break;
                }
            }
        }
        level.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(level, x, y, z, 1, entity));
    }

    /** 召唤多个 */
    public static void chargingAttack(ServerLevel level, LivingEntity entity){
        double x = entity.position().x;
        int y = entity.blockPosition().getY();
        double z = entity.position().z;
        double v = 10;
        AABB aabb = new AABB(x - v, y - 3, z - v, x + v, y + 3, z + v);
        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()));
        int i = entityList.size();
        if (i > 0) {
            for (LivingEntity livingEntity : entityList){
                x = livingEntity.position().x;
                y = livingEntity.blockPosition().getY();
                z = livingEntity.position().z;
                while (level.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
                    y--;
                    if (y < -64) {
                        y = (int) livingEntity.position().y;
                        break;
                    }
                }
                level.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(level, x, y, z, i, entity));
            }
        }
    }
}
