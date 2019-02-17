package com.naxanria.poopcraft.proxy;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
  @Override
  public boolean isClient()
  {
    return true;
  }
  
  @Override
  public void registerItemRender(Item item, int meta, String id)
  {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(PoopCraft.MODID + ":" + id, "inventory"));
  }
}
