package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon;
import ctn.project_moon.init.PmItemDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static ctn.project_moon.api.tool.PmDamageTool.FourColorType.*;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;

public class ChaosKnifeItem extends CloseEgoWeapon {
	public ChaosKnifeItem(Properties properties) {
		super(properties, new Builder().minDamage(6).maxDamage(7).attackSpeed(-1.4F), PHYSICS);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
		ItemStack itemStack = player.getItemInHand(usedHand);
		PmDamageTool.FourColorType s = switch (getType(itemStack.get(PmItemDataComponents.CURRENT_DAMAGE_TYPE))) {
			case PHYSICS -> THE_SOUL;
			case SPIRIT -> PHYSICS;
			case EROSION -> SPIRIT;
			case THE_SOUL -> EROSION;
			case null -> PHYSICS;
		};
		itemStack.set(CURRENT_DAMAGE_TYPE, s.getLocationString());
		return InteractionResultHolder.success(itemStack);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
	}
}
