package com.naxanria.poopcraft.fluid.base;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class FluidBase extends Fluid
{
  public final String name;
  
  public FluidBase(String fluidName, String texture)
  {
    this
      (
        fluidName,
        new ResourceLocation(PoopCraft.MODID, "blocks/" + texture + "_still"),
        new ResourceLocation(PoopCraft.MODID, "blocks/" + texture + "_flowing")
      );
  }
  
  public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing)
  {
    super(fluidName, still, flowing);
    
    name = fluidName;
    
    FluidRegistry.registerFluid(this);
    FluidRegistry.addBucketForFluid(this);
  }
  
  public ItemStack getAsBucket()
  {
    return FluidUtil.getFilledBucket(new FluidStack(this, BUCKET_VOLUME));
  }
}
