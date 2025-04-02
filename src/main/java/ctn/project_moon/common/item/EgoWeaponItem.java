package ctn.project_moon.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;


public class EgoWeaponItem extends Item implements EgoItem{
    private BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> damageType;
    private final boolean isSpecialTemplate;
    public EgoWeaponItem(Properties properties, BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> damageType) {
        super(properties);
        isSpecialTemplate = false;
        this.damageType = damageType;
    }

    public EgoWeaponItem(Properties properties) {
        super(properties);
        isSpecialTemplate = true;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    // TODO
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//        if (!isSpecialTemplate) {
//            target.hurt(damageType.apply(target, attacker), 10);
//        } else {
//            target.hurt(egoDamage().apply(target, attacker), 10);
//        }
//        CompoundTag nbt = attacker.getPersistentData();
//        updateSpiritValue(attacker, -1);
    }

//    public static ItemAttributeModifiers createAttributes(Tier tier, int attackDamage, float attackSpeed) {
//        return createAttributes(tier, (float)attackDamage, attackSpeed);
//    }
//
//    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
//        return ItemAttributeModifiers.builder()
//                .add(
//                        Attributes.ATTACK_DAMAGE,
//                        new AttributeModifier(
//                                BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
//                        ),
//                        EquipmentSlotGroup.MAINHAND
//                )
//                .add(
//                        Attributes.ATTACK_SPEED,
//                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
//                        EquipmentSlotGroup.MAINHAND
//                )
//                .build();
//    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    public boolean isSpecialTemplate() {
        return isSpecialTemplate;
    }
}
