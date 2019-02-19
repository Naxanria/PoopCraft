package com.naxanria.poopcraft.util.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface ISerializable
{
  NBTTagCompound writeToNBT(NBTTagCompound compound);

  void readFromNBT(NBTTagCompound compound);
}
