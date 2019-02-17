package com.naxanria.poopcraft.tab;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.init.PoopBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class PoopCraftTab extends CreativeTabs
{
  public PoopCraftTab()
  {
    super(PoopCraft.MODID);
    
    
  }
  
  @Override
  public ItemStack getTabIconItem()
  {
    return new ItemStack(PoopBlocks.TOILET.getAsItem());
  }
}
