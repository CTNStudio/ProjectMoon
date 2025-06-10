package ctn.project_moon.common.item.weapon.remote;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.item.IPlayerAnim;
import ctn.project_moon.capability.item.IUsageReqItem;
import ctn.project_moon.client.models.GuiItemModel;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.entity.projectile.MagicBulletEntity;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.abstract_ltem.RemoteEgoWeapon;
import ctn.project_moon.tool.PmTool;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;
import java.util.List;

import static ctn.project_moon.api.TempNbtAttribute.*;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;
import static net.minecraft.world.InteractionHand.OFF_HAND;

/**
 * 魔弹武器
 *
 * @author dusttt
 * @date 2025-5-23
 */
public class MagicBulletItem extends RemoteEgoWeapon implements IUsageReqItem, IPlayerAnim {
	private final float BULLET_SPEED         = 1.0f;// 子弹速度
	private final int   NORMAL_ATTACK_TICK   = 8;
	private final int   CHARGING_ATTACK_TICK = 10;
	private       int   shootCount           = 0;

	public MagicBulletItem(Builder builder) {
		super(
				builder.build().component(
						ITEM_COLOR_USAGE_REQ,
						ItemColorUsageReq.empty().setValue(FourColorAttribute.Type.TEMPERANCE, FourColorAttribute.Rating.III)),
				builder, false);
		setDefaultModel(new PmGeoItemModel<>("magic_bullet"));
		setGuiModel(new GuiItemModel<>("magic_bullet"));
	}

