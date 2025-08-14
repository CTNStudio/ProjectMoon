package ctn.project_moon.client.gui;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * Hud层
 */
public class PmHudLayers {
	public static final ResourceLocation PLAYER_RATIONALITY = getLayers("rationality");
	public static final ResourceLocation PLAYER_SKILL       = getLayers("skills");
	
	private static @NotNull ResourceLocation getLayers(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}
}
