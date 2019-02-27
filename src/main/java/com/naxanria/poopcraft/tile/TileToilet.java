package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.base.energy.EnergyStorageBase;
import com.naxanria.poopcraft.tile.base.TileEntityTickingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileToilet extends TileEntityTickingBase
{
  private EnergyStorageBase storage;
  
  private AxisAlignedBB checkBox;
  
  private int generate = 45;
  private int time = 200;
  private int currentTime = 0;
  private boolean generating = false;
  private int lastCheck = 0;
  private int checkCooldown = 20;
  
  public TileToilet()
  {
    storage = new EnergyStorageBase(9001, 9001, 0);
  }
  
  @Override
  protected void entityUpdate()
  {
    boolean mark = false;
    
    if (generating)
    {
      if (currentTime++ >= time)
      {
        generating = false;
      }
      
      if (generating)
      {
        storage.receiveInternal(generate);
        if (currentTime == 5)
        {
          // OVER 9000
          storage.receiveInternal(1);
        }
      }
  
      mark = true;
    }
    if (!generating && --lastCheck <= 0 && !storage.isFull())
    {
      lastCheck = checkCooldown;
      
      if (checkBox == null)
      {
        checkBox = new AxisAlignedBB(pos);
      }
      
      List<EntityItem> found = world.getEntitiesWithinAABB(EntityItem.class, checkBox);
      boolean startGeneration = false;
  
      for (EntityItem ei :
        found)
      {
        if (ei.getItem().getItem() == PoopItems.POOPS.POOP_HUMAN.getItem())
        {
          ItemStack stack = ei.getItem();
          
          stack.setCount(stack.getCount() - 1);
          
          startGeneration = true;
          
          break;
        }
      }
      
      if (startGeneration)
      {
        generating = true;
        currentTime = 0;
        mark = true;
      }
    }
    
    if (mark && !world.isRemote)
    {
      markDirty();
    }
  }
  
  @Override
  public int getComparatorStrength()
  {
    return storage.getAsComparatorStrength();
  }
}
