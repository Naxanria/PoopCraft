package com.naxanria.poopcraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;

public class EntityAIPoop extends EntityAIBase
{
  private final EntityLiving entity;
  private final Item item;
  private final int cooldown;
  
  public EntityAIPoop(EntityLiving entity, Item item, int cooldown)
  {
    this.entity = entity;
    this.item = item;
    this.cooldown = cooldown;
    
    if (cooldown <= 0)
    {
      throw new IllegalArgumentException("Cooldown must be bigger than 0!");
    }
  }
  
  @Override
  public boolean shouldExecute()
  {
    if (entity instanceof EntityChicken)
    {
      return !((EntityChicken) entity).isChickenJockey();
    }
    
    return true;
  }
  
  @Override
  public void updateTask()
  {
    if (entity.ticksExisted % cooldown == 0)
    {
      entity.dropItem(item, 1);
    }
  }
}
