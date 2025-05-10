package ctn.project_moon.init;

import com.mojang.serialization.Codec;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.EncoderCache;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 物品组件
 */
public class PmItemDataComponents {
	public static final DeferredRegister<DataComponentType<?>> ITEM_DATA_COMPONENT_REGISTER = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MOD_ID);

	public static final Supplier<DataComponentType<Boolean>> MODE_BOOLEAN = recordBoolean("mode_boolean");
	/**
	 * 是否正在受到抑制器的影响属性
	 */
	public static final Supplier<DataComponentType<Boolean>> IS_RESTRAIN = recordBoolean("is_restrain");
	/**
	 * 此组件用于近战EGO判断伤害类型
	 */
	public static final Supplier<DataComponentType<String>> CURRENT_DAMAGE_TYPE = recordComponent("current_damage_type");
	/** 物品四色属性能力使用要求 */
	public static final Supplier<DataComponentType<ItemColorUsageReq>> ITEM_COLOR_USAGE_REQ =
			register("item_color_usage_req", builder -> builder.persistent(ItemColorUsageReq.CODEC).networkSynchronized(ItemColorUsageReq.STREAM_CODEC).cacheEncoding());

	private static final EncoderCache ENCODER_CACHE = new EncoderCache(512);

	public static Supplier<DataComponentType<Boolean>> recordBoolean(String name) {
		return register(name, builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
	}

	public static Supplier<DataComponentType<String>> recordComponent(String name) {
		return register(name, builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
	}

	public static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return register(name, () -> builder.apply(DataComponentType.builder()).build());
	}

	public static <B extends DataComponentType<?>> DeferredHolder<DataComponentType<?>, B> register(String name, Supplier<? extends B> builder) {
		return ITEM_DATA_COMPONENT_REGISTER.register("data_components." + name, builder);
	}
}
