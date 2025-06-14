package ctn.project_moon.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.api.UniqueList;
import ctn.project_moon.tool.PmColourTool;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.FourColorAttribute.Type.*;
import static ctn.project_moon.init.PmEntityAttributes.ID_ACT;
import static ctn.project_moon.tool.PmTool.i18ColorText;
import static net.minecraft.client.gui.screens.Screen.hasShiftDown;

/**
 * 物品四色属性能力使用要求提示
 * <p>
 * 当一个最后一个值为-1就是包括大于
 */
public class ItemColorUsageReq {
	public static final  StreamCodec<ByteBuf, ItemColorUsageReq>                      STREAM_CODEC   = StreamCodec.composite(
			ByteBufCodecs.map(LinkedHashMap::new, FourColorAttribute.Type.STREAM_CODEC, ByteBufCodecs.INT.apply(ByteBufCodecs.list())),
			ItemColorUsageReq::apply,
			ItemColorUsageReq::new
	);
	private static final String                                                       PREFIX         = MOD_ID + ":data_components.item_color_usage_req.";
	public static final  String                                                       All            = PREFIX + "all";
	public static final  String                                                       REQUIREMENT    = PREFIX + "requirement";
	public static final  String                                                       INTERVAL       = PREFIX + "interval";
	public static final  String                                                       NOT_TO_EXCEED  = PREFIX + "not_to_exceed";
	public static final  String                                                       NOT_LOWER_THAN = PREFIX + "not_lower_than";
	public static final  String                                                       USE_CONDITION  = PREFIX + "use_condition";
	private static final Codec<LinkedHashMap<FourColorAttribute.Type, List<Integer>>> LEVELS_CODEC   = Codec.unboundedMap(FourColorAttribute.Type.CODEC, Codec.list(Codec.INT)).xmap(LinkedHashMap::new, Function.identity());
	private static final Codec<ItemColorUsageReq>                                     FULL_CODEC     = RecordCodecBuilder.create(instance -> instance
			.group(LEVELS_CODEC.fieldOf("require").forGetter(req -> req.requireMap))
			.apply(instance, ItemColorUsageReq::new));
	public static final  Codec<ItemColorUsageReq>                                     CODEC          = Codec.withAlternative(FULL_CODEC, LEVELS_CODEC, ItemColorUsageReq::new);
	private final        LinkedHashMap<FourColorAttribute.Type, List<Integer>>        requireMap;

	public ItemColorUsageReq(LinkedHashMap<FourColorAttribute.Type, List<Integer>> map) {
		this.requireMap = map;
	}

	private static LinkedHashMap<FourColorAttribute.Type, List<Integer>> apply(ItemColorUsageReq p_340784_) {
		return p_340784_.requireMap;
	}

	public static ItemColorUsageReq empty() {
		LinkedHashMap<FourColorAttribute.Type, List<Integer>> map = new LinkedHashMap<>();
		map.put(FORTITUDE, new UniqueList<>());
		map.put(PRUDENCE, new UniqueList<>());
		map.put(TEMPERANCE, new UniqueList<>());
		map.put(JUSTICE, new UniqueList<>());
		map.put(COMPOSITE_RATING, new UniqueList<>());
		return new ItemColorUsageReq(map);
	}

	private static void validateCompositeRatingValue(FourColorAttribute.Type attribute, int value) {
		assert attribute != FourColorAttribute.Type.COMPOSITE_RATING || value != 6 : String.format("Composite Rating must be between [-1, 1, 2, 3, 4, 5]. Currently, it is: %d", value);
	}

	private static Component getParameterComponent(boolean detailed, int value) {
		return detailed ? Component.literal(String.valueOf(value)) : Component.translatable(FourColorAttribute.Rating.getRating(value).getIdName());
	}

	public List<Integer> getValue(FourColorAttribute.Type attribute) {
		return getAttributeList(attribute);
	}

	//没什么特殊要求的建议使用这个
	/// 至少
	public ItemColorUsageReq setNotToExceedValue(FourColorAttribute.Type attribute, FourColorAttribute.Rating rating) {
		assert attribute != FourColorAttribute.Type.COMPOSITE_RATING || rating != FourColorAttribute.Rating.EX :
				String.format("Composite Rating must be between I and V. Currently, it is: %s", rating);
		List<Integer> list = getAttributeList(attribute);
		list.clear();
		list.add(rating.getMinValue());
		list.add(-1);
		return this;
	}

	/// 至多
	public ItemColorUsageReq setNotLowerThanValue(FourColorAttribute.Type attribute, FourColorAttribute.Rating rating) {
		assert attribute != FourColorAttribute.Type.COMPOSITE_RATING || rating != FourColorAttribute.Rating.EX :
				String.format("Composite Rating must be between I and V. Currently, it is: %s", rating);
		List<Integer> list = getAttributeList(attribute);
		list.clear();
		list.add(-1);
		list.add(rating.getMinValue());
		return this;
	}


