package com.naxanria.poopcraft.proxy;

import net.minecraft.item.Item;

public class ServerProxy extends CommonProxy
{
  @Override
  public boolean isClient()
  {
    return false;
  }
  
  @Override
  public void registerItemRender(Item item, int meta, String id)
  {
  
  }
}
