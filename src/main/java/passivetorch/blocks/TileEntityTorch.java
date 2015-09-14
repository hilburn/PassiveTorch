package passivetorch.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import passivetorch.events.ServerEventHandler;

public class TileEntityTorch extends TileEntity
{
    private static final double RANGE_H = 16384.0D;
    private static final double RANGE_V = 1024.0D;

    private byte worldID;
    private boolean needsWorldID;

    public TileEntityTorch()
    {
        needsWorldID = true;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (needsWorldID)
        {
            worldID = (byte)worldObj.provider.dimensionId;
            needsWorldID = false;
        }
    }

    public boolean isInRange(Entity entity)
    {
        if (entity.worldObj.provider.dimensionId != worldID) return false;
        double dx = (xCoord + 0.5D) - entity.posX;
        double dy = (yCoord + 0.5D) - entity.posY;
        double dz = (zCoord + 0.5D) - entity.posZ;
        return (dx * dx + dz * dz) / RANGE_H + dy * dy / RANGE_V <= 1.0D;
    }

    @Override
    public void onChunkUnload()
    {
        if (!worldObj.isRemote)
            ServerEventHandler.torches.remove(this);
        super.onChunkUnload();
    }

    @Override
    public void invalidate()
    {
        if (!worldObj.isRemote)
            ServerEventHandler.torches.remove(this);
        super.invalidate();
    }

    @Override
    public void validate()
    {
        super.validate();
        if (!worldObj.isRemote)
            ServerEventHandler.torches.add(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof TileEntityTorch && ((TileEntityTorch)obj).worldID == this.worldID &&
                ((TileEntityTorch)obj).xCoord == this.xCoord && ((TileEntityTorch)obj).yCoord == this.yCoord &&
                ((TileEntityTorch)obj).zCoord == this.zCoord;
    }
}