	/// 至少 推荐使用方法
	public static ItemColorUsageReq notToExceed(FourColorAttribute.Rating fortitudeRating, FourColorAttribute.Rating prudenceRating, FourColorAttribute.Rating temperanceRating, FourColorAttribute.Rating justiceRating, FourColorAttribute.Rating compositeRating) {
		final ItemColorUsageReq empty = ItemColorUsageReq.empty();
		if (fortitudeRating != null) {
			empty.setNotToExceedValue(FourColorAttribute.Type.FORTITUDE, fortitudeRating);
		}
		if (prudenceRating != null) {
			empty.setNotToExceedValue(FourColorAttribute.Type.PRUDENCE, prudenceRating);
		}
		if (temperanceRating != null) {
			empty.setNotToExceedValue(FourColorAttribute.Type.TEMPERANCE, temperanceRating);
		}
		if (justiceRating != null) {
			empty.setNotToExceedValue(FourColorAttribute.Type.JUSTICE, justiceRating);
		}
		if (compositeRating != null) {
			empty.setNotToExceedValue(FourColorAttribute.Type.COMPOSITE_RATING, compositeRating);
		}
		return empty;
	}

	/// 至多
	public static ItemColorUsageReq notLowerThan(FourColorAttribute.Rating fortitudeRating, FourColorAttribute.Rating prudenceRating, FourColorAttribute.Rating temperanceRating, FourColorAttribute.Rating justiceRating, FourColorAttribute.Rating compositeRating) {
		final ItemColorUsageReq empty = ItemColorUsageReq.empty();
		if (fortitudeRating != null) {
			empty.setNotLowerThanValue(FourColorAttribute.Type.FORTITUDE, fortitudeRating);
		}
		if (prudenceRating != null) {
			empty.setNotLowerThanValue(FourColorAttribute.Type.PRUDENCE, prudenceRating);
		}
		if (temperanceRating != null) {
			empty.setNotLowerThanValue(FourColorAttribute.Type.TEMPERANCE, temperanceRating);
		}
		if (justiceRating != null) {
			empty.setNotLowerThanValue(FourColorAttribute.Type.JUSTICE, justiceRating);
		}
		if (compositeRating != null) {
			empty.setNotLowerThanValue(FourColorAttribute.Type.COMPOSITE_RATING, compositeRating);
		}
		return empty;
	}

	public ItemColorUsageReq setValue(FourColorAttribute.Type attribute, FourColorAttribute.Rating... ratings) {
		if (attribute == FourColorAttribute.Type.COMPOSITE_RATING) {
			for (FourColorAttribute.Rating rating : ratings) {
				assert rating != FourColorAttribute.Rating.EX : String.format("Composite Rating must be between I and V. Currently, it is: %s", rating);
			}
		}
		List<Integer> list = getAttributeList(attribute);
		Arrays.sort(ratings);
		list.clear();
		list.add(ratings[0].getMinValue());
		list.add(ratings[1].getMinValue());
		return this;
	}

	/**
	 * 适合仅对应的值可用 -1代表没有限制
	 * <p>
	 * 注意{@link FourColorAttribute.Type#COMPOSITE_RATING}只有6个值（-1、1、2、3、4、5）
	 */
	public ItemColorUsageReq setValue(FourColorAttribute.Type attribute, int... values) {
		if (attribute == FourColorAttribute.Type.COMPOSITE_RATING) {
			for (int value : values) {
				assert value != 6 : String.format("Composite Rating must be between [-1, 1, 2, 3, 4, 5]. Currently, it is: %d", value);
			}
		}
		List<Integer> list = getAttributeList(attribute);
		list.clear();
		if (values.length == 0) {
			list.add(-1);
			return this;
		}
		Arrays.sort(values);
		for (int value : values) {
			if (value == -1) {
				list.clear();
				list.add(-1);
				return this;
			}
			list.add(value);
		}
		return this;
	}

	public ItemColorUsageReq setMinValue(FourColorAttribute.Type attribute, int value) {
		validateCompositeRatingValue(attribute, value);
		List<Integer> list = getAttributeList(attribute);
		list.clear();
		if (value == 0 || value == -1) {
			list.add(-1);
			return this;
		}
		list.add(value);
		list.add(-1);
		return this;
	}

	public ItemColorUsageReq setMaxValue(FourColorAttribute.Type attribute, int value) {
		validateCompositeRatingValue(attribute, value);
		List<Integer> list = getAttributeList(attribute);
		list.clear();
		if (value == 0 || value == -1) {
			list.add(-1);
			return this;
		}
		list.add(-1);
		list.add(value);
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof ItemColorUsageReq itemColorUsageReq) {
			return itemColorUsageReq.requireMap.equals(requireMap);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return requireMap.hashCode() * FourColorAttribute.Type.values().length;
	}

	private List<Integer> getAttributeList(FourColorAttribute.Type attribute) {
		if (requireMap.get(attribute) == null) {
			requireMap.put(attribute, new UniqueList<>());
		}
		return requireMap.get(attribute);
	}

	public ItemColorUsageReq clearAttributeList(FourColorAttribute.Type attribute) {
		getAttributeList(attribute).clear();
		return this;
	}

