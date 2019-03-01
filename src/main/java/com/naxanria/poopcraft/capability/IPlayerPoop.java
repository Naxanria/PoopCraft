package com.naxanria.poopcraft.capability;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerPoop
{
  int getLastPoop();
  
  void setLastPoop(int t);
  
  void tick();
  
  void poop(EntityPlayer player);
  
  boolean hasToPoop();
  
  void setHasToPoop(boolean hasToPoop);
}
