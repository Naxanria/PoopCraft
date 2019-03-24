package com.naxanria.poopcraft.blocks.base;

import com.naxanria.poopcraft.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockFacingBase extends Block
{
  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  
  protected boolean mirrorPlacement = false;
  
  public BlockFacingBase(Material blockMaterial, boolean mirrorPlacement)
  {
    super(blockMaterial);
    
    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    
    this.mirrorPlacement = mirrorPlacement;
  }
  
  public BlockFacingBase(Material blockMaterial)
  {
    this(blockMaterial, false);
  }
  
  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
  {
    return new ItemStack(getAsItem(), 1, getMetaFromState(world.getBlockState(pos)) / EnumFacing.values().length);
  }
  
  protected Item getAsItem()
  {
    return Item.getItemFromBlock(this);
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
}
