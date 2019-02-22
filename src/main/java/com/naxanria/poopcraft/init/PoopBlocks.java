package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.blocks.BlockComposter;
import com.naxanria.poopcraft.blocks.BlockTurbine;
import com.naxanria.poopcraft.blocks.base.BlockBase;
import com.naxanria.poopcraft.blocks.BlockToilet;
import com.naxanria.poopcraft.init.registry.BlockRegistry;

public class PoopBlocks
{
  public static final BlockToilet TOILET = new BlockToilet();
  public static final BlockComposter COMPOSTER = new BlockComposter();
  public static final BlockTurbine TURBINE = new BlockTurbine();
  
  public static void register(BlockRegistry registry)
  {
    registry.addAll
    (
      TOILET,
      COMPOSTER,
      TURBINE
    );
  }
}
