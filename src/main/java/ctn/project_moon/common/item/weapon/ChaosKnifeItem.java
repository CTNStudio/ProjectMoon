package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.weapon.ego.CloseCombatEgo;
import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.init.PmItemDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmDamageTypes.Types.*;
import static ctn.project_moon.init.PmDamageTypes.getDamageTypeLocation;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.tool.PmTool.createColorText;
import static ctn.project_moon.tool.PmTool.i18ColorText;

public class ChaosKnifeItem extends CloseCombatEgo {
    public ChaosKnifeItem(Properties properties) {
        super(properties, new Weapon.Builder().minDamage(6).maxDamage(7).attackSpeed(-1.4F), PHYSICS);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        PmDamageTypes.Types s = switch (getType(itemStack.get(PmItemDataComponents.CURRENT_DAMAGE_TYPE))) {
            case PHYSICS -> THE_SOUL;
            case SPIRIT -> PHYSICS;
            case EROSION -> SPIRIT;
            case THE_SOUL -> EROSION;
            case null -> PHYSICS;
        };
        itemStack.set(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(s));
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
