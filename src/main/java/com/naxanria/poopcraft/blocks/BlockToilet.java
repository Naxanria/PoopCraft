package com.naxanria.poopcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockToilet extends BlockBase
{
  public BlockToilet()
  {
    super(Material.IRON, "toilet");
  }
  
  @Override
  public boolean isFullBlock(IBlockState state)
  {
    return false;
  }
  
  @Override
  public boolean isBlockNormalCube(IBlockState state)
  {
    return false;
  }
  
  @Override
  public boolean isNormalCube(IBlockState state)
  {
    return super.isNormalCube(state);
  }
  
  @Override
  public boolean isFullCube(IBlockState state)
  {
    return false;
  }
  
  @Override
  public boolean isOpaqueCube(IBlockState state)
  {
    return false;
  }
}
