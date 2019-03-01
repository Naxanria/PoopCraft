package com.naxanria.poopcraft.capability;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerPoop implements IPlayerPoop, INBTSerializable<NBTTagCompound>
{
  private int lastPoop = 0;
  private boolean hasToPoop = false;
  
  @Override
  public int getLastPoop()
  {
    return lastPoop;
  }
  
  @Override
  public void setLastPoop(int t)
  {
    lastPoop = t;
  }
  
  @Override
  public void tick()
  {
    if (lastPoop++ >= Settings.playerPoopCooldown)
    {
      hasToPoop = true;
    }
  }
  
  @Override
  public void poop(EntityPlayer player)
  {
    if (!player.world.isRemote)
    {
      WorldUtil.dropItemInWorld(player.world, player.getPosition(), PoopItems.POOPS.POOP_HUMAN);
    }
    
    hasToPoop = false;
    lastPoop = 0;
  }
  
  @Override
  public boolean hasToPoop()
  {
    return hasToPoop;
  }
  
  @Override
  public void setHasToPoop(boolean hasToPoop)
  {
    this.hasToPoop = hasToPoop;
  }
  
  @Override
  public NBTTagCompound serializeNBT()
  {
    NBTTagCompound compound = new NBTTagCompound();
    compound.setInteger("LastPoop", lastPoop);
    compound.setBoolean("HasToPoop", hasToPoop);
    
    return compound;
  }
  
  @Override
  public void deserializeNBT(NBTTagCompound compound)
  {
    lastPoop = compound.getInteger("LastPoop");
    hasToPoop = compound.getBoolean("HasToPoop");
  }
}
