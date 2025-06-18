package ctn.project_moon.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import static ctn.project_moon.api.attr.MobGeneralAttribute.RATIONALITY_VALUE;
import static ctn.project_moon.init.PmEntityAttributes.*;

public abstract class RationalityEvent extends LivingEvent {
	public RationalityEvent(LivingEntity entity) {
		super(entity);
		entity.getAttribute(MAX_RATIONALITY);
	}
	
	public double getMaxRationalityValue() {
		return getEntity().getAttribute(MAX_RATIONALITY).getValue();
	}
	
	public double getRationalityNaturalRecoveryRate() {
		return getEntity().getAttribute(RATIONALITY_NATURAL_RECOVERY_RATE).getValue();
	}
	
	public double getRationalityRecoveryAmountValue() {
		return getEntity().getAttribute(RATIONALITY_RECOVERY_AMOUNT).getValue();
	}
	
	public double getRationalityValue() {
		return getEntity().getPersistentData().getDouble(RATIONALITY_VALUE);
	}
	
	public static class Heal extends RationalityEvent implements ICancellableEvent {
		private double amount;
		
		public Heal(LivingEntity entity, double amount) {
			super(entity);
			this.amount = amount;
		}
		
		public double getAmount() {
			return amount;
		}
		
		public void setHealAmount(double amount) {
			this.amount = amount;
		}
	}
	
	public static class Damage extends RationalityEvent implements ICancellableEvent {
		private double amount;
		
		public Damage(LivingEntity entity, double amount) {
			super(entity);
			this.amount = amount;
		}
		
		public double getAmount() {
			return amount;
		}
		
		public void setDamageAmount(double amount) {
			this.amount = amount;
		}
	}
}
