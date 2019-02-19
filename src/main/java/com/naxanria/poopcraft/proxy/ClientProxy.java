package com.naxanria.poopcraft.proxy;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.tile.base.TileEntityBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

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
  
  @Override
  public <TE extends TileEntityBase> void registerTileEntityRenderer(Class<TE> tileEntityClass, String tileEntityRenderer)
  {
    try
    {
      Class clazz = Class.forName(tileEntityRenderer);
      TileEntitySpecialRenderer renderer = (TileEntitySpecialRenderer) clazz.newInstance();
      ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
    catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
    {
      PoopCraft.logger.error("Unable to register renderer " + tileEntityRenderer + " for " + tileEntityClass.getCanonicalName());
      e.printStackTrace();
    }
  }
}
