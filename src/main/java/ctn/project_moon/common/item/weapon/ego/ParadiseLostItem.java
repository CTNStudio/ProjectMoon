package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.events.player.PlayerAnimEvents;
import joptsimple.BuiltinHelpFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static ctn.project_moon.common.item.AnimItem.createAnim;

public class ParadiseLostItem extends SpecialEgoWeapon implements AnimAttackItem {
    public ParadiseLostItem(Weapon.Builder builder) {
        super(builder);
        setDefaultModel(new PmGeoItemModel<>("paradise_lost"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (Minecraft.getInstance().player != null && PlayerAnimEvents.getInput(Minecraft.getInstance().player.input)) {
            return super.use(level, player, usedHand);
        }
        createAnim(player, "animation.project_moon.paradise_lost.attack", 0);
        CompoundTag nbt = player.getPersistentData();
        nbt.putFloat(TemporaryAttribute.PLAYER_RECORD_SPEED, player.getSpeed());
        nbt.putBoolean(TemporaryAttribute.PLAYER_SPECIAL_WEAPON_ATTACK, true);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
        return super.use(level, player, usedHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!(livingEntity instanceof Player player)){
            return;
        }
        trigger(level, player, stack);
        super.onUseTick(level, livingEntity, stack, 1000);
    }

    @Override
    public int freMovementTick() {
        return 9;
    }

    public int triggerTick(){
        return 8;
    }

    @Override
    public void trigger(Level level, LivingEntity livingEntity, ItemStack stack) {
        if (level.isClientSide){
            return;
        }
        livingEntity.sendSystemMessage(Component.literal("触发了"));
    }

    @Override
    public boolean isRightKeyEmpty(){
        return true;
    }

    @Override
    public void executeRightKeyEmpty(Player player) {

    }
}
