package com.naxanria.poopcraft.blocks.base;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.tile.base.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockTileBase<TE extends TileEntityBase> extends Block
{
  public BlockTileBase(Material blockMaterialIn, MapColor blockMapColorIn)
  {
    super(blockMaterialIn, blockMapColorIn);
  }
  
  public BlockTileBase(Material materialIn)
  {
    super(materialIn);
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
  
//  public void registerTileEntity()
//  {
//    if (needTileEntityRegistration())
//    {
//      PoopCraft.logger.info("Registering " + getTileEntityClass().getCanonicalName() + " as a TileEntity");
//
//      GameRegistry.registerTileEntity(getTileEntityClass(), getRegistryName().toString());
//
//      String tileEntityRenderer = getTileEntityRendererClass();
//      if (tileEntityRenderer != null)
//      {
//        PoopCraft.proxy.registerTileEntityRenderer(getTileEntityClass(), tileEntityRenderer);
//      }
//    }
//  }


  
  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
  {
    //todo: check for if the itemstack has nbt data and put that onto the placed block
    
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }
  
  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
  {
    // todo: save nbt to itemstack if needed.
    
    super.getDrops(drops, world, pos, state, fortune);
  }
//
//  @Override
//  public boolean hasComparatorInputOverride(IBlockState state)
//  {
//    return true;
//  }
//
//  @Override
//  public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
//  {
//    return getTileEntity(world, pos).getComparatorStrength();
//  }
}
