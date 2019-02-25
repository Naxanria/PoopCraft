package com.naxanria.poopcraft.tile.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntityTickingBase extends TileEntityBase implements ITickable
{
  private int ticks;
  
  public int getTicks()
  {
    return ticks;
  }
  
  @Override
  public final void update()
  {
    ticks++;
    
    // do standard ticking stuff if needed
    
    entityUpdate();
    
    // update/sync nbt if need be.
    
    if (ticks % 5 == 0)
    {
      sendUpdate();
    }
  }
  
  protected abstract void entityUpdate();
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound)
  {
    super.writeSyncableNBT(compound);
    
    compound.setInteger("Ticks", ticks);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound)
  {
    super.readSyncableNBT(compound);
    
    ticks = compound.getInteger("Ticks");
  }
}
