package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.api.TemporaryNbtAttribute;
import ctn.project_moon.tool.PlayerAnimTool;
import ctn.project_moon.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.tool.PmTool;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static ctn.project_moon.api.TemporaryNbtAttribute.*;
import static ctn.project_moon.common.item.AnimItem.createAnim;
import static net.minecraft.world.level.block.Block.canSupportCenter;

/** 失乐园物品 */
public class ParadiseLostItem extends SpecialEgoWeapon implements AnimAttackItem {
    public ParadiseLostItem(Weapon.Builder builder) {
        super(builder);
        setDefaultModel(new PmGeoItemModel<>("paradise_lost"));
    }

    private final int NORMAL_ATTACK_TICK = 8;
    private final int CHARGING_ATTACK_TICK = 10;

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 666;
    }

    // TODO 仍然有问题：玩家在移动或跳跃之后长按无法触发动画
    // TODO 其他玩家无法看到动画
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getWeaponItem();
        CompoundTag nbt = player.getPersistentData();
        // 玩家移动、下方方块没有实体方块顶部、在使用中时不执行
        if ((Minecraft.getInstance().player != null && PlayerAnimTool.isInput(Minecraft.getInstance().player.input) || !isGround(player) || nbt.getBoolean(IS_PLAYER_USE_ITEM) || nbt.getBoolean(IS_PLAYER_ATTACK))) {
            return InteractionResultHolder.fail(itemstack);
        }

        nbt.putFloat(PLAYER_RECORD_SPEED, player.getAbilities().getWalkingSpeed());;
        nbt.putBoolean(IS_PLAYER_USE_ITEM, true);
        nbt.putBoolean(IS_PLAYER_ATTACK, true);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
        player.startUsingItem(hand);
        createAnim(level, player, "animation.project_moon.paradise_lost.attack", 0);
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player) || !isGround(player) || isJumpCancellation(player)) {
            return;
        }
        PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
        PmTool.incrementNbt(player, PLAYER_USE_TICK, 1);
        CompoundTag nbt = player.getPersistentData();
        if (nbt.getInt(PLAYER_USE_ITEM_TICK) < NORMAL_ATTACK_TICK) {
            return;
        }
        if (nbt.getBoolean(IS_PLAYER_USE_ITEM)){
            createAnim(level, player, "animation.project_moon.paradise_lost.attack1", 1);
            nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
            nbt.putBoolean(IS_PLAYER_USE_ITEM, false);
        }
        if (nbt.getInt(PLAYER_USE_ITEM_TICK) < CHARGING_ATTACK_TICK){
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            chargingAttack(serverLevel, player);
        }
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
    }

    private static boolean isJumpCancellation(Player player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (minecraft.player.input.jumping) {
                restore(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {
        if (!(entity instanceof Player player)) {
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(IS_PLAYER_USE_ITEM, false);
        if (nbt.getInt(PLAYER_USE_TICK) < NORMAL_ATTACK_TICK){
            return;
        }
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(1);
            createAnim(level, player, "animation.project_moon.paradise_lost.attack2", 2);
        }
        nbt.putBoolean(IS_PLAYER_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
        PlayerAnimTool.restorePlayerSpeed(player);
    }

    /** 恢复 */
    private static void restore(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(IS_PLAYER_USE_ITEM, false);
        nbt.putBoolean(IS_PLAYER_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
        PlayerAnimTool.cancelAnimationLayer(player);
        PlayerAnimTool.restorePlayerSpeed(player);
        player.releaseUsingItem();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!isSelected || !(entity instanceof Player player) || player.isUsingItem()) {
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        if (!nbt.getBoolean(IS_PLAYER_ATTACK) || nbt.getBoolean(IS_PLAYER_USE_ITEM)) {
            return;
        }
        if (!isGround(player)) return;
        Minecraft minecraft = Minecraft.getInstance();
        // 因为不知名BUG因此这么写
        if (minecraft != null && minecraft.player != null && minecraft.player.input != null) {
            if (minecraft.player.input.jumping) {
                restore(player);
                return;
            }
        }
        PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
        if (nbt.getInt(PLAYER_USE_ITEM_TICK) < NORMAL_ATTACK_TICK){
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            normalAttack(serverLevel, player);
        }
        nbt.putBoolean(IS_PLAYER_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
        PlayerAnimTool.restorePlayerSpeed(player);
        PlayerAnimTool.removeLayer1(player);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        restore(player);
        return super.onDroppedByPlayer(item, player);
    }

    /** 是否在地面 */
    private boolean isGround(Player player) {
        return player.onGround();
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
            double v = 2;
            AABB aabb = new AABB(x - v, y - v, z - v, x + v, y + v, z + v);
            List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()) && livingEntity.isAlive());
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
        double v = 8;
        AABB aabb = new AABB(x - v, y - 3, z - v, x + v, y + 3, z + v);
        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, aabb, (livingEntity) -> !livingEntity.getUUID().equals(entity.getUUID()) && livingEntity.isAlive());
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
