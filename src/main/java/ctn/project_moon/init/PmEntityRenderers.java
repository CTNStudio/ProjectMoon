package ctn.project_moon.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

public class PmEntityRenderers {
    private static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = new Object2ObjectOpenHashMap<>();
    private static final Map<PlayerSkin.Model, EntityRendererProvider<AbstractClientPlayer>> PLAYER_PROVIDERS = Map.of(
            PlayerSkin.Model.WIDE, p_174098_ -> new PlayerRenderer(p_174098_, false), PlayerSkin.Model.SLIM, p_174096_ -> new PlayerRenderer(p_174096_, true)
    );
}
