package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.blocks.BlockTurbine;
import com.naxanria.poopcraft.init.PoopFluids;
import com.naxanria.poopcraft.tile.base.TileEntityTickingBase;
import com.naxanria.poopcraft.tile.base.energy.EnergyStorageBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileTurbine extends TileEntityTickingBase implements IFluidHandler
{
  private FluidTank methaneTank = new FluidTank(PoopFluids.METHANE, 0, Settings.TURBINE.tankCapacity);
  private EnergyStorageBase storage = new EnergyStorageBase(Settings.TURBINE.energyCapacity, Settings.TURBINE.energyTransferMax, 0);
  private int totalTime = 0;
  private int burnTime = 0;
  private boolean burning = false;
  
  private EnumFacing currentFacing;
  
  @Override
  protected void entityUpdate()
  {
    if (world.isRemote)
    {
      return;
    }
    
    boolean dirty = false;
    
    if (!burning)
    {
      if (methaneTank.getFluidAmount() >= Settings.TURBINE.methaneAmount)
      {
        if (storage.getEnergyStored() + Settings.TURBINE.generatePerTick * Settings.TURBINE.timeToBurn <= storage.getMaxEnergyStored())
        {
          burning = true;
  
          burnTime = 0;
          totalTime = Settings.TURBINE.timeToBurn;
  
          methaneTank.drainInternal(Settings.TURBINE.methaneAmount, true);
  
//          PoopCraft.logger.info("Started a burn cycle");
  
          dirty = true;
        }
      }
    }
    
    if (burning)
    {
      if (burnTime++ <= totalTime)
      {
//        PoopCraft.logger.info("Producing " + burnTime + "/" + totalTime);
        
        storage.receiveInternal(Settings.TURBINE.generatePerTick);
        
//        PoopCraft.logger.info("Stored: " + storage.getEnergyStored());
        dirty = true;
      }
      else
      {
        burnTime = 0;
        burning = false;
        
        dirty = true;
      }
    }
    
    dirty = dirty | shareEnergy();
    
    if (dirty)
    {
      markDirty();
    }
  }
  
  private boolean shareEnergy()
  {
    if (storage.getEnergyStored() > 0)
    {
      TileEntity entity = world.getTileEntity(pos.offset(getCurrentFacing().getOpposite()));
      
      if (entity != null)
      {
        if (entity.hasCapability(CapabilityEnergy.ENERGY, getCurrentFacing()))
        {
          IEnergyStorage other = entity.getCapability(CapabilityEnergy.ENERGY, getCurrentFacing());
          
          int amount = Math.min(storage.getMaxDrain(), storage.getEnergyStored());
          int moved = other.receiveEnergy(amount, false);
          
          storage.extractInternal(moved);
          
          return true;
        }
      }
    }
    
    return false;
  }


  private EnumFacing getCurrentFacing()
  {
    if (currentFacing != null)
    {
      return currentFacing;
    }
    
    return currentFacing = getProperty(BlockTurbine.FACING);
  }
  
  public void invalidateCurrentFacing()
  {
    currentFacing = null;
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    {
      return  (getCurrentFacing() == facing);
    }
    
    if (capability == CapabilityEnergy.ENERGY)
    {
      return (getCurrentFacing().getOpposite() == facing);
    }
    
    return super.hasCapability(capability, facing);
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    {
      if (getCurrentFacing() == facing)
      {
        return (T) this;
      }
    }
  
    if (capability == CapabilityEnergy.ENERGY)
    {
      if (getCurrentFacing().getOpposite() == facing)
      {
        return (T) storage;
      }
    }
    
    return super.getCapability(capability, facing);
  }
  
  @Override
  public IFluidTankProperties[] getTankProperties()
  {
    return methaneTank.getTankProperties();
  }
  
  @Override
  public int fill(FluidStack resource, boolean doFill)
  {
    if (resource.getFluid() != PoopFluids.METHANE)
    {
      return 0;
    }
    
    return methaneTank.fillInternal(resource, doFill);
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
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound)
  {
    compound.setTag("MethaneTank", methaneTank.writeToNBT(new NBTTagCompound()));
    compound.setTag("Storage", storage.writeToNBT(new NBTTagCompound()));
    
    compound.setInteger("TotalTime", totalTime);
    compound.setInteger("BurnTime", burnTime);
    compound.setBoolean("Burning", burning);
    
    super.writeSyncableNBT(compound);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound)
  {
    methaneTank.readFromNBT(compound.getCompoundTag("MethaneTank"));
    storage.readFromNBT(compound.getCompoundTag("Storage"));
    
    totalTime = compound.getInteger("TotalTime");
    burnTime = compound.getInteger("BurnTime");
    burning = compound.getBoolean("Burning");
    
    super.readSyncableNBT(compound);
  }
  
}
