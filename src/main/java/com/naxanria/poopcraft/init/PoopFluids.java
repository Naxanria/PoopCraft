package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.fluid.BlockFluidMethane;
import com.naxanria.poopcraft.fluid.FluidMethane;
import com.naxanria.poopcraft.init.registry.FluidRegistry;

public class PoopFluids
{
  public static final FluidMethane METHANE = new FluidMethane();
  public static final BlockFluidMethane BLOCK_METHANE = new BlockFluidMethane(METHANE); //new BlockFluidBase("block_methane", METHANE, PoopMaterials.METHANE);
  
  public static void register(FluidRegistry registry)
  {
    registry.addAll
    (
      BLOCK_METHANE
    );
  }
}
