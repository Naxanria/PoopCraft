package com.naxanria.poopcraft.proxy;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.tile.base.TileEntityBase;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy
{
  public abstract boolean isClient();
  
  public abstract void registerItemRender(Item item, int meta, String id);
  
  public String localized(String key)
  {
    return key;
  }
  
  public void registerHandler(Object obj)
  {
    PoopCraft.logger.info("Registering " + obj.getClass().getCanonicalName());
    MinecraftForge.EVENT_BUS.register(obj);
  }
  
  public abstract <TE extends TileEntityBase> void registerTileEntityRenderer(Class<TE> tileEntityClass, String tileEntityRenderer);
}
