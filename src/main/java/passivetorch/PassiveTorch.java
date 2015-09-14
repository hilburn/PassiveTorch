package passivetorch;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import passivetorch.blocks.BlockTorch;
import passivetorch.blocks.TileEntityTorch;
import passivetorch.events.ServerEventHandler;
import passivetorch.references.*;

@Mod(modid = Reference.ID, name=Reference.NAME)
public class PassiveTorch
{
    @Instance(value = Reference.ID)
    public static PassiveTorch INSTANCE;

    @Metadata(Reference.ID)
    public static ModMetadata metadata;

    public static BlockTorch torch;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        metadata = Meta.init(event.getModMetadata());
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        GameRegistry.registerBlock(torch = new BlockTorch(), "torch");
        GameRegistry.registerTileEntity(TileEntityTorch.class, BlockTorch.NAME);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        OreDictionaryHelper.registerUsefulThings();
        GameRegistry.addRecipe(new ShapedOreRecipe(torch, "WWW", "CTF", "BPI", 'W', "wool", 'C', Items.chicken, 'T', Blocks.torch, 'F', Items.feather, 'B', Items.beef, 'P', Items.porkchop, 'I', new ItemStack(Items.dye, 1, 0)));
    }
}
