package com.naxanria.poopcraft.blocks;

import com.naxanria.poopcraft.blocks.base.BlockTileFacingBase;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.TileComposter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockComposter extends BlockTileFacingBase<TileComposter>
{
  public BlockComposter()
  {
    super(Material.IRON, "composter", true);
  }
  
  @Override
  public TileComposter createTileEntity(World world, IBlockState state)
  {
    return new TileComposter();
  }
  
  @Override
  public Class<TileComposter> getTileEntityClass()
  {
    return TileComposter.class;
  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    ItemStack stack = playerIn.inventory.mainInventory.get(playerIn.inventory.currentItem);
    
    if (stack.getItem() == PoopItems.POOP)
    {
      TileComposter composter = getTileEntity(worldIn, pos);
      ItemStack usedStack = composter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).insertItem(0, stack, false);
      stack.setCount(stack.getCount() - usedStack.getCount());
    }
    
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }
}
