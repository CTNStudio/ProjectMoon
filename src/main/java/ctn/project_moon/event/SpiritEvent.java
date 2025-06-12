package ctn.project_moon.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_VALUE;
import static ctn.project_moon.init.PmEntityAttributes.*;

public abstract class SpiritEvent extends LivingEvent {
	public SpiritEvent(LivingEntity entity) {
		super(entity);
		entity.getAttribute(MAX_SPIRIT);
	}

	public double getMaxSpiritValue() {
		return getEntity().getAttribute(MAX_SPIRIT).getValue();
	}

	public double getSpiritNaturalRecoveryRate() {
		return getEntity().getAttribute(SPIRIT_NATURAL_RECOVERY_RATE).getValue();
	}

	public double getSpiritRecoveryAmountValue() {
		return getEntity().getAttribute(SPIRIT_RECOVERY_AMOUNT).getValue();
	}

	public double getSpiritValue() {
		return getEntity().getPersistentData().getDouble(SPIRIT_VALUE);
	}

	public static class Heal extends SpiritEvent implements ICancellableEvent {
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

	public static class Damage extends SpiritEvent implements ICancellableEvent {
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
