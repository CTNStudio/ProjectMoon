package ctn.project_moon.common.item.armor.ego;

import ctn.project_moon.common.item.armor.PmArmor;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

public class EgoArmor extends PmArmor {

	public EgoArmor(Builder builder) {
		this(builder.build(), builder);
	}

	public EgoArmor(Properties properties, Builder builder) {
		super(properties.component(IS_RESTRAIN, false), builder);
	}
}
