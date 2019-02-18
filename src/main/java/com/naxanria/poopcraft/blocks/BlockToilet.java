package com.naxanria.poopcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockToilet extends BlockFacingBase
{
  public static final AxisAlignedBB hitbox =
    new AxisAlignedBB
    (
      4.0 * (1 / 16.0), 0, 4.0 * (1/ 16.0),
      12.0 * (1 / 16.0), 0.65, 12.0 * (1 / 16.0)
    );
  
  //private boolean shownHitbox = false;
  
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
  
  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
  {
    return hitbox;
  }
}