	/**
	 * 使用物品
	 * 方法参照失乐园
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack itemstack = super.use(level, player, hand).getObject();
		CompoundTag nbt = player.getPersistentData();
		if (hand == OFF_HAND ||
		    Minecraft.getInstance().player == null ||
		    nbt.getBoolean(PLAYER_USE_ITEM) ||
		    nbt.getBoolean(PLAYER_ATTACK)) {
			return InteractionResultHolder.fail(itemstack);
		}
//		enterAttackState(level, player, null);// TODO:播放动画
		nbt.putBoolean(PLAYER_USE_ITEM, true);
		nbt.putBoolean(PLAYER_ATTACK, true);

		nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
		nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
		player.startUsingItem(hand);

		return InteractionResultHolder.success(itemstack);
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
		return 777;
	}

	@Override
	public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration) {
		if (!(entity instanceof Player player)) {
			return;
		}
		PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
		PmTool.incrementNbt(player, PLAYER_USE_TICK, 1);
		PmTool.incrementNbt(player, ITEM_TICK, 1);
		CompoundTag nbt = player.getPersistentData();
		if (!nbt.getBoolean(CANNOT_PLAYER_SWITCH_ITEMS)) {
			nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
		}
		if (!nbt.getBoolean(CANNOT_PLAYER_MOVED)) {
			nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
		}
		if (nbt.getInt(PLAYER_USE_TICK) < NORMAL_ATTACK_TICK) {
			return;
		}
		if (nbt.getInt(PLAYER_USE_TICK) == NORMAL_ATTACK_TICK) {
//			if (level instanceof ServerLevel serverLevel) {
//				PlayerAnimAPI.playPlayerAnim(serverLevel, player, PlayerAnim.getAnimationID(CONTINUOUS_ATTACK),
//						PlayerParts.allExceptHeadRot(), null, 2000);
//			}
			nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, true);
			if (!level.isClientSide()) {
				/**
				 * 特殊技能释放
				 */
				chargingShoot(player, stack, this.getMaxDamage(), this.getMinDamage());
			}
			nbt.putInt(ITEM_TICK, 0);
		}
		if (nbt.getInt(ITEM_TICK) >= CHARGING_ATTACK_TICK) {
			if (!level.isClientSide) {
				chargingShoot(player, stack, this.getMaxDamage(), this.getMinDamage());
			}
			nbt.putInt(ITEM_TICK, 0);
		}
	}

	@Override
	public void onStopUsing(@NotNull ItemStack stack, @NotNull LivingEntity entity, int count) {
		if (!(entity instanceof Player player)) {
			return;
		}
		CompoundTag nbt = player.getPersistentData();
		nbt.putBoolean(PLAYER_USE_ITEM, false);
		nbt.putInt(ITEM_TICK, 0);
		nbt.putInt(PLAYER_USE_TICK, 0);
		if (nbt.getInt(PLAYER_USE_ITEM_TICK) < NORMAL_ATTACK_TICK) {
			return;
		}
//		if (player.level() instanceof ServerLevel serverLevel) {
//            PlayerAnim.stopAnimation(serverLevel, player, CONTINUOUS_ATTACK);
//			PlayerAnimAPI.playPlayerAnim(serverLevel, player, PlayerAnim.getAnimationID(END),
//					PlayerParts.allExceptHeadRot(), null, 3000);
//		}
		nbt.putBoolean(PLAYER_ATTACK, false);
		nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
		nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
		nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
		nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
		super.onStopUsing(stack, entity, count);
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
		if (!isSelected || !(entity instanceof Player player) || player.isUsingItem()) {
			return;
		}
		CompoundTag nbt = player.getPersistentData();
		if (nbt.getBoolean(PLAYER_USE_ITEM)) {
			return;
		}
		if (nbt.getBoolean(PLAYER_ATTACK)) {
			Minecraft minecraft = Minecraft.getInstance();
			// 因为不知名BUG因此这么写(和失乐园一样的)
			if (minecraft.player != null) {
				if (minecraft.player.input.jumping) {
					forcedInterruption(level, player);
					return;
				}
			}
			PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
			if (nbt.getInt(PLAYER_USE_ITEM_TICK) == NORMAL_ATTACK_TICK) {
				if (!level.isClientSide) {
					//  普通攻击（第七发特殊）
					if (this.shootCount != 7) {
						normalShoot(player, stack, this.getMaxDamage(), this.getMinDamage());//测试用
					} else {
						SeventhShoot(player, stack, this.getMaxDamage(), this.getMinDamage());
					}
				}
			}
			if (nbt.getInt(PLAYER_USE_ITEM_TICK) == 10) {
				nbt.putBoolean(PLAYER_ATTACK, false);
				nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
				nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
				nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
				nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
			}
		}
	}

	/**
	 * 普通攻击
	 *
	 * @param shooter   射击者
	 * @param gunStack  射击物品
	 * @param maxDamage 最大伤害
	 * @param minDamage 最小伤害
	 */
	private void normalShoot(Player shooter, ItemStack gunStack, int maxDamage, int minDamage) {
		Level level = shooter.level();

		MagicBulletEntity magicBullet = MagicBulletEntity.create(level, shooter, maxDamage, minDamage);
		magicBullet.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, BULLET_SPEED, 1.0F);
		level.addFreshEntity(magicBullet);

		//TODO:播放音效

		addShootCount();
	}

	/**
	 * 第七发魔弹（普通攻击）
	 * 第七发魔弹会穿透追踪玩家与队友
	 *
	 * @param shooter   射击者
	 * @param gunStack  射击物品
	 * @param maxDamage 最大伤害
	 * @param minDamage 最小伤害
	 */
	private void SeventhShoot(Player shooter, ItemStack gunStack, int maxDamage, int minDamage) {
		Level level = shooter.level();

		MagicBulletEntity seventh_bullet = MagicBulletEntity.create(level, shooter, maxDamage, minDamage);
		/**
		 * 使其会追踪队友
		 */
		seventh_bullet.setDealDamageToAllies(true);
		seventh_bullet.setCanGoThroughWallsWhenNoTarget(true);//无目标时可穿墙

		Vec3 playerLook = shooter.getLookAngle().normalize();
		seventh_bullet.setPos(shooter.getEyePosition().subtract(playerLook.scale(3.0f)));//生成于玩家脑后
		/**
		 * 玩家可攻击时追踪玩家
		 */
		if (!shooter.isCreative() && !shooter.isSpectator() && shooter.isAlive() && shooter.isAttackable()) {
			seventh_bullet.setTrackingTarget(shooter);
		}
		seventh_bullet.shoot(playerLook.x, playerLook.y, playerLook.z, BULLET_SPEED, 0.5f);
		level.addFreshEntity(seventh_bullet);

		//TODO:播放音效

		addShootCount();
	}

	/**
	 * 蓄力攻击
	 * 在玩家身后扇形生成6枚子弹向前射去
	 * 以及一枚子弹穿过玩家身体
	 */
	private void chargingShoot(Player player, ItemStack stack, int maxDamage, int minDamage) {
		Level level = player.level();

		// 获取玩家后方基准点
		Vec3 playerLook = player.getLookAngle().normalize();
		Vec3 backwardPos = player.position()
				.subtract(playerLook.scale(2.5)) // 向后移动2格
				.add(0, player.getEyeHeight() * 0.8, 0); // 调整到腰部高度

		// 扇形参数配置
		int bulletCount = 6;
		float fanWidth = 5.0f; // 扇形宽度
		float startOffset = -fanWidth / 2; // 起始横向偏移
		float step = fanWidth / (bulletCount - 1); // 横向间隔

		// 计算垂直于视线的平面基向量
		Vec3 planeRight = playerLook.cross(new Vec3(0, 1, 0)).normalize(); // 平面右向量
		Vec3 planeUp = planeRight.cross(playerLook).normalize(); // 平面上向量

		// 生成子弹
		for (int i = 0; i < bulletCount; i++) {
			// 计算当前子弹在平面上的偏移
			float currentOffset = startOffset + step * i;

			// 计算生成位置（在垂直平面上呈直线排列）
			Vec3 spawnPos = backwardPos
					.add(planeRight.scale(currentOffset)) // 水平偏移
					.add(planeUp.scale(fanWidth / 2.0f * Math.sin(i * 0.628f))); // 添加垂直波动

			// 创建并发射子弹（全部朝玩家视线方向）
			MagicBulletEntity bullet = MagicBulletEntity.create(level, player, maxDamage, minDamage);

			//  选用：设置无目标时可穿墙(否则容易被墙挡)
			bullet.setCanGoThroughWallsWhenNoTarget(true);

			bullet.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
			bullet.shoot(
					playerLook.x,
					playerLook.y,
					playerLook.z,
					BULLET_SPEED,
					0.5f
			);
			level.addFreshEntity(bullet);
		}
		// 第七发魔弹：穿透玩家(大致于普通攻击的第七发相同)
		MagicBulletEntity seventh_bullet = MagicBulletEntity.create(level, player, maxDamage, minDamage);
		seventh_bullet.setDealDamageToAllies(true);
		seventh_bullet.setPos(backwardPos.subtract(playerLook.scale(1.5)));
		if (!player.isCreative() && player.isAlive() && player.isAttackable()) {
			seventh_bullet.setTrackingTarget(player);
		}
		seventh_bullet.setCanGoThroughWallsWhenNoTarget(true);//无目标时可穿墙
		seventh_bullet.shoot(playerLook.x, playerLook.y, playerLook.z, BULLET_SPEED, 0.5f);
		level.addFreshEntity(seventh_bullet);

		// TODO:播放音效

	}

	/**
	 * 计算射击次数
	 */
	private void addShootCount() {
		this.shootCount = this.shootCount % 7 + 1;
	}

	@Override
	public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	}

	@Override
	public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	}

	@Override
	public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	}

	@Override
	public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	}

	@Override
	public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	}

	@Override
	public void forcedInterruption(Level level, Player player) {
		resetTemporaryAttribute(player);
//		PlayerAnim.stopAnimation(level, player, ATTACK);
//		PlayerAnim.stopAnimation(level, player, CONTINUOUS_ATTACK);
//		PlayerAnim.stopAnimation(level, player, END);
		player.releaseUsingItem();
	}

	@CheckForNull
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.EROSION);
	}
}
