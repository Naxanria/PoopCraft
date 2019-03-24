package com.naxanria.poopcraft.tab;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.init.PoopBlocks;
import com.naxanria.poopcraft.init.PoopFluids;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class PoopCraftTab extends CreativeTabs
{
  public PoopCraftTab()
  {
    super(PoopCraft.MODID);
  }
  
  @Override
  public void displayAllRelevantItems(NonNullList<ItemStack> list)
  {
    super.displayAllRelevantItems(list);
    
//    list.add(PoopFluids.METHANE.getAsBucket());
  }
  
  @Override
  public ItemStack getTabIconItem()
  {
    return new ItemStack(PoopBlocks.TOILET);
  }
}
