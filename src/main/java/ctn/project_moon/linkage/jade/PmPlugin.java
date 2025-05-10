package ctn.project_moon.linkage.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import static ctn.project_moon.PmMain.MOD_ID;

@WailaPlugin
public class PmPlugin implements IWailaPlugin {
	public static final ResourceLocation LEVEL = ResourceLocation.fromNamespaceAndPath(MOD_ID, "level");
	public static final ResourceLocation RESISTANCE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "resistance");

	@Override
	public void register(IWailaCommonRegistration registration) {
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(BlockLevel.INSTANCE, Block.class);
		registration.registerEntityComponent(MobEntityLevel.INSTANCE, Mob.class);
		registration.registerEntityComponent(MobEntityResistance.INSTANCE, Mob.class);
	}
}