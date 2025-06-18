package ctn.project_moon.mixin.client;

import ctn.project_moon.mixin_extend.IModParticleEngine$MutableSpriteSet;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(targets = "net.minecraft.client.particle.ParticleEngine$MutableSpriteSet")
@Implements(@Interface(iface = IModParticleEngine$MutableSpriteSet.class, prefix = "projectMoonInt$"))
public abstract class ParticleEngine$MutableSpriteSetMixin implements SpriteSet, IModParticleEngine$MutableSpriteSet {
	@Shadow
	private List<TextureAtlasSprite> sprites;
	
	@Unique
	public List<TextureAtlasSprite> projectMoonInt$getSprites() {
		return sprites;
	}
}
