package passivetorch.events;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import passivetorch.blocks.TileEntityTorch;

import java.util.ArrayList;
import java.util.List;

public class ServerEventHandler
{
    public static List<TileEntityTorch> torches = new ArrayList<TileEntityTorch>();

    public static boolean isInRangeOfTorch(Entity entity)
    {
        for (TileEntityTorch torch : torches)
        {
            if (torch.isInRange(entity)) return true;
        }
        return false;
    }

    @SubscribeEvent
    public void spawnCheck(LivingSpawnEvent.CheckSpawn event)
    {
        if (event.getResult() != Event.Result.ALLOW)
        {
            if (((event.entityLiving instanceof EntitySlime && !event.entityLiving.isImmuneToFire()) || !(event.entityLiving instanceof IMob)) && isInRangeOfTorch(event.entity))
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
