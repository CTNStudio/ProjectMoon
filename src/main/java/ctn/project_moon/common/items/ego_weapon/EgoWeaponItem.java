package ctn.project_moon.common.items.ego_weapon;

import ctn.project_moon.api.PmApi;
import ctn.project_moon.common.items.EgoItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.PmTags.PmItem.*;


public class EgoWeaponItem extends Item implements EgoItem{

    public EgoWeaponItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        levelText(tooltipComponents, stack);
        injuryType(tooltipComponents, stack);
    }

    public void levelText(List<Component> tooltipComponents, ItemStack stack) {
        TagKey<Item> levelTags = getEgoLevelTag(stack);
        if (levelTags.equals(ALEPH)) {
            tooltipComponents.add(createColorText("ALEPH", "#ff0000"));
        } else if (levelTags.equals(WAW)) {
            tooltipComponents.add(createColorText("WAW", "#8a2be2"));
        } else if (levelTags.equals(HE)) {
            tooltipComponents.add(createColorText("HE", "#ffff00"));
        } else if (levelTags.equals(TETH)) {
            tooltipComponents.add(createColorText("TETH", "#1e90ff"));
        } else if (levelTags.equals(ZAYIN)) {
            tooltipComponents.add(createColorText("ZAYIN", "#00ff00"));
        }
    }

    private @NotNull MutableComponent createColorText(String text, String color) {
        return Component.literal(text).withColor(PmApi.colorConversion(color));
    }

    private @NotNull MutableComponent createtRanslatableColorText(String text, String color) {
        return Component.translatable(text).withColor(PmApi.colorConversion(color));
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, int attackDamage, float attackSpeed) {
        return createAttributes(tier, (float)attackDamage, attackSpeed);
    }

    /**
     * Neo: Method overload to allow giving a float for damage instead of an int.
     */
    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public void injuryType(List<Component> tooltipComponents, ItemStack stack) {
        List<TagKey<Item>> damageTypesTags = egoDamageTypes(stack);
        LinkedHashMap<String, String> texts = new LinkedHashMap<>();
        for (TagKey<Item> itemTag : damageTypesTags) {
            if (itemTag.equals(PHYSICS)) {
                texts.put("physics", "#ff0000");
            } else if (itemTag.equals(SPIRIT)) {
                texts.put("spirit", "#ffffff");
            } else if (itemTag.equals(EROSION)) {
                texts.put("erosion", "#8a2be2");
            } else if (itemTag.equals(THE_SOUL)) {
                texts.put("the_soul", "#00ffff");
            }
        }
        Set<String> levelText = texts.keySet();
        if (levelText.isEmpty()) {
            return;
        }
        tooltipComponents.add(Component.translatable(MOD_ID + ".item.geo_describe.damage_type"));
        for (String level : levelText) {
            String color = texts.get(level);
            tooltipComponents.add(createtRanslatableColorText(MOD_ID + ".item.geo_describe." + level, color));
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }
}
