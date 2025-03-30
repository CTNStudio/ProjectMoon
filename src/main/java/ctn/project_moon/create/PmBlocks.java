package ctn.project_moon.create;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmBlocks{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    public static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties props){
        return BLOCKS.registerSimpleBlock(name, props);
    }
}
