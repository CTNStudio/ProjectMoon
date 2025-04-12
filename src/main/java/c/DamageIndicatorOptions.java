package c;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;


// 除了显示数字还能显示“美味...” “致命失误！”
public record DamageIndicatorOptions(Component text, boolean big, Type type) implements ParticleOptions {
    @Override
    @NotNull
    public ParticleType<?> getType(){
//        return DAMAGE_INDICATOR.get();
        return null;
    }

    public static final MapCodec<DamageIndicatorOptions> CODEC = RecordCodecBuilder.mapCodec(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            ComponentSerialization.CODEC.fieldOf("text").forGetter((thisOptions) -> thisOptions.text),
            Codec.BOOL.fieldOf("big").forGetter(options -> options.big),
            Codec.INT.fieldOf("type").forGetter(options -> options.type.ordinal())
        ).apply(thisOptionsInstance, (text,big,type)-> new DamageIndicatorOptions(text, big, Type.values()[type]))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageIndicatorOptions> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC, opt -> opt.text,
        ByteBufCodecs.BOOL, opt -> opt.big,
        ByteBufCodecs.VAR_INT, opt -> opt.type.ordinal(),
        (text, big, type) -> new DamageIndicatorOptions(text, big, Type.values()[type])
    );

    public enum Type{
        DAMAGE,HEAL,OTHER
    }

}
