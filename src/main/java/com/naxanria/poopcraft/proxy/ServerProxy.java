package com.naxanria.poopcraft.proxy;

import com.naxanria.poopcraft.tile.base.TileEntityBase;
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
  { }
  
  @Override
  public <TE extends TileEntityBase> void registerTileEntityRenderer(Class<TE> tileEntityClass, String tileEntityRenderer)
  { }
}
