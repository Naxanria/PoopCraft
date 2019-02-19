package com.naxanria.poopcraft.tile.base.energy;

import com.naxanria.poopcraft.util.MathUtil;
import com.naxanria.poopcraft.util.interfaces.ISerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageBase implements IEnergyStorage, ISerializable
{
  public static final String TAG = "ENERGY";
  
  protected int stored;
  protected int capacity;
  protected int maxDrain;
  protected int maxReceive;
  
  protected boolean active = true;
  
  public EnergyStorageBase(int capacity)
  {
    this.capacity = capacity;
  }
  
  public EnergyStorageBase(int capacity, int maxTransfer)
  {
    this.capacity = capacity;
    this.maxDrain = maxTransfer;
    this.maxReceive = maxTransfer;
  }
  
  public EnergyStorageBase(int capacity, int maxDrain, int maxReceive)
  {
    this.capacity = capacity;
    this.maxDrain = maxDrain;
    this.maxReceive = maxReceive;
  }
  
  public EnergyStorageBase(int stored, int capacity, int maxDrain, int maxReceive)
  {
    this.stored = stored;
    this.capacity = capacity;
    this.maxDrain = maxDrain;
    this.maxReceive = maxReceive;
  }
  
  @Override
  public int receiveEnergy(int receive, boolean simulate)
  {
    if (!canReceive())
    {
      return 0;
    }
    
    int space = capacity - stored;
    int amount = Math.min(Math.min(space, maxReceive), receive);
    
    if (!simulate)
    {
      stored += amount;
    }
    
    return amount;
  }
  
  public void receiveInternal(int amount)
  {
    setStored(stored + amount);
  }
  
  @Override
  public int extractEnergy(int extract, boolean simulate)
  {
    if (!canExtract())
    {
      return 0;
    }
    
    int space = stored;
    int amount = Math.min(Math.min(extract, maxDrain), space);
    
    if (!simulate)
    {
      stored -= amount;
    }
    
    return amount;
  }
  
  public void extractInternal(int extract)
  {
    setStored(stored - extract);
  }
  
  @Override
  public int getEnergyStored()
  {
    return stored;
  }
  
  @Override
  public int getMaxEnergyStored()
  {
    return capacity;
  }
  
  @Override
  public boolean canExtract()
  {
    return active;
  }
  
  public boolean canExtract(int amount)
  {
    return active && stored >= amount;
  }
  
  @Override
  public boolean canReceive()
  {
    return active;
  }
  
  public boolean canReceive(int amount)
  {
    return active && capacity - stored >= amount;
  }
  
  /**
   * @return the comparator strength based on current stored energy, with any energy stored, > 0 and not full, < 15
   */
  public int getAsComparatorStrength()
  {
    if (stored == 0)
    {
      return 0;
    }
    
    if (stored == capacity)
    {
      return 15;
    }
    
    return MathUtil.clamp((int) (((capacity - (capacity - stored)) / (float) capacity) * 15), 1, 14);
  }
  
  public boolean isActive()
  {
    return active;
  }
  
  public void setActive(boolean active)
  {
    this.active = active;
  }
  
  public void setStored(int stored)
  {
    this.stored = MathUtil.clamp(stored, 0, capacity);
  }
  
  public void setCapacity(int capacity)
  {
    this.capacity = capacity;
    
    if (stored > capacity)
    {
      stored = capacity;
    }
  }
  
  public void setMaxDrain(int maxDrain)
  {
    if (maxDrain < 0)
    {
      throw new IllegalArgumentException("maxDrain must be > 0");
    }
    
    this.maxDrain = maxDrain;
  }
  
  public void setMaxReceive(int maxReceive)
  {
    if (maxReceive < 0)
    {
      throw new IllegalArgumentException("maxReceive must be > 0");
    }
    
    this.maxReceive = maxReceive;
  }
  
  public boolean isFull()
  {
    return stored == capacity;
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    NBTTagCompound tag = new NBTTagCompound();
    
    tag.setInteger("Stored", stored);
    tag.setInteger("Capacity", capacity);
    tag.setInteger("MaxDrain", maxDrain);
    tag.setInteger("MaxReceive", maxReceive);
    
    tag.setBoolean("Active", active);
    
    compound.setTag(TAG, tag);
    
    return compound;
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound)
  {
    NBTTagCompound tag = compound.getCompoundTag(TAG);
    
    stored = tag.getInteger("Stored");
    capacity = tag.getInteger("Capacity");
    maxDrain = tag.getInteger("MaxDrain");
    maxReceive = tag.getInteger("MaxReceive");
    
    active = tag.getBoolean("Active");
  }
}
