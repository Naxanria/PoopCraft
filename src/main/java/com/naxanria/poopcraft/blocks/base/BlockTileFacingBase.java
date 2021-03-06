package com.naxanria.poopcraft.blocks.base;

import com.naxanria.poopcraft.tile.base.TileEntityBase;
import com.naxanria.poopcraft.util.WorldUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileFacingBase<TE extends TileEntityBase> extends BlockFacingBase
{
  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  
  protected boolean mirrorPlacement = false;
  
  public BlockTileFacingBase(Material blockMaterial, boolean mirrorPlacement)
  {
    super(blockMaterial, mirrorPlacement);
  }
  
  public BlockTileFacingBase(Material blockMaterial)
  {
    super(blockMaterial);
  }
  
  
  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
  {
    return new ItemStack(getAsItem(), 1, getMetaFromState(world.getBlockState(pos)) / EnumFacing.values().length);
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta)
  {
    EnumFacing facing = EnumFacing.getFront(meta);
    
    if (facing.getAxis() == EnumFacing.Axis.Y)
    {
      facing = EnumFacing.NORTH;
    }
    
    return getDefaultState().withProperty(FACING, facing);
  }
  
  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
  {
    return
      (mirrorPlacement)
        ? getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite())
        : getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
  }
  
  @Override
  public int getMetaFromState(IBlockState state)
  {
    return state.getValue(FACING).getIndex();
  }
  
  private void setDefaultFacing(World world, BlockPos pos, IBlockState state)
  {
    if (!world.isRemote)
    {
      IBlockState northState = world.getBlockState(pos.north());
      IBlockState southState = world.getBlockState(pos.south());
      IBlockState westState = world.getBlockState(pos.west());
      IBlockState eastState = world.getBlockState(pos.east());
      EnumFacing enumfacing = state.getValue(FACING);
      
      if (enumfacing == EnumFacing.NORTH && northState.isFullBlock() && !southState.isFullBlock())
      {
        enumfacing = EnumFacing.SOUTH;
      }
      else if (enumfacing == EnumFacing.SOUTH && southState.isFullBlock() && !northState.isFullBlock())
      {
        enumfacing = EnumFacing.NORTH;
      }
      else if (enumfacing == EnumFacing.WEST && westState.isFullBlock() && !eastState.isFullBlock())
      {
        enumfacing = EnumFacing.EAST;
      }
      else if (enumfacing == EnumFacing.EAST && eastState.isFullBlock() && !westState.isFullBlock())
      {
        enumfacing = EnumFacing.WEST;
      }
      
      world.setBlockState(pos, state.withProperty(FACING, enumfacing), WorldUtil.FLAG_STATE_SEND_TO_ALL_CLIENTS);
    }
  }
  
  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot)
  {
    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
  }
  
  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirror)
  {
    return state.withRotation(mirror.toRotation(state.getValue(FACING)));
  }
  
  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state)
  {
    setDefaultFacing(world, pos, state);
  }
  
  @Override
  protected BlockStateContainer createBlockState()
  {
    return new BlockStateContainer(this, FACING);
  }
  
  @Override
  public boolean hasTileEntity(IBlockState state)
  {
    return true;
  }
  
  public TE getTileEntity(IBlockAccess world, BlockPos pos)
  {
    return (TE) world.getTileEntity(pos);
  }
  
  public abstract TE createTileEntity(World world, IBlockState state);
  
//  public abstract Class<TE> getTileEntityClass();
}
