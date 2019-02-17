package com.naxanria.poopcraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IItemBase
{
  protected String name;
  
  public ItemBase(String name)
  {
    this.name = name;
    
    setUnlocalizedName(name);
    setRegistryName(name);
  }
  
  @Override
  public void registerItemModel()
  {
  
  }
  
  @Override
  public ItemBase setCreativeTab(CreativeTabs tab)
  {
    super.setCreativeTab(tab);
    return this;
  }
  
  @Override
  public Item getItem()
  {
    return this;
  }
}
