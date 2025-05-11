package ctn.project_moon.client.gui.widget;

import net.minecraft.client.gui.components.Button;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class PmOnPressAbstract implements Button.OnPress {
	private final Button.OnPress onPress;

	public PmOnPressAbstract(Button.OnPress onPress) {
		this.onPress = onPress;
	}

	@Override
	public void onPress(Button button) {
		on(button);
		onPress.onPress(button);
	}

	public abstract void on(Button button);

	public static class PmOnPress extends PmOnPressAbstract {
		public PmOnPress(Button.OnPress onPress) {
			super(onPress);
		}

		@Override
		public void on(Button button) {
		}
	}
}
