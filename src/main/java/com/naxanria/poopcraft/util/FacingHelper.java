package com.naxanria.poopcraft.util;

import net.minecraft.util.EnumFacing;

public class FacingHelper
{
  public static EnumFacing getLeft(EnumFacing facing)
  {
    switch (facing)
    {
      case DOWN:
      case UP:
        
        return EnumFacing.NORTH;
      
      case NORTH:
        
        return EnumFacing.WEST;
      
      case SOUTH:
        
        return EnumFacing.EAST;
      
      case WEST:
        
        return EnumFacing.SOUTH;
      
      case EAST:
        
        return EnumFacing.NORTH;
    }
    
    return EnumFacing.NORTH;
  }
  
  public static EnumFacing getRight(EnumFacing facing)
  {
    switch (facing)
    {
      case DOWN:
      case UP:
        
        return EnumFacing.NORTH;
      
      case NORTH:
        
        return EnumFacing.EAST;
      
      case SOUTH:
        
        return EnumFacing.WEST;
      
      case WEST:
        
        return EnumFacing.NORTH;
      
      case EAST:
        
        return EnumFacing.SOUTH;
    }
    
    return EnumFacing.NORTH;
  }
}
