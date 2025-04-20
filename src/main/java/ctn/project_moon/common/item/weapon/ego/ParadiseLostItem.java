package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.events.player.PlayerAnimEvents;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import static ctn.project_moon.PmMain.MOD_ID;
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
    public int freMovementTick() {
        return 9;
    }

    public int triggerTick(){
        return 8;
    }

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
        createAnim(player, "animation.project_moon.paradise_lost.attack", 0);
        nbt.putFloat(PLAYER_RECORD_SPEED, player.getSpeed());
        nbt.putBoolean(PLAYER_SPECIAL_WEAPON_ATTACK, true);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
        player.startUsingItem(hand);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
//        if (level.isClientSide){
//            System.out.print("<8>");
//        }else {
//            System.out.print("{8}");
//        }
//        if (!(entity instanceof Player player)){
//            return;
//        }
//        tick(level, player, stack);
//        super.onUseTick(level, entity, stack, 1000);
        if (!(entity instanceof Player player)){
            return;
        }
        CompoundTag nbt = player.getPersistentData();
        if (!(stack.getItem() instanceof AnimAttackItem item)){
            return;
        }
        nbt.putInt(PLAYER_USE_ITEM_TICK, nbt.getInt(PLAYER_USE_ITEM_TICK) + 1);
        if (!(nbt.getInt(PLAYER_USE_ITEM_TICK) >= item.triggerTick())){
            return;
        }
        if (nbt.getBoolean(PLAYER_SPECIAL_WEAPON_ATTACK)){
            createAnim(player, "animation.project_moon.paradise_lost.attack1", 0);
            nbt.putBoolean(PLAYER_SPECIAL_WEAPON_ATTACK, false);
        }
        if (!canSupportCenter(level, player.getOnPos(), Direction.DOWN)) {
            extracted(player);
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            if (minecraft.player.input.jumping) {
                extracted(player);
                return;
            }
        }
        if (!(nbt.getInt(PLAYER_USE_ITEM_TICK) >= item.triggerTick())) {
            return;
        }
        if (!player.level().isClientSide){
            entity.sendSystemMessage(Component.literal("触发了"));
        }
        restoreTick(player);
//        restorePlayerSpeed(player);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {
//        time
//        if (level.isClientSide){
//            System.out.print("<9>");
//        }else {
//            System.out.print("{9}");
//        }
        if (!(entity instanceof Player player)){
            return;
        }
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(0);
            animationStack.removeLayer(1);
            createAnim(player, "animation.project_moon.paradise_lost.attack2", 1);
        }
        restoreTick(player);
        restorePlayerSpeed(player);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 666;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (isSelected){
        }
//        if (!(entity instanceof AbstractClientPlayer clientPlayer)) {
//            return;
//        }
//        removeAnimation(clientPlayer);
//        restorePlayerSpeed(clientPlayer);
    }

    private static void extracted(Player player) {
        cancelAnimation(player);
        restoreTick(player);
        restorePlayerSpeed(player);
        player.releaseUsingItem();
    }

    private static void removeAnimation(AbstractClientPlayer clientPlayer) {
        AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
        animationStack.removeLayer(0);
    }

    @Override
    public void tick(Level level, LivingEntity livingEntity, ItemStack stack) {

    }

    @Override
    public boolean isRightKeyEmpty(){
        return true;
    }

    @Override
    public void executeRightKeyEmpty(Player player) {

    }
}
