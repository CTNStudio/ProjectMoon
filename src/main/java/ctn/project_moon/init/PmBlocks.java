package ctn.project_moon.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties props) {
        return BLOCKS.registerSimpleBlock(name, props);
    }
}
