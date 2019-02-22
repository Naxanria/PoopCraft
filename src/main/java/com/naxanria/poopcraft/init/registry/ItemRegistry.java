package com.naxanria.poopcraft.init.registry;

import com.naxanria.poopcraft.items.base.IItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry extends Registry<IItemBase, IForgeRegistry<Item>>
{
  @Override
  public void register(IForgeRegistry<Item> registry, IItemBase item)
  {
    registry.register(item.getItem());
  }
  
  public void registerModels()
  {
    for (IItemBase item :
      toRegister)
    {
      item.registerItemModel();
    }
  }
}
