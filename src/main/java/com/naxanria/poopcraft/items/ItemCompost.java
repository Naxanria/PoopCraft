package com.naxanria.poopcraft.items;

import com.naxanria.poopcraft.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCompost extends Item
{
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    ItemStack stack = player.getHeldItem(hand);
    
    if (!player.canPlayerEdit(pos.offset(facing), facing, stack))
    {
      return EnumActionResult.FAIL;
    }
    
    if (WorldUtil.applyBoneMeal(stack, world, pos, player, hand))
    {
      // send event, bonemeal event? - is what bonemeal uses
      world.playEvent(2005, pos, 0);
      
      stack.shrink(1);
      
      return EnumActionResult.SUCCESS;
    }
    
    return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
  }
}
