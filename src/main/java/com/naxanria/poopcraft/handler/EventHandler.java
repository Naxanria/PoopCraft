package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.entity.EntityAIPoop;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event)
  {
    if (event.getWorld().isRemote)
    {
      return;
    }
  
    if (event.getEntity() instanceof EntityCreature)
    {
      EntityCreature entity = (EntityCreature) event.getEntity();
    
      if (EntityPoopCapabilities.hasCapability(entity))
      {
        EntityPoopCapabilities poopCapabilities = EntityPoopCapabilities.getCapability(entity);
      
        EntityAIPoop poopAI = new EntityAIPoop(entity, poopCapabilities.poop, poopCapabilities.cooldown);
      
      
        entity.tasks.addTask(0, poopAI);
      }
    }
  }
}
