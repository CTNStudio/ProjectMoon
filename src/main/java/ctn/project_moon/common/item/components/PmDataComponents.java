package ctn.project_moon.common.item.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.EncoderCache;

import java.util.function.UnaryOperator;

import static ctn.project_moon.PmMain.MOD_ID;

/** 物品组件 */
public class PmDataComponents{
    static final EncoderCache ENCODER_CACHE = new EncoderCache(512);
    public static final DataComponentType<Boolean> MODE_BOOLEAN = register("mode_boolean", p_330231_ -> p_330231_.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), builder.apply(DataComponentType.builder()).build());
    }
}
