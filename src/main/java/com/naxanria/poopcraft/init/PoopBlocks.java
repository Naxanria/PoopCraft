package com.naxanria.poopcraft.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("poopcraft")
public class PoopBlocks
{
  public static final Block TOILET = Blocks.AIR; // new BlockToilet();
  public static final Block COMPOSTER = Blocks.AIR;// new BlockComposter();
  public static final Block TURBINE = Blocks.AIR;// new BlockTurbine();
  
  public static final Block METHANE = Blocks.AIR;
  
//  public static void register(BlockRegistry registry)
//  {
//    registry.addAll
//    (
//      TOILET,
//      COMPOSTER,
//      TURBINE
//    );
//  }
}
