package com.naxanria.poopcraft.fluid;

import com.naxanria.poopcraft.fluid.base.BlockFluidBase;
import com.naxanria.poopcraft.init.PoopMaterials;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidMethane extends BlockFluidBase
{
  public BlockFluidMethane(Fluid fluid)
  {
    super("block_methane", fluid, PoopMaterials.METHANE);
    
    this.temperature = 20;
  }
  
  
}
