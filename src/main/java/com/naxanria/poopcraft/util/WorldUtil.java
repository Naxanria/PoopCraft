package com.naxanria.poopcraft.util;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

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
  
  public static boolean applyBoneMeal(ItemStack stack, World world, BlockPos target, EntityPlayer player, EnumHand hand)
  {
    IBlockState state = world.getBlockState(target);
    
    int hook = ForgeEventFactory.onApplyBonemeal(player, world, target, state, stack, hand);
    
    if (hook != 0)
    {
      return hook > 0;
    }
    
    if (state.getBlock() instanceof IGrowable)
    {
      IGrowable growable = (IGrowable) state.getBlock();
      
      if (growable.canGrow(world, target, state, world.isRemote))
      {
        if (!world.isRemote)
        {
          if (growable.canUseBonemeal(world, world.rand, target, state))
          {
            growable.grow(world, world.rand, target, state);
          }
        }
      }
      
      return true;
    }
    
    return false;
  }
  
}
