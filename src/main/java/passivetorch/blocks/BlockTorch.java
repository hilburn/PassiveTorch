package passivetorch.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockTorch extends Block implements ITileEntityProvider
{
    public static final String NAME = "passivetorch:torch";
    @SideOnly(Side.CLIENT)
    IIcon iconTop;
    @SideOnly(Side.CLIENT)
    IIcon iconBase;

    public BlockTorch()
    {
        super(Material.circuits);
        setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        setHardness(1.2f);
        setLightLevel(1f);
        setLightOpacity(0);
        setBlockName(NAME);
        setBlockTextureName(NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityTorch();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        double dx = (rand.nextDouble() * 2.0D - 1.0D) * 0.125D;
        double dy = (rand.nextDouble() * 2.0D - 1.0D) * 0.0625D;
        double dz = (rand.nextDouble() * 2.0D - 1.0D) * 0.125D;
        world.spawnParticle("smoke", (x + 0.5D + dx), ((y + 1) + dy), (z + 0.5D + dz), 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", (x + 0.5D + dx), ((y + 1) + dy), (z + 0.5D + dz), 0.0D, 0.0D, 0.0D);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);
        this.iconTop = register.registerIcon(NAME + "Top");
        this.iconBase = register.registerIcon(NAME + "Base");
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        switch (side)
        {
            case 0:
                return this.iconBase;
            case 1:
                return this.iconTop;
            default:
                return this.blockIcon;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x, y - 1, z, ForgeDirection.UP, true);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!this.canPlaceBlockAt(world, x, y, z) && world.getBlock(x, y, z) == this)
        {
            this.dropBlockAsItem(world, x, y, z, 0, 0);
            world.setBlockToAir(x, y, z);
        }
    }


}
