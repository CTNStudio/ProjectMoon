package ctn.project_moon.datagen;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class DatagenParticle extends ParticleDescriptionProvider {
	public DatagenParticle(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, fileHelper);
	}

	@Override
	protected void addDescriptions() {
	}

	private void createSprite(Supplier<ParticleType<?>> type, String name) {
		sprite(type.get(), ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
	}
}
