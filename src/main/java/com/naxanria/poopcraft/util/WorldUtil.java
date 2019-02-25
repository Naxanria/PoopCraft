package com.naxanria.poopcraft.util;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtil
{
  public static final int FLAG_STATE_UPDATE_BLOCK = 1;
  public static final int FLAG_STATE_SEND_TO_ALL_CLIENTS = 2;
  public static final int FLAG_STATE_PREVENT_RERENDER = 4;
  public static final int FLAG_STATE_RERENDER_MAINTHREAD = 8;
  public static final int FLAG_STATE_PREVENT_OBSERVERS = 16;
  
  public static Block[] getAround(World world, BlockPos pos)
  {
    return getAround(world, pos, EnumFacing.values());
  }
  
  public static Block[] getAround(World world, BlockPos pos, EnumFacing... facings)
  {
    Block[] blocks = new Block[facings.length];
    int p = 0;
    
    for (EnumFacing facing :
      facings)
    {
      blocks[p++] = world.getBlockState(pos.offset(facing)).getBlock();
    }
    
    return blocks;
  }
  
}
