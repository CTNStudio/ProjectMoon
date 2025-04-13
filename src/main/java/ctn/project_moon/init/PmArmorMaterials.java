package ctn.project_moon.init;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS_TYPES = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, MOD_ID);

    // EGO护甲
    public static final Holder<ArmorMaterial> ZAYIN = register("zayin",1,2,3,1,3 ,9,
            PmSoundEvents.ARMOR_EQUIP_ZAYIN, 0.5F, 0.0F);
    public static final Holder<ArmorMaterial> TETH = register("teth",2,5,6,2,5 ,9,
            PmSoundEvents.ARMOR_EQUIP_TETH, 1.0F, 0.0F);
    public static final Holder<ArmorMaterial> HE = register("he",3,6,8,3,11 ,9,
            PmSoundEvents.ARMOR_EQUIP_HE, 2.0F, 0.0F);
    public static final Holder<ArmorMaterial> WAW = register("waw",4,7,9,4,12 ,9,
            PmSoundEvents.ARMOR_EQUIP_WAW, 3.0F, 0.1F);
    public static final Holder<ArmorMaterial> ALEPH = register("aleph",4,8,10,5,13 ,9,
            PmSoundEvents.ARMOR_EQUIP_ALEPH, 4.0F, 0.1F);

    // 其他
    public static final Holder<ArmorMaterial> SUIT = register("suit",1,2,3,1,3 ,9,
            PmSoundEvents.ARMOR_EQUIP_ZAYIN, 0.0F, 0.0F);

    public static Holder<ArmorMaterial> register(
            String name,
            int boots, int leggings, int chestplate, int helmet, int body,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance){
        return register(name, boots, leggings, chestplate, helmet, body, enchantmentValue, equipSound, toughness, knockbackResistance, () -> Ingredient.of(Items.AIR));
    }

    private static Holder<ArmorMaterial> register(
            String name,
            int boots, int leggings, int chestplate, int helmet, int body,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient) {
        return register(name, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.HELMET, helmet);
            map.put(ArmorItem.Type.BODY, body);
        }), enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
    }

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace(name)));
        return register(name, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngridient,
            List<ArmorMaterial.Layer> layers
    ) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }
        return ARMOR_MATERIALS_TYPES.register(name, () -> new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance));
    }
}
