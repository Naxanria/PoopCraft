package com.naxanria.poopcraft.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class NBTHelper
{
  public static BlockPos readBlockPos(NBTTagCompound compound)
  {
    return new BlockPos
    (
      compound.getInteger("X"),
      compound.getInteger("Y"),
      compound.getInteger("Z")
    );
  }
  
  public static NBTTagCompound writeBlockPos(BlockPos pos, NBTTagCompound compound)
  {
    compound.setInteger("X", pos.getX());
    compound.setInteger("Y", pos.getY());
    compound.setInteger("Z", pos.getZ());
    
    return compound;
  }
  
  public static World readWorld(NBTTagCompound compound)
  {
    return DimensionManager.getWorld(compound.getInteger("WorldId"));
  }
  
  public static NBTTagCompound writeWorld(World world, NBTTagCompound compound)
  {
    compound.setInteger("WorldId", world.provider.getDimension());
    
    return compound;
  }
}
