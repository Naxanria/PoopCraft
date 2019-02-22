package com.naxanria.poopcraft.items.base;

import com.naxanria.poopcraft.PoopCraft;
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
    
    setCreativeTab(PoopCraft.tab);
  }
  
  @Override
  public void registerItemModel()
  {
    PoopCraft.proxy.registerItemRender(this, 0, name);
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
