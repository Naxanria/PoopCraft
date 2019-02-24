package com.naxanria.poopcraft.tile.base.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidHandlerBase implements IFluidHandler
{
  @Override
  public IFluidTankProperties[] getTankProperties()
  {
    return new IFluidTankProperties[0];
  }
  
  @Override
  public int fill(FluidStack resource, boolean doFill)
  {
    return 0;
  }
  
  @Nullable
  @Override
  public FluidStack drain(FluidStack resource, boolean doDrain)
  {
    return null;
  }
  
  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain)
  {
    return null;
  }
}
