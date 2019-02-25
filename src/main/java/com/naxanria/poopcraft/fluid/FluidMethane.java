package com.naxanria.poopcraft.fluid;

import com.naxanria.poopcraft.fluid.base.FluidBase;

public class FluidMethane extends FluidBase
{
  public FluidMethane()
  {
    super
    (
      "fluid_methane",
      "methane"
    );
    
    density = -1;
    temperature = KELVIN + 30;
    
    viscosity = 800;
    
    isGaseous = true;
  }
}
