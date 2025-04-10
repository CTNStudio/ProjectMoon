package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.renderers.PmGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static ctn.project_moon.api.PmApi.ENTITY_RANGE;
import static ctn.project_moon.init.PmDataComponents.MODE_BOOLEAN;

public abstract class Weapon extends Item implements GeoItem{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final float maxDamage, minDamage;
    /**
     * 是否是特殊物品
     */
    private final boolean isSpecialTemplate;
    private GeoModel<Weapon> defaultModel;

    public Weapon(Builder builder) {
        this(builder.build(), false, builder.maxDamage, builder.minDamage);
    }

    public Weapon(Builder builder, boolean isSpecialTemplate) {
        this(builder.build(), isSpecialTemplate, builder.maxDamage, builder.minDamage);
    }

    public Weapon(Item.Properties properties, Builder builder) {
        this(properties.attributes(builder.getItemAttributeModifiers()), false, builder.maxDamage, builder.minDamage);
    }

    public Weapon(Item.Properties properties, boolean isSpecialTemplate, Builder builder) {
        this(properties.attributes(builder.getItemAttributeModifiers()), isSpecialTemplate, builder.maxDamage, builder.minDamage);
    }

    private Weapon(Item.Properties properties, boolean isSpecialTemplate, float maxDamage, float minDamage) {
        super(properties.component(MODE_BOOLEAN, false));
        this.isSpecialTemplate = isSpecialTemplate;
        this.maxDamage = maxDamage;
        this.minDamage = minDamage;
    }

    public void setDefaultModel(GeoModel<Weapon> defaultModel) {
        this.defaultModel = defaultModel;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private PmGeoItemRenderer<Weapon> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new PmGeoItemRenderer<>(defaultModel);

                return this.renderer;
            }
        });
    }

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

    public static class Builder{
        private float minDamage, maxDamage, attackSpeed, attackDistance;
        private int durability;
        private Item.Properties properties = new Item.Properties();

        public Builder(){}

        public Builder(float minDamage, float maxDamage, float attackSpeed) {
            this.minDamage = minDamage;
            this.maxDamage = maxDamage;
            this.attackSpeed = attackSpeed;
        }

        public Builder(float minDamage, float maxDamage, float attackSpeed, int durability) {
            this(minDamage, maxDamage, attackSpeed);
            this.durability = durability;
        }

        public Builder(float minDamage, float maxDamage, float attackSpeed, float attackDistance) {
            this(minDamage, maxDamage, attackSpeed);
            this.attackDistance = attackDistance;
        }

        public Builder(float minDamage, float maxDamage, float attackSpeed, float attackDistance, int durability) {
            this(minDamage, maxDamage, attackSpeed, attackDistance);
            this.durability = durability;
        }

        public Item.Properties build(){
            properties.attributes(getItemAttributeModifiers());
            if (durability > 0) {
                properties.durability(durability);
            }
            return properties;
        }

        public ItemAttributeModifiers getItemAttributeModifiers() {
            return ItemAttributeModifiers.builder()
                    .add(
                            Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(BASE_ATTACK_DAMAGE_ID, minDamage, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.HAND
                    ).add(
                            Attributes.ATTACK_SPEED,
                            new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.HAND
                    ).add(
                            Attributes.ENTITY_INTERACTION_RANGE,
                            new AttributeModifier(ENTITY_RANGE, attackDistance, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.HAND
                    ).build();
        }

        public Builder durability(int durability) {
            this.durability = durability;
            return this;
        }

        public Builder minDamage(float minDamage) {
            this.minDamage = minDamage;
            return this;
        }

        public Builder maxDamage(float maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        public Builder damage(float damage){
            this.maxDamage = damage;
            this.minDamage = damage;
            return this;
        }

        public Builder attackSpeed(float attackSpeed) {
            this.attackSpeed = attackSpeed;
            return this;
        }

        public Builder attackDistance(float attackDistance) {
            this.attackDistance = attackDistance;
            return this;
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }
    }
}
