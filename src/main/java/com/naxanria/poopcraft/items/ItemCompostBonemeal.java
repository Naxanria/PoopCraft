package com.naxanria.poopcraft.items;

import com.naxanria.poopcraft.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCompostBonemeal extends ItemCompost
{
  public ItemCompostBonemeal()
  {
    super("compost_bonemeal");
  }
  
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    ItemStack stack = player.getHeldItem(hand);
    
    if (!player.canPlayerEdit(pos.offset(facing), facing, stack))
    {
      return EnumActionResult.FAIL;
    }
  
    
    boolean applied = false;
    for (int x = -1; x <= 1 ; x++)
    {
      for (int z = -1; z <= 1; z++)
      {
        BlockPos target = pos.add(x, 0, z);
        
        boolean curr = WorldUtil.applyBoneMeal(stack, world, target, player, hand);
        applied |= curr;
        
        if (curr)
        {
          // send event, bonemeal event? - is what bonemeal uses
          world.playEvent(2005, target, 0);
        }
      }
    }
    
    if (applied)
    {
      stack.shrink(1);
      return EnumActionResult.SUCCESS;
    }
    
    return EnumActionResult.PASS;
  }
}
