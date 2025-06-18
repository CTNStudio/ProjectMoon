package ctn.project_moon.common.skill;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.init.PmRegistries;
import ctn.project_moon.init.PmSkills;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.skill.Skill.EMPTY_SKILL;

/// 技能堆栈
///
/// @author 尽
public final class SkillStack {
	public static final  Codec<Holder<Skill>>                                   SKILL_NON_EMPTY_CODEC      = PmRegistries.SKILL
			.holderByNameCodec()
			.validate(
					holder -> holder.equals(PmSkills.EMPTY)
							? DataResult.error(() -> "Skill must not be " + MOD_ID + "empty")
							: DataResult.success(holder)
			);
	public static final  Codec<SkillStack>                                      CODEC                      = Codec.lazyInitialized(
			() -> RecordCodecBuilder.create(
					instance -> instance.group(
							SKILL_NON_EMPTY_CODEC.fieldOf("id").forGetter(SkillStack::getSkillHolder),
							Codec.INT.fieldOf("cd").forGetter(SkillStack::getCd),
							Type.CODEC.fieldOf("type").forGetter(SkillStack::getType),
							Codec.INT.optionalFieldOf("key", -1).forGetter(SkillStack::getKey)
					).apply(instance, SkillStack::new)
			)
	);
	public static final  StreamCodec<RegistryFriendlyByteBuf, SkillStack>       OPTIONAL_STREAM_CODEC      = new StreamCodec<>() {
		private static final StreamCodec<RegistryFriendlyByteBuf, Holder<Skill>> SKILL_STREAM_CODEC = ByteBufCodecs.holderRegistry(PmRegistries.SKILL_REGISTRY_KEY);
		
		@Override
		public @NotNull SkillStack decode(@NotNull RegistryFriendlyByteBuf byteBuf) {
			Holder<Skill> holder = SKILL_STREAM_CODEC.decode(byteBuf);
			int cd = byteBuf.readVarInt();
			Type type = Type.get(byteBuf.readVarInt());
			int key = byteBuf.readVarInt();
			return new SkillStack(holder, cd, type, key);
		}
		
		@Override
		public void encode(@NotNull RegistryFriendlyByteBuf buffer, SkillStack patch) {
			SKILL_STREAM_CODEC.encode(buffer, patch.getSkillHolder());
			buffer.writeVarInt(patch.getCd());
			buffer.writeVarInt(patch.getType().ordinal());
			buffer.writeVarInt(patch.getKey());
		}
	};
	public static final  StreamCodec<RegistryFriendlyByteBuf, SkillStack>       STREAM_CODEC               = new StreamCodec<>() {
		@Override
		public @NotNull SkillStack decode(@NotNull RegistryFriendlyByteBuf buffer) {
			return SkillStack.OPTIONAL_STREAM_CODEC.decode(buffer);
		}
		
		@Override
		public void encode(@NotNull RegistryFriendlyByteBuf buffer, SkillStack stack) {
			SkillStack.OPTIONAL_STREAM_CODEC.encode(buffer, stack);
		}
	};
	public static final  StreamCodec<RegistryFriendlyByteBuf, List<SkillStack>> LIST_STREAM_CODEC          = STREAM_CODEC.apply(
			ByteBufCodecs.collection(NonNullList::createWithCapacity)
	);
	public static final  StreamCodec<RegistryFriendlyByteBuf, List<SkillStack>> OPTIONAL_LIST_STREAM_CODEC = OPTIONAL_STREAM_CODEC.apply(
			ByteBufCodecs.collection(NonNullList::createWithCapacity)
	);
	public static final  SkillStack                                             EMPTY                      = new SkillStack(EMPTY_SKILL, Type.ADDITIONAL);
	private static final Logger                                                 LOGGER                     = LogUtils.getLogger();
	private final        Skill                                                  skill;
	private final        Type                                                   type;
	private              int                                                    cd;
	private              int                                                    key;
	/// 一个额外计时，预留
	private              int                                                    tick;
	
