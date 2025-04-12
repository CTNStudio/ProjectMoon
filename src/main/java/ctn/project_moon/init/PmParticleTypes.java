package ctn.project_moon.init;

import com.mojang.serialization.MapCodec;
import ctn.project_moon.client.particles.DamageParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);

    public static final Supplier<ParticleType<DamageParticle.Options>> DAMAGE_PARTICLE_TYPE = register("damage_particle_type", false, DamageParticle.Options.CODEC, DamageParticle.Options.STREAM_CODEC);

    private static <T extends ParticleOptions> Supplier<ParticleType<T>> register(String id, boolean overrideLimiter, MapCodec<T> mapCodec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return PARTICLE_TYPES.register(id, () -> new ParticleType<>(overrideLimiter) {
            @Override
            @NotNull
            public MapCodec<T> codec() {
                return mapCodec;
            }

            @Override
            @NotNull
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec;
            }
        });
    }
}
