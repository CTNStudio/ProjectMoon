package ctn.project_moon.common.item;

import ctn.project_moon.common.renderers.PmGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
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

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PmApi.ENTITY_RANGE;
import static ctn.project_moon.common.item.components.PmDataComponents.MODE_BOOLEAN;


public abstract class EgoWeaponItem extends Item implements EgoItem, GeoItem, AnimItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final float maxDamage, minDamage;
    /**
     * 是否是特殊物品
     */
    private final boolean isSpecialTemplate;
    private GeoModel<EgoWeaponItem> defaultModel;

    public EgoWeaponItem(Properties properties) {
        this(properties, false, 0, 0, 0, 0);
    }

    public EgoWeaponItem(Properties properties, float maxDamage, float minDamage, float attackSpeed) {
        this(properties, false, maxDamage, minDamage, attackSpeed, 0);
    }

    public EgoWeaponItem(Properties properties, Boolean isSpecialTemplate, float maxDamage, float minDamage, float attackSpeed) {
        this(properties, isSpecialTemplate, maxDamage, minDamage, attackSpeed, 0);
    }

    public EgoWeaponItem(Properties properties, Boolean isSpecialTemplate, float maxDamage, float minDamage, float attackSpeed, float attackDistance) {
        super(properties.component(MODE_BOOLEAN, false).attributes(createAttributes(maxDamage, attackSpeed, attackDistance)));
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

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed){
        return createAttributes(attackDamage, attackSpeed, 0);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed, float attackDistance) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                ).add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ENTITY_RANGE, attackDistance, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                )
                .build();
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
}
