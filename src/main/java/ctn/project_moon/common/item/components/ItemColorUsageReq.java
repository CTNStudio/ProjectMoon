package ctn.project_moon.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.api.UniqueList;
import ctn.project_moon.tool.PmColourTool;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmEntityAttributes.ID_ACT;
import static ctn.project_moon.tool.PmTool.i18ColorText;
import static net.minecraft.client.gui.screens.Screen.hasShiftDown;

/**
 * 物品四色属性能力使用要求
 * <p>
 * 当一个最后一个值为-1就是包括大于
 */
public final class ItemColorUsageReq implements PmToTooltip {
    public static ItemColorUsageReq empty() {
        LinkedHashMap<Type, List<Integer>> map = new LinkedHashMap<>();
        map.put(Type.FORTITUDE, new UniqueList<>());
        map.put(Type.PRUDENCE, new UniqueList<>());
        map.put(Type.TEMPERANCE, new UniqueList<>());
        map.put(Type.JUSTICE, new UniqueList<>());
        return new ItemColorUsageReq(map);
    }

    private static final String PREFIX = MOD_ID + ":data_components.item_color_usage_req.";
    public static final String All = PREFIX + "all";
    public static final String REQUIREMENT = PREFIX + "requirement";
    public static final String INTERVAL = PREFIX + "interval";
    public static final String NOT_TO_EXCEED = PREFIX + "not_to_exceed";
    public static final String NOT_LOWER_THAN = PREFIX + "not_lower_than";
    public static final String USE_CONDITION = PREFIX + "use_condition";

    private final LinkedHashMap<Type, List<Integer>> requireMap;

