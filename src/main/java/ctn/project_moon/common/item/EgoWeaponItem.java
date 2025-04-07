package ctn.project_moon.common.item;

import ctn.project_moon.common.renderers.PmGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static ctn.project_moon.common.item.components.PmDataComponents.MODE_BOOLEAN;


public abstract class EgoWeaponItem extends Item implements EgoItem, GeoItem, AnimItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final float maxDamage, minDamage;
    private final boolean isSpecialTemplate;
    private GeoModel<EgoWeaponItem> defaultModel;

    public EgoWeaponItem(Properties properties, EgoAttribute egoAttribute) {
        this(properties, false, egoAttribute.maxDamage, egoAttribute.minDamage);
    }

    public EgoWeaponItem(Properties properties) {
        this(properties, true, 0, 0);
    }

    private EgoWeaponItem(Properties properties,
                          Boolean isSpecialTemplate,
                          float maxDamage, float minDamage) {
        super(properties.component(MODE_BOOLEAN, false));
        this.isSpecialTemplate = isSpecialTemplate;
        this.maxDamage = maxDamage;
        this.minDamage = minDamage;
    }

    public void setDefaultModel(GeoModel<EgoWeaponItem> defaultModel) {
        this.defaultModel = defaultModel;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private PmGeoItemRenderer<EgoWeaponItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new PmGeoItemRenderer<>(defaultModel);

                return this.renderer;
            }
        });
    }

//    @Override
//    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//        return true;
//    }

//    // TODO 未完成
//    @Override
//    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//        if (isSpecialTemplate) {
//            return;
//        }
//        target.hurt(damageType.apply(target, attacker), 10);
//    }

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

    public float getMinDamage() {
        return minDamage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static class EgoAttribute {
        private float maxDamage = 1, minDamage = 1;

        public static EgoAttribute builder() {
            return new EgoAttribute();
        }

        public EgoAttribute maxDamage(float maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        public EgoAttribute minDamage(float minDamage) {
            this.minDamage = minDamage;
            return this;
        }

        public EgoAttribute damage(float maxDamage, float minDamage) {
            this.maxDamage = maxDamage;
            this.minDamage = minDamage;
            return this;
        }

        public EgoAttribute damage(float damage) {
            return damage(damage, damage);
        }
    }

}
