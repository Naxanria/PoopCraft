package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.init.registry.ItemRegistry;
import com.naxanria.poopcraft.items.ItemBase;

public class PoopItems
{
  public static ItemBase POOP = new ItemBase("poop");
  
  public static void register(ItemRegistry registry)
  {
    registry.addAll
    (
      POOP
    );
  }
}
