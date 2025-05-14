package ctn.project_moon.common.item.weapon.abstract_ltem;

/**
 * 远程武器
 */
public abstract class RemoteWeapon extends Weapon {
	private final boolean isConsumingBullets;

	public RemoteWeapon(Properties properties, Builder builder, boolean isConsumingBullets) {
		super(properties, true, builder);
		this.isConsumingBullets = isConsumingBullets;
	}

	public boolean isConsumingBullets() {
		return isConsumingBullets;
	}
}
