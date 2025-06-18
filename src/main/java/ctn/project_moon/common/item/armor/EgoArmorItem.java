package ctn.project_moon.common.item.armor;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

public class EgoArmorItem extends PmArmorItem {
	
	public EgoArmorItem(Builder builder) {
		this(builder.build(), builder);
	}
	
	public EgoArmorItem(Properties properties, Builder builder) {
		super(properties.component(IS_RESTRAIN, false), builder);
	}
}
