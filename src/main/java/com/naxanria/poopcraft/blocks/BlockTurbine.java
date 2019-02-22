package com.naxanria.poopcraft.blocks;

import com.naxanria.poopcraft.blocks.base.BlockTileFacingBase;
import com.naxanria.poopcraft.tile.TileTurbine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockTurbine extends BlockTileFacingBase<TileTurbine>
{
  public BlockTurbine()
  {
    super(Material.IRON, "turbine", true);
  }
  
  @Override
  public TileTurbine createTileEntity(World world, IBlockState state)
  {
    return new TileTurbine();
  }
  
  @Override
  public Class<TileTurbine> getTileEntityClass()
  {
    return TileTurbine.class;
  }
  
  @Override
  public boolean isOpaqueCube(IBlockState state)
  {
    return false;
  }
}
