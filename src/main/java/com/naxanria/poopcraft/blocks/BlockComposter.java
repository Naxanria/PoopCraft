package com.naxanria.poopcraft.blocks;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.blocks.base.BlockTileFacingBase;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.TileComposter;
import com.naxanria.poopcraft.tile.base.inventory.ItemStackHandlerBase;
import com.naxanria.poopcraft.util.ItemHandlerHelper;
import com.naxanria.poopcraft.util.SettingsHelper;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
  {
    super.breakBlock(worldIn, pos, state);
  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote)
    {
      ItemStack stack = playerIn.inventory.mainInventory.get(playerIn.inventory.currentItem);
  
      if (!stack.isEmpty() || stack.getItem() != Items.AIR)
      {
        TileComposter composter = getTileEntity(worldIn, pos);
        ItemStackHandlerBase handlerBase = (ItemStackHandlerBase) composter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
  
        if (handlerBase != null)
        {
          PoopCraft.logger.info("Trying to insert: " + StackUtil.getItemId(stack) + " " + stack.getCount() + " has caps: " + SettingsHelper.hasPoopCapabilities(stack));
          
          ItemStack remainder = ItemHandlerHelper.insert(handlerBase, stack);
          stack.setCount(remainder.getCount());
  
          PoopCraft.logger.info("Remainder : " + StackUtil.getItemId(stack) + " " + stack.getCount());
        }
      }
    }
    
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }
}
