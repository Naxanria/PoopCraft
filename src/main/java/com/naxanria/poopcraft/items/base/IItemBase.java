package com.naxanria.poopcraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public interface IItemBase
{
  void registerItemModel();
  
  Item setCreativeTab(CreativeTabs tab);
  
  Item getItem();
}
