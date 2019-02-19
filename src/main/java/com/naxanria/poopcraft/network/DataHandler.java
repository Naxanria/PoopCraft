package com.naxanria.poopcraft.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public abstract class DataHandler
{
  public abstract void handleData(NBTTagCompound compound, MessageContext context);
  
  public final String name;
  public final int id;
  
  public DataHandler(String name)
  {
    this.name = name;
    this.id = name.hashCode();
  }
}
