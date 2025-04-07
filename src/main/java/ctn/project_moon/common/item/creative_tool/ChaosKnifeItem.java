package ctn.project_moon.common.item.creative_tool;

import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.item.components.PmDataComponents;
import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.common.item.components.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.init.PmDamageTypes.Types.*;
import static ctn.project_moon.init.PmDamageTypes.getDamageTypeLocation;

public class ChaosKnifeItem extends EgoCloseCombat {
    public ChaosKnifeItem(Properties properties) {
        super(properties, PHYSICS, new EgoAttribute().damage(1, 5));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        PmDamageTypes.Types s = switch (getType(itemStack.get(PmDataComponents.CURRENT_DAMAGE_TYPE))) {
            case PHYSICS -> THE_SOUL;
            case SPIRIT -> PHYSICS;
            case EROSION -> SPIRIT;
            case THE_SOUL -> EROSION;
            case null -> PHYSICS;
        };
        itemStack.set(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(s));
        return InteractionResultHolder.success(itemStack);
    }
}
