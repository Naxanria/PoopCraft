package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.entity.EntityAIPoop;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event)
  {
    
    //PoopCraft.logger.info(EntityList.getEntityString(event.getEntity()));
    
    if (event.getWorld().isRemote)
    {
      return;
    }
  
    if (event.getEntity() instanceof EntityCreature)
    {
      EntityCreature entity = (EntityCreature) event.getEntity();
      
      ResourceLocation key = EntityList.getKey(entity);
      
      if (key != null)
      {
        //PoopCraft.logger.info(key.getResourceDomain() + ":" + key.getResourcePath());
        
      }
      
      
      if (EntityPoopCapabilities.hasCapability(entity))
      {
        EntityPoopCapabilities poopCapabilities = EntityPoopCapabilities.getCapability(entity);
      
        EntityAIPoop poopAI = new EntityAIPoop(entity, poopCapabilities.poop, poopCapabilities.cooldown);
      
      
        entity.tasks.addTask(0, poopAI);
      }
    }
  }
}
