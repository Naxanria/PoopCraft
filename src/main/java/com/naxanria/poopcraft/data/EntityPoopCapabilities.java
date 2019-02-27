package com.naxanria.poopcraft.data;

import com.google.common.reflect.Reflection;
import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static com.naxanria.poopcraft.init.PoopItems.POOPS.POOP_CHICKEN;
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
    
    //todo: defaults for all the rest through reflection?
//    Class<?>[] creatures = EntityCreature.class.getClasses();
//
//    for (Class<?> c :
//      creatures)
//    {
//      String id = c.getCanonicalName();
//      EntityPoopCapabilities capabilities = getCapability(id);
//
//      if (capabilities == null)
//      {
//        PoopCraft.logger.info("  Registering default poop for: " + id);
//        register((Class<EntityCreature>) c, POOP_CHICKEN, 6000);
//      }
//    }
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
  
  public static <T extends EntityCreature> String getId(Class<T> clazz)
  {
    return clazz.getCanonicalName();
  }
  
  public static String getId(EntityCreature entityLivingBase)
  {
    return entityLivingBase.getClass().getCanonicalName();
//    if (entityLivingBase instanceof EntityLiving)
//    {
//      if (entityLivingBase instanceof EntityCreature)
//      {
//        EntityCreature creature = (EntityCreature) entityLivingBase;
//
//        //creature.
//      }
//    }
//
//    entityLivingBase.getName();
  }
}
