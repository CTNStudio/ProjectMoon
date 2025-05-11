package ctn.project_moon.core;

import ctn.project_moon.PmMain;
import ctn.project_moon.api.Attribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class PMRegistries {
    public static final ResourceKey<Registry<Attribute<?>>> ATTIBUTE_KEY;

    public static final Registry<Attribute<?>> ATTRIBUTE;

    static {
        ATTIBUTE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(PmMain.MOD_ID, "attibute"));

        ATTRIBUTE = new RegistryBuilder<>(ATTIBUTE_KEY).sync(true).create();
    }
}