	/**
	 * 列表是否为空
	 */
	public boolean isListEmpty(List<Integer> list) {
		if (list.size() == 0) {
			return true;
		}
		if (list.size() == 1 && list.get(0) == -1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取属性对应的使用要求
	 */
	public Component analysisUsageReq(FourColorAttribute.Type attribute, boolean detailed) {
		List<Integer> list = getAttributeList(attribute);
		if (attribute == FourColorAttribute.Type.COMPOSITE_RATING) {
			detailed = false;
		}
		String color = switch (attribute) {
			case FORTITUDE -> PmColourTool.PHYSICS.getColour();
			case PRUDENCE -> PmColourTool.SPIRIT.getColour();
			case TEMPERANCE -> PmColourTool.EROSION.getColour();
			case JUSTICE -> PmColourTool.THE_SOUL.getColour();
			case COMPOSITE_RATING -> null;
		};

		// 根据类型生成
		MutableComponent component = attribute != FourColorAttribute.Type.COMPOSITE_RATING ?
				Component.literal("").append(i18ColorText(attribute.getSerializedName(), color)) :
				Component.translatable(FourColorAttribute.Type.COMPOSITE_RATING.getSerializedName());
		if (isListEmpty(list)) {
			component.append(Component.translatable(All));
			return component;
		}
		switch (list.size()) {
			case 1 -> {
				if (list.get(0) != -1) {
					component.append(Component.translatable(REQUIREMENT)).append(" ")
							.append(getParameterComponent(detailed, list.get(0)));
				}
			}
			// 说真的我并不清楚这些奇奇怪怪的功能到底会有多少人用，但凭借着有比没有好的原则还是加上比较好
			case 2 -> {
				if (list.get(0) == -1) {
					component.append(Component.translatable(NOT_TO_EXCEED))
							.append(" ").append(getParameterComponent(detailed, list.get(1)));
				} else if (list.get(1) == -1) {
					component.append(Component.translatable(NOT_LOWER_THAN))
							.append(" ").append(getParameterComponent(detailed, list.get(0)));
				} else {
					component.append(Component.translatable(
							INTERVAL,
							getParameterComponent(detailed, list.get(0)),
							getParameterComponent(detailed, list.get(1))));
				}
			}
			default -> {
				component.append(Component.translatable(REQUIREMENT));
				for (int i : list) {
					component.append(" ").append(getParameterComponent(detailed, i));
				}
			}
		}
		return component;
	}


	public boolean isAccord(FourColorAttribute.Type attribute, int value) {
		List<Integer> attributeUsageReq = getAttributeList(attribute);
		if (isListEmpty(attributeUsageReq)) {
			return true;
		}
		final int i1 = attributeUsageReq.get(0);
		return switch (attributeUsageReq.size()) {
			case 1 -> i1 == value;
			// 在这 -1 等于 <= 或 >=
			case 2 -> {
				final int i2 = attributeUsageReq.get(1);
				if (i1 == -1 && i2 == -1) {
					yield true;
				} else if (i1 == -1) {
					yield value <= i2;
				} else if (i2 == -1) {
					yield value >= i1;
				} else {
					yield i2 >= value && value >= i1;
				}
			}
			// 在这理论上不应该有-1
			default -> {
				for (int i : attributeUsageReq) {
					if (i == value) yield true;
				}
				yield false;
			}
		};
	}

	@Override
	public String toString() {
		return analysisUsageReq(FORTITUDE, true).tryCollapseToString() + "," +
		       analysisUsageReq(PRUDENCE, true).tryCollapseToString() + "," +
		       analysisUsageReq(TEMPERANCE, true).tryCollapseToString() + "," +
		       analysisUsageReq(JUSTICE, true).tryCollapseToString();
	}

	public boolean isEmpty() {
		int i = 0;
		for (FourColorAttribute.Type type : FourColorAttribute.Type.values()) {
			if (isListEmpty(getAttributeList(type))) {
				i++;
			}
		}
		return i == FourColorAttribute.Type.values().length;
	}

	public void getToTooltip(List<Component> components) {
		if (isEmpty()) {
			return;
		}
		components.add(i18ColorText(USE_CONDITION, "#AAAAAA"));
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		boolean detailed = player != null && (player.getAttributeValue(ID_ACT) == 1 || player.isCreative() && hasShiftDown());
		for (FourColorAttribute.Type type : FourColorAttribute.Type.values()) {
			if (isListEmpty(getAttributeList(type))) {
				continue;
			}
			components.add(Component.literal(" ").append(analysisUsageReq(type, detailed)));
		}
	}

	public List<Integer> getFortitudeUsageReq() {
		return getAttributeList(FORTITUDE);
	}

	public List<Integer> getPrudenceUsageReq() {
		return getAttributeList(PRUDENCE);
	}

	public List<Integer> getTemperanceUsageReq() {
		return getAttributeList(TEMPERANCE);
	}

	public List<Integer> getJusticeUsageReq() {
		return getAttributeList(JUSTICE);
	}

	public List<Integer> getCompositeRatingUsageReq() {
		return getAttributeList(COMPOSITE_RATING);
	}
}
