package ctn.project_moon.common.animation_controller;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class PmArmorAnimationController<T extends GeoAnimatable> extends AnimationController<T> {
	protected final Supplier<Item> boots;
	protected final Supplier<Item> leggings;
	protected final Supplier<Item> chestplate;
	protected final Supplier<Item> helmet;
	
	public PmArmorAnimationController(T animatable, int transitionTickTime, Supplier<Item> boots, Supplier<Item> leggings, Supplier<Item> chestplate, Supplier<Item> helmet) {
		super(
				animatable, transitionTickTime, (state) -> {
					// Apply our generic idle animation.
					// Whether it plays or not is decided down below.
					state.getController().setAnimation(DefaultAnimations.IDLE);
					
					// Let's gather some data from the state to use below
					// This is the entity that is currently wearing/holding the item
					Entity entity = state.getData(DataTickets.ENTITY);
					
					// We'll just have ArmorStands always animate, so we can return here
					if (entity instanceof ArmorStand)
						return PlayState.CONTINUE;
					
					// For this example, we only want the animation to play if the entity is wearing all pieces of the armor
					// Let's collect the armor pieces the entity is currently wearing
					Set<Item> wornArmor = new ObjectOpenHashSet<>();
					
					if (entity != null) {
						for (ItemStack stack : Objects.requireNonNull(entity.getControllingPassenger()).getArmorSlots()) {
							// We can stop immediately if any of the slots are empty
							if (stack.isEmpty())
								return PlayState.STOP;
							
							wornArmor.add(stack.getItem());
						}
					}
					List<Item> ToInclude = new ArrayList<>();
					if (boots != null) {
						ToInclude.add(boots.get());
					}
					if (leggings != null) {
						ToInclude.add(leggings.get());
					}
					if (chestplate != null) {
						ToInclude.add(chestplate.get());
					}
					if (helmet != null) {
						ToInclude.add(helmet.get());
					}
					// Check each of the pieces match our set
					boolean isFullSet = wornArmor.containsAll(ToInclude);
					
					// Play the animation if the full set is being worn, otherwise stop
					return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
				});
		this.boots      = boots;
		this.leggings   = leggings;
		this.chestplate = chestplate;
		this.helmet     = helmet;
	}
}