    private static final Codec<LinkedHashMap<Type, List<Integer>>> LEVELS_CODEC = Codec.unboundedMap(Type.CODEC, Codec.list(Codec.INT)).xmap(LinkedHashMap::new, Function.identity());
    private static final Codec<ItemColorUsageReq> FULL_CODEC = RecordCodecBuilder.create(instance -> instance
            .group(LEVELS_CODEC.fieldOf("require").forGetter(req -> req.requireMap))
            .apply(instance, ItemColorUsageReq::new));
    public static final Codec<ItemColorUsageReq> CODEC = Codec.withAlternative(FULL_CODEC, LEVELS_CODEC, ItemColorUsageReq::new);
    public static final StreamCodec<ByteBuf, ItemColorUsageReq> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(LinkedHashMap::new, Type.STREAM_CODEC, ByteBufCodecs.INT.apply(ByteBufCodecs.list())),
            ItemColorUsageReq::apply,
            ItemColorUsageReq::new
    );

    public ItemColorUsageReq(LinkedHashMap<Type, List<Integer>> map) {
        this.requireMap = map;
    }

    private static LinkedHashMap<Type, List<Integer>> apply(ItemColorUsageReq p_340784_) {
        return p_340784_.requireMap;
    }

    public List<Integer> getValue(Type attribute) {
        return getAttributeList(attribute);
    }

    /**
     * 没什么特殊要求的建议使用这个
     */
    public ItemColorUsageReq setValue(Type attribute, Rating rating) {
        assert attribute != Type.COMPOSITE_RATING || rating != Rating.EX : String.format("Composite Rating must be between I and V. Currently, it is: %s", rating);
        List<Integer> list = getAttributeList(attribute);
        list.clear();
        list.add(rating.minValue);
        list.add(-1);
        return this;
    }

    public ItemColorUsageReq setValue(Type attribute, Rating... ratings) {
        if (attribute == Type.COMPOSITE_RATING){
            for (Rating rating : ratings) {
                assert rating != Rating.EX : String.format("Composite Rating must be between I and V. Currently, it is: %s", rating);
            }
        }
        List<Integer> list = getAttributeList(attribute);
        Arrays.sort(ratings);
        list.clear();
        list.add(ratings[0].minValue);
        list.add(ratings[1].minValue);
        return this;
    }

    /**
     * 适合仅对应的值可用 -1代表没有限制
     */
    public ItemColorUsageReq setValue(Type attribute, int... values) {
        if (attribute == Type.COMPOSITE_RATING){
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

    public ItemColorUsageReq setMinValue(Type attribute, int value) {
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

    private static void validateCompositeRatingValue(Type attribute, int value) {
        assert attribute != Type.COMPOSITE_RATING || value != 6 : String.format("Composite Rating must be between [-1, 1, 2, 3, 4, 5]. Currently, it is: %d", value);
    }

    public ItemColorUsageReq setMaxValue(Type attribute, int value) {
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
        return requireMap.hashCode() * Type.values().length;
    }

    private List<Integer> getAttributeList(Type attribute) {
        if (requireMap.get(attribute) == null) {
            requireMap.put(attribute, new UniqueList<>());
        }
        return requireMap.get(attribute);
    }

    public ItemColorUsageReq clearAttributeList(Type attribute) {
        getAttributeList(attribute).clear();
        return this;
    }

    public boolean isListEmpty(List<Integer> list) {
        if (list.size() == 0) {
            return true;
        }
        if (list.size() == 1 && list.get(0) == -1) {
            return true;
        }
        return false;
    }

    // 说真的我并不清楚这些奇奇怪怪的功能到底会有多少人用，但凭借着有比没有好的原则还是加上比较好
    public Component getComponent(Type attribute, boolean detailed) {
        List<Integer> list = getAttributeList(attribute);
        if (attribute == Type.COMPOSITE_RATING) {
            detailed = false;
        }
        String color = switch (attribute) {
            case FORTITUDE -> PmColourTool.PHYSICS.getColour();
            case PRUDENCE -> PmColourTool.SPIRIT.getColour();
            case TEMPERANCE -> PmColourTool.EROSION.getColour();
            case JUSTICE -> PmColourTool.THE_SOUL.getColour();
            case COMPOSITE_RATING -> null;
        };
        MutableComponent component = attribute != Type.COMPOSITE_RATING ?
                Component.literal("").append(i18ColorText(attribute.getSerializedName(), color)) :
                Component.translatable(Type.COMPOSITE_RATING.getSerializedName());
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
            case 2 -> {
                if (list.get(0) == -1) {
                    component.append(Component.translatable(NOT_TO_EXCEED))
                            .append(" ").append(getParameterComponent(detailed, list.get(1)));
                } else if (list.get(1) == -1) {
                    component.append(Component.translatable(NOT_LOWER_THAN))
                            .append(" ").append(getParameterComponent(detailed, list.get(0)));
                } else {
                    component.append(Component.translatable(INTERVAL,
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

    private static Component getParameterComponent(boolean detailed, int value) {
        return detailed ? Component.literal(String.valueOf(value)) : Component.translatable(Rating.getRating(value).getName());
    }

    public enum Type implements StringRepresentable {
        /** 勇气 */
        FORTITUDE(0, PREFIX + "fortitude"),
        /** 谨慎 */
        PRUDENCE(1, PREFIX + "prudence"),
        /** 自律 */
        TEMPERANCE(2, PREFIX + "temperance"),
        /** 正义 */
        JUSTICE(3, PREFIX + "justice"),
        /** 综合评价 */
        COMPOSITE_RATING(4, PREFIX + "composite_rating");

        private final String name;
        private final int id;

        Type(int id, String name) {
            this.name = name;
            this.id = id;
        }

        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, type -> type.id);

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    public enum Rating {
        EX(PREFIX + "ex", 6, 101),
        V(PREFIX + "five", 5, 85),
        IV(PREFIX + "four", 4, 65),
        III(PREFIX + "three", 3, 45),
        II(PREFIX + "two", 2, 30),
        I(PREFIX + "one", 1, 1);

        private final String name;
        private final int level;
        private final int minValue;

        Rating(String name, int level, int minValue) {
            this.name = name;
            this.level = level;
            this.minValue = minValue;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public int getMinValue() {
            return minValue;
        }

        public static Rating getRating(int value) {
            Rating[] values = Rating.values();
            for (Rating rating : values) {
                if (value >= rating.minValue) {
                    return rating;
                }
            }
            return I;
        }
    }

    @Override
    public String toString() {
        return getComponent(Type.FORTITUDE, true).tryCollapseToString() + "," +
                getComponent(Type.PRUDENCE, true).tryCollapseToString() + "," +
                getComponent(Type.TEMPERANCE, true).tryCollapseToString() + "," +
                getComponent(Type.JUSTICE, true).tryCollapseToString();
    }

    public boolean isEmpty() {
        int i = 0;
        for (Type type : Type.values()) {
            if (isListEmpty(getAttributeList(type))) {
                i++;
            }
        }
        return i == Type.values().length;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, List<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if (isEmpty()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean detailed = player != null && (player.getAttributeValue(ID_ACT) == 1 || player.isCreative() && hasShiftDown());
        Type[] values = Type.values();
        tooltipAdder.add(i18ColorText(USE_CONDITION, "#AAAAAA"));
        for (Type type : values) {
            if (isListEmpty(getAttributeList(type))) {
                continue;
            }
            tooltipAdder.add(Component.literal(" ").append(getComponent(type, detailed)));
        }
    }
}
