package ctn.project_moon.init;

import ctn.project_moon.common.skill.Skill;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class PmRegistries {
	public static final ResourceKey<Registry<Skill>> SKILL_REGISTRY_KEY = ResourceKey.createRegistryKey(getPath("spells"));
	public static final Registry<Skill>              SKILL              = new RegistryBuilder<>(SKILL_REGISTRY_KEY).sync(true).defaultKey(getPath("empty")).create();

//	// Alternatively:
//	@SubscribeEvent
//	public static void register(RegisterEvent event) {
//		event.register(SKILL_REGISTRY_KEY, registry -> {});
//	}
	
	@SubscribeEvent
	public static void registrar(NewRegistryEvent event) {
		event.register(SKILL);
	}
	
	private static @NotNull ResourceLocation getPath(String spells) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, spells);
	}
}
