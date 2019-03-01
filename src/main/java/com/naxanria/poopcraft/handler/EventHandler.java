package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.capability.PlayerPoop;
import com.naxanria.poopcraft.capability.PlayerPoopCapability;
import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.entity.EntityAIPoop;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
  
  @SubscribeEvent
  public void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
  {
    if (event.getEntityLiving() instanceof EntityPlayer)
    {
      onPlayerUpdate(event, (EntityPlayer) event.getEntityLiving());
    }
  }
  
  private void onPlayerUpdate(LivingEvent.LivingUpdateEvent event, EntityPlayer player)
  {
//    if (player.world.isRemote)
//    {
//      return;
//    }
    
    // no need to poop in those modes
    if (player.isCreative() || player.isSpectator())
    {
      return;
    }
    
    PlayerPoop playerPoop = PlayerPoopCapability.getHandler(player);
    if (playerPoop == null)
    {
      return;
    }
    
    playerPoop.tick();
    
    if (playerPoop.hasToPoop())
    {
      // todo: key for pooping
      // todo: negative effects if player doesn't poop
      
      if (player.isSneaking())
      {
        playerPoop.poop(player);
      }
    }
  }
  
  
  @SubscribeEvent
  public void onAttachCapability(AttachCapabilitiesEvent<Entity> event)
  {
    if (event.getObject() instanceof EntityPlayer)
    {
      event.addCapability(PlayerPoopCapability.RESOURCE_LOCATION, new PlayerPoopCapability.Provider());
    }
  }
  
  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent event)
  {
    if (event.getModID().equals(PoopCraft.MODID))
    {
      ConfigHandler.config.save();
      ConfigHandler.init();
    }
  }
}
