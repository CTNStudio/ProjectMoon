package ctn.project_moon.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static ctn.project_moon.PmMain.MOD_ID;


public class PmBlockState extends BlockStateProvider {
    public PmBlockState(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
