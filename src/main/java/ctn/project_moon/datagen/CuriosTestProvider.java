
package ctn.project_moon.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.data.IEntitiesData;
import top.theillusivec4.curios.api.type.data.ISlotData;

import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;
import static top.theillusivec4.curios.api.type.capability.ICurio.DropRule.ALWAYS_KEEP;

public class CuriosTestProvider extends CuriosDataProvider {
    public static final String HEADWEAR_CURIOS = "ego_headwear";
    public static final String HEAD_CURIOS = "ego_head";
    public static final String HINDBRAIN_CURIOS = "ego_hindbrain";
    public static final String EYE_AREA_CURIOS = "ego_eye_area";
    public static final String FACE_CURIOS = "ego_face";
    public static final String CHEEK_CURIOS = "ego_cheek";
    public static final String MASK_CURIOS = "ego_mask";
    public static final String MOUTH_CURIOS = "ego_mouth";
    public static final String NECK_CURIOS = "ego_neck";
    public static final String CHEST_CURIOS = "ego_chest";
    public static final String HAND_CURIOS ="ego_hand";
    public static final String GLOVE_CURIOS = "ego_glove";
    public static final String RIGHT_BACK_CURIOS = "ego_right_back";
    public static final String LEFT_BACK_CURIOS = "ego_left_back";

    public static final ResourceLocation EGO_CURIOS_TAG = createTagId("ego_curios");
    public static final ResourceLocation HEADWEAR_TAG = createTagId("headwear");
    public static final ResourceLocation HEAD_TAG = createTagId("head");
    public static final ResourceLocation HINDBRAIN_TAG = createTagId("hindbrain");
    public static final ResourceLocation EYE_AREA_TAG = createTagId("eye_area");
    public static final ResourceLocation FACE_TAG = createTagId("face");
    public static final ResourceLocation CHEEK_TAG = createTagId("cheek");
    public static final ResourceLocation MASK_TAG = createTagId("mask");
    public static final ResourceLocation MOUTH_TAG = createTagId("mouth");
    public static final ResourceLocation NECK_TAG = createTagId("neck");
    public static final ResourceLocation CHEST_TAG = createTagId("chest");
    public static final ResourceLocation HAND_TAG = createTagId("hand");
    public static final ResourceLocation GLOVE_TAG = createTagId("glove");
    public static final ResourceLocation RIGHT_BACK_TAG = createTagId("right_back");
    public static final ResourceLocation LEFT_BACK_TAG = createTagId("left_back");
    public CuriosTestProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(MOD_ID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createSlot(HEADWEAR_CURIOS, HEADWEAR_TAG);
        createSlot(HEAD_CURIOS, HEAD_TAG);
        createSlot(HINDBRAIN_CURIOS, HINDBRAIN_TAG);
        createSlot(EYE_AREA_CURIOS, EYE_AREA_TAG);
        createSlot(FACE_CURIOS, FACE_TAG);
        createSlot(CHEEK_CURIOS, CHEEK_TAG);
        createSlot(MASK_CURIOS, MASK_TAG);
        createSlot(MOUTH_CURIOS, MOUTH_TAG);
        createSlot(NECK_CURIOS, NECK_TAG);
        createSlot(CHEST_CURIOS, CHEST_TAG);
        createSlot(HAND_CURIOS, HAND_TAG);
        createSlot(GLOVE_CURIOS, GLOVE_TAG);
        createSlot(RIGHT_BACK_CURIOS, RIGHT_BACK_TAG);
        createSlot(LEFT_BACK_CURIOS, LEFT_BACK_TAG);

        createSimpleEntities("player");
    }

    public ISlotData createSlot(String nameID, String icon, ResourceLocation validator) {
        return createSlot(nameID, validator).icon(ResourceLocation.fromNamespaceAndPath(MOD_ID, icon));
    }

    public ISlotData createSlot(String nameId, ResourceLocation validator) {
        return super.createSlot(nameId)
                .dropRule(ALWAYS_KEEP)
                .addValidator(validator)
                .addCosmetic(true);
    }

    public IEntitiesData createSimpleEntities(String nameID){
        return createEntities(nameID).addPlayer().addSlots(HEADWEAR_CURIOS, HEAD_CURIOS, HINDBRAIN_CURIOS, EYE_AREA_CURIOS, FACE_CURIOS, CHEEK_CURIOS, MASK_CURIOS, MOUTH_CURIOS, NECK_CURIOS, CHEST_CURIOS, HAND_CURIOS, GLOVE_CURIOS, RIGHT_BACK_CURIOS, LEFT_BACK_CURIOS);
    }

    private static String createId(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name).toString();
    }

    private static ResourceLocation createTagId(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name + "_tag");
    }
}