package ctn.project_moon.init;

import com.mojang.serialization.MapCodec;
import ctn.project_moon.client.particles.TextParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

/** 粒子类型 */
public class PmParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);
	
	public static final Supplier<ParticleType<TextParticle.Options>> TEXT_PARTICLE_TYPE =
			register("text_particle", false, TextParticle.Options.CODEC, TextParticle.Options.STREAM_CODEC);
	
	private static <T extends ParticleOptions> Supplier<ParticleType<T>> register(String id,
			boolean overrideLimiter,
			MapCodec<T> mapCodec,
			StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
		return PARTICLE_TYPE_REGISTER.register(
				id, () -> new ParticleType<>(overrideLimiter) {
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