	public SkillStack(Holder<Skill> skill, Type type) {
		this(skill.value(), type);
	}
	
	public SkillStack(Holder<Skill> skill, int cd, Type type) {
		this(skill.value(), cd, type);
	}
	
	public SkillStack(Holder<Skill> skill, int cd, Type type, int key) {
		this(skill.value(), cd, type, key);
	}
	
	public SkillStack(Skill skill, Type type) {
		this(skill, 0, type);
	}
	
	public SkillStack(Skill skill, int cd, Type type) {
		this(skill, cd, type, -1);// -1代表没有
	}
	
	public SkillStack(Skill skill, int cd, Type type, int key) {
		this.skill = skill;
		this.cd    = cd;
		this.type  = type;
		this.key   = key;
	}
	
	/// 加载
	public static Optional<SkillStack> parse(HolderLookup.Provider lookupProvider, Tag tag) {
		return CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
		            .resultOrPartial(s -> LOGGER.error("Tried to load invalid Skill: '{}'", s));
	}
	
	public static boolean validity(SkillStack skill) {
		return skill == null || skill.isEmpty();
	}
	
	public void tick(Level level, Entity entity) {
		if (cd > 0) {
			getSkill().tick(level, entity);
			cd--;
		}
	}
	
	/**
	 * 设置cd并返回老cd
	 *
	 * @param newCd cd
	 * @return 老cd
	 */
	public int setCd(int newCd) {
		if (newCd < 0) {
			newCd = 0;
		}
		int oldCd = this.cd;
		this.cd = newCd;
		return oldCd;
	}
	
	/// 保存
	public Tag save(HolderLookup.Provider provider, Tag outputTag) {
		if (this.isEmpty()) {
			throw new IllegalStateException("Cannot encode empty SkillStack");
		} else {
			// TODO 有空可以做异常处置
			return CODEC.encode(this, provider.createSerializationContext(NbtOps.INSTANCE), outputTag).getOrThrow();
		}
	}
	
	/// 获取持有人
	public Holder.Reference<Skill> getSkillHolder() {
		Skill skill = this.getSkill();
		if (skill == null) {
			skill = PmSkills.EMPTY.get();
		}
		
		return skill.builtInRegistryHolder();
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public ResourceLocation getIdName() {
		return skill.getSkillId();
	}
	
	public Component getTooltip() {
		return skill.getTooltip();
	}
	
	public ResourceLocation getIconPath() {
		return skill.getIconPath();
	}
	
	public int getMaxCd() {
		return skill.getMaxCd();
	}
	
	public int getCd() {
		return cd;
	}
	
	/// 获取百分比
	public float getCdPercentage() {
		return Math.min(100, cd * ((float) getMaxCd() / 100));
	}
	
	public boolean isEmpty() {
		return skill == null || skill.isEmpty() || skill.getSkillId() == null;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SkillStack stack)) return false;
		
		return getCd() == stack.getCd() && getSkill().equals(stack.getSkill());
	}
	
	@Override
	public int hashCode() {
		int result = getSkill().hashCode();
		result = 31 * result + getCd();
		return result;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	/// 技能的存储类型
	public enum Type implements StringRepresentable {
		/// 适用于实体本身自带的技能，死亡不会移除
		POSSESS(0, "possess"),
		/// 适用于临时技能，死亡会移除，比如武器提供的技能
		ADDITIONAL(1, "additional");
		
		public static final  Codec<Type>                CODEC        = StringRepresentable.fromEnum(Type::values);
		private static final IntFunction<Type>          BY_ID        = ByIdMap.continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final  StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, type -> type.id);
		
		private final int    id;
		private final String name;
		
		Type(int i, String name) {
			this.id   = i;
			this.name = name;
		}
		
		public static Type is(String name) {
			for (Type type : values()) {
				if (type.name.equals(name)) return type;
			}
			return null;
		}
		
		public static Type get(int id) {
			return BY_ID.apply(id);
		}
		
		public int getId() {
			return id;
		}
		
		@Override
		public @NotNull String getSerializedName() {
			return name;
		}
	}
}
