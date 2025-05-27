package ctn.project_moon.events;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

import javax.annotation.Nullable;

/**
 * {@link ICancellableEvent} 用于防止其他继续覆盖伤害类型
 */
public class DourColorDamageTypesEvent extends Event implements ICancellableEvent {
	@Nullable
	private final DamageContainer container;
	private final DamageSource               source;
	private       PmDamageTool.FourColorType fourColorDourColorDamageTypes;

	public DourColorDamageTypesEvent(DamageSource source, @org.jetbrains.annotations.Nullable DamageContainer container) {

		this.source = source;
		this.container = container;
	}

	public DourColorDamageTypesEvent(DamageSource source) {
		this.source = source;
		this.container = null;
	}

	public @org.jetbrains.annotations.Nullable DamageContainer getContainer() {
		return container;
	}

	public DamageSource getSource() {
		return source;
	}

	public PmDamageTool.FourColorType getDamageTypes() {
		return fourColorDourColorDamageTypes;
	}

	public void setDourColorDamageTypes(PmDamageTool.FourColorType fourColorDourColorDamageTypes) {
		this.fourColorDourColorDamageTypes = fourColorDourColorDamageTypes;
	}
}
