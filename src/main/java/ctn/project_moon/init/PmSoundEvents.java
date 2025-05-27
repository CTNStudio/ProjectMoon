package ctn.project_moon.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;

/** 声音类型 */
public class PmSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUND_EVENT_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MOD_ID);

	public static final Holder<SoundEvent> ARMOR_EQUIP_ZAYIN = registerForHolder("equip_zayin", "item.armor.equip_zayin");
	public static final Holder<SoundEvent> ARMOR_EQUIP_TETH = registerForHolder("equip_teth", "item.armor.equip_teth");
	public static final Holder<SoundEvent> ARMOR_EQUIP_HE = registerForHolder("equip_he", "item.armor.equip_he");
	public static final Holder<SoundEvent> ARMOR_EQUIP_WAW = registerForHolder("equip_waw", "item.armor.equip_waw");
	public static final Holder<SoundEvent> ARMOR_EQUIP_ALEPH = registerForHolder("equip_aleph", "item.armor.equip_aleph");

	private static DeferredHolder<SoundEvent, SoundEvent> registerForHolder(String name, String location) {
		return SOUND_EVENT_TYPE_REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, location)));
	}
}
