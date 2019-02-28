package com.naxanria.poopcraft.data;

import com.google.common.reflect.Reflection;
import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static com.naxanria.poopcraft.init.PoopItems.POOPS.POOP_CHICKEN;
import static com.naxanria.poopcraft.init.PoopItems.POOPS.POOP_DEFAULT;
import static com.naxanria.poopcraft.init.PoopItems.POOPS.POOP_ZOMBIE;

public class EntityPoopCapabilities
{
  private static Map<String, EntityPoopCapabilities> capabilities = new HashMap<>();
  
  public String id;
  public Item poop;
  public int cooldown;
  //public int cooldownRandom;
  
  //boolean needToBeFed;
  
  public static void reset()
  {
    capabilities.clear();
  }
  
  public static void init()
  {
    // todo: load from json
    
    
  
    register(EntityChicken.class, POOP_CHICKEN, 6000);
    
  
    
    // == Zomberts == //
    register(EntityZombie.class, POOP_ZOMBIE, 6000);
    register(EntityHusk.class, POOP_ZOMBIE, 6000);
    register(EntityGiantZombie.class, POOP_ZOMBIE, 6000);
    register(EntityPigZombie.class, POOP_ZOMBIE, 6000);
    register(EntityZombieVillager.class, POOP_ZOMBIE, 6000);
    
    PoopCraft.logger.info("=== LOADING DEFAULTS FOR ENTITIES ===");
    
    for(ResourceLocation location: EntityList.getEntityNameList())
    {
      if (!Settings.makeNonMinecraftCreaturesPoop && !location.getResourceDomain().equals("minecraft"))
      {
        continue;
      }
      
      String id = location.getResourceDomain() + ":" + location.getResourcePath();
      
      if (!hasCapability(id))
      {
        Class<?> c = EntityList.getClass(location);
        
        if (c == null)
        {
          continue;
        }
        
        if (!EntityCreature.class.isAssignableFrom(c))
        {
          continue;
        }
        
        EntityPoopCapabilities poopCapabilities = new EntityPoopCapabilities();
        poopCapabilities.id = id;
        poopCapabilities.poop = POOP_DEFAULT;
        poopCapabilities.cooldown = 6000;
        
        register(poopCapabilities);
      }
    }
  }
  
  public static <T extends EntityCreature> void register(Class<T> clazz, Item poop, int cooldown)
  {
    EntityPoopCapabilities poopCapabilities = new EntityPoopCapabilities();
    poopCapabilities.id = getId(clazz);
    poopCapabilities.poop = poop;
    poopCapabilities.cooldown = cooldown;
    
    register(poopCapabilities);
  }
  
  public static void register(EntityPoopCapabilities capability)
  {
    PoopCraft.logger.info("Registering PoopEntity: " + capability.id);
    
    capabilities.put(capability.id, capability);
  }
  
  public static EntityPoopCapabilities getCapability(String id)
  {
    return capabilities.getOrDefault(id, null);
  }
  
  public static EntityPoopCapabilities getCapability(EntityCreature entityLiving)
  {
    return getCapability(getId(entityLiving));
  }
  
  public static boolean hasCapability(EntityCreature entityLiving)
  {
    return getCapability(entityLiving) != null;
  }
  
  private static boolean hasCapability(String id)
  {
    return getCapability(id) != null;
  }
  
  public static <T extends EntityCreature> String getId(Class<T> clazz)
  {
    ResourceLocation key = EntityList.getKey(clazz);
  
    if (key == null)
    {
      return "UNKNOWN:UNKNOWN";
    }
    return key.getResourceDomain() + ":" + key.getResourcePath();
  }
  
  public static String getId(EntityCreature entityLivingBase)
  {
    return getId(entityLivingBase.getClass());
  }
}
