package ctn.project_moon.datagen;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmParticle extends ParticleDescriptionProvider {
    public PmParticle(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
//        createSprite(PmParticleTypes.PHYSICS_PARTICLE::get, "physics_particle");
//        createSprite(PmParticleTypes.PHYSICS_PARTICLE::get, "physics_particle");
    }

    private void createSprite(Supplier<ParticleType<?>> type, String name) {
        sprite(type.get(), ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
