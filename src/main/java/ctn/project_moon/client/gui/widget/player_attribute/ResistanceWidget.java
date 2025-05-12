package ctn.project_moon.client.gui.widget.player_attribute;

import ctn.project_moon.client.gui.widget.StateWidget;
import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ResistanceWidget extends StateWidget {
	public ResistanceWidget(int x, int y) {
		super(x, y, 16, 13, 233, 113, PlayerAttributeScreen.GUI);
	}

	@Override
	public void setStateV(int stateV) {
		if (stateV < 0) {
			stateV = 0;
		}
		if (stateV > 5) {
			stateV = 5;
		}
		super.setStateV(stateV);
	}

	public void setState(double state) {
		int i = 0;
		if (state < 0.5) {
			i = 1;
		} else if (state < 1.0) {
			i = 2;
		} else if (state == 1.0) {
			i = 3;
		} else if (state >= 2.0) {
			i = 5;
		} else if (state > 1.0) {
			i = 4;
		}
		setStateV(i);
	}
}
