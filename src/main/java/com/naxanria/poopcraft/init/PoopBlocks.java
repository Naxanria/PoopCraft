package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.blocks.BlockBase;
import com.naxanria.poopcraft.blocks.BlockToilet;
import com.naxanria.poopcraft.init.registry.BlockRegistry;

public class PoopBlocks
{
  public static BlockBase TOILET = new BlockToilet();
  
  public static void register(BlockRegistry registry)
  {
    registry.addAll
    (
      TOILET
    );
  }
}
