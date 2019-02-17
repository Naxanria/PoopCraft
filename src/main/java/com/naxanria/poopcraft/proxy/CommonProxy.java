package com.naxanria.poopcraft.proxy;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy
{
  public abstract boolean isClient();
  
  public abstract void registerItemRender(Item item, int meta, String id);
  
  
  public void registerHandler(Object obj)
  {
    PoopCraft.logger.info("Registering " + obj.getClass().getCanonicalName());
    MinecraftForge.EVENT_BUS.register(obj);
  }
}
