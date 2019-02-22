package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.init.registry.ItemRegistry;
import com.naxanria.poopcraft.items.ItemCompost;
import com.naxanria.poopcraft.items.ItemPoop;
import com.naxanria.poopcraft.items.base.ItemBase;

public class PoopItems
{
  public static final ItemPoop POOP = new ItemPoop();
  public static final ItemCompost COMPOST = new ItemCompost();
  
  public static void register(ItemRegistry registry)
  {
    registry.addAll
    (
      POOP,
      COMPOST
    );
  }
}
