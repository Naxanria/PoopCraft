package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class PoopFluids
{
  private static Fluid createFluid(String name)
  {
    Fluid fluid = new Fluid
    (
      name,
      new ResourceLocation(PoopCraft.MODID, "blocks/" + name + "_still"),
      new ResourceLocation(PoopCraft.MODID, "blocks/" + name + "_flowing"),
      new ResourceLocation(PoopCraft.MODID, "blocks/" + name + "_overlay")
    );
    
    fluid.setUnlocalizedName(PoopCraft.MODID + "." + name);
    
    return fluid;
  }
  
  public static final Fluid METHANE = createFluid("methane").setGaseous(true).setDensity(-1).setViscosity(800);
}
