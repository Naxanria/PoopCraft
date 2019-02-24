package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.blocks.BlockComposter;
import com.naxanria.poopcraft.init.PoopFluids;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.base.TileEntityTickingBase;
import com.naxanria.poopcraft.tile.base.inventory.ItemStackHandlerBase;
import com.naxanria.poopcraft.util.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileComposter extends TileEntityTickingBase implements IFluidHandler
{
  private ItemStackHandlerBase poopStorage = new ItemStackHandlerBase(Settings.COMPOSTER.poopStorage).setSlotValidator(this::isItemPoop);
  private ItemStackHandlerBase compostStorage = new ItemStackHandlerBase(Settings.COMPOSTER.compostStorage).setExtractOnly();
  private FluidTank methaneTank = new FluidTank(new FluidStack(PoopFluids.METHANE, 0), Settings.COMPOSTER.methaneStorage);
  
  private ItemStack converting;
  private PoopCapabilities capabilities;
  
  private int convertTime = 0;
  private int convertTotalTime = 100;
  
  private int compostTotal = 0;
  
  private EnumFacing currentFacing;
  
  @Override
  protected void entityUpdate()
  {
    if (world.isRemote)
    {
      return;
    }
    
    if (converting == null)
    {
      if (hasSpace())
      {
        converting = getNextToConverting();
  
        if (converting != null)
        {
          capabilities = SettingsHelper.getPoopCapabilities(converting);
  
          if (capabilities == null)
          {
            if (converting.getItem() != Items.AIR)
            {
              PoopCraft.logger.error(StackUtil.getItemId(converting) + " is not a valid poop!.");
              new Exception().printStackTrace();
            }
            converting = null;
          }
          else
          {
            convertTotalTime = capabilities.compostSpeed;
            convertTime = 0;
          }
        }
      }
    }
  
    if (converting != null)
    {
      if (convertTime++ < convertTotalTime)
      {
        //PoopCraft.logger.info("converting... " + convertTime + " " + StackUtil.getItemId(converting) + " | " + compostTotal + "|" + methaneStored);
  
        compostTotal += capabilities.compostAmount;
        
        methaneTank.fill(new FluidStack(PoopFluids.METHANE, capabilities.methaneAmount), true);
      }
      else
      {
        converting = null;
        capabilities = null;
        convertTime = 0;
      }
    }
  
    if (compostTotal >= Settings.COMPOSTER.compostAmountNeeded)
    {
      if (createCompost())
      {
        compostTotal -= Settings.COMPOSTER.compostAmountNeeded;
      }
    }
  
    if (getTicks() % 5 == 0)
    {
      sendCompostAway();
    }
    
    markDirty();
  }
  
  private void sendCompostAway()
  {
    EnumFacing compostFacing = getCurrentFacing().getOpposite();
    BlockPos posToCheck = pos.offset(compostFacing);
  
    TileEntity te = world.getTileEntity(posToCheck);
  
    if (te != null)
    {
      if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, compostFacing.getOpposite()))
      {
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, compostFacing.getOpposite());
        if (handler != null)
        {
//          PoopCraft.logger.info(compostFacing.toString() + " " + compostFacing.getOpposite().toString() + " " + te.getClass().getCanonicalName());
        
          int total = 0;
          int max = Settings.COMPOSTER.autoOutputAmount;
        
          for (int i = 0; i < compostStorage.getSlots(); i++)
          {
            ItemStack stack = compostStorage.getStackInSlot(i);
          
            if (stack.isEmpty())
            {
              continue;
            }
          
            int amount = Math.min(Math.max(max, total - max), stack.getCount());
            
//            PoopCraft.logger.info("Amount to transfer: " + amount);
          
            ItemStack insert = stack.copy();
            insert.setCount(amount);
          
            ItemStack remainder = ItemHandlerHelper.insert(handler, insert);
          
            total += amount - remainder.getCount();
            stack.shrink(amount - remainder.getCount());
          
            if (total >= max)
            {
              break;
            }
          }
        }
      }
    }
  }
  
  private boolean hasSpace()
  {
    if (methaneTank.getFluidAmount() != methaneTank.getCapacity())
    {
      for (int i = 0; i < compostStorage.getSlots(); i++)
      {
        ItemStack stack = compostStorage.getStackInSlot(i);
        
        if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize())
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  private boolean createCompost()
  {
    for (int i = 0; i < compostStorage.getSlots(); i++)
    {
      ItemStack stack = compostStorage.getStackInSlot(i);
      
      if (stack.isEmpty())
      {
        compostStorage.setStackInSlot(i, new ItemStack(PoopItems.COMPOST));
        
        return true;
      }
      else
      {
        if (stack.getCount() < stack.getMaxStackSize())
        {
          stack.setCount(stack.getCount() + 1);
          
          return true;
        }
      }
    }
    
    return false;
  }
  
  public ItemStack getNextToConverting()
  {
//    PoopCraft.logger.info("Trying to get next for converting");
    for (int i = 0; i < poopStorage.getSlots(); i++)
    {
      ItemStack stack = poopStorage.getStackInSlot(i);
      
//      PoopCraft.logger.info("Checking for slot=" + i + ";item=" + StackUtil.getItemId(stack));
      
      if (stack.isEmpty() || stack.getItem() == Items.AIR)
      {
        continue;
      }
      
      stack.setCount(stack.getCount() - 1);
  
      ItemStack convertingStack = stack.copy();
      convertingStack.setCount(1);
      
//      PoopCraft.logger.info("Trying to convert " + StackUtil.getItemId(convertingStack));
      
      return convertingStack;
    }
    
    return null;
  }
  
  private boolean isItemPoop(int slot, ItemStack stack)
  {
    PoopCraft.logger.info("Checking for: " + StackUtil.getItemId(stack));
    
    PoopCapabilities capabilities = SettingsHelper.getPoopCapabilities(stack);
    
    PoopCraft.logger.info(capabilities);
    
    return SettingsHelper.hasPoopCapabilities(stack);
  }
  
  private EnumFacing getCurrentFacing()
  {
    if (currentFacing != null)
    {
      return currentFacing;
    }
    
    return currentFacing = getProperty(BlockComposter.FACING);
  }
  
  public void invalidateCurrentFacing()
  {
    currentFacing = null;
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      if (facing == EnumFacing.UP || getCurrentFacing().getOpposite() == facing)
      {
        return true;
      }
    }
    
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    {
      if (facing == getCurrentFacing())
      {
        return true;
      }
    }
    
    return super.hasCapability(capability, facing);
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      if (facing == EnumFacing.UP)
      {
        return (T) poopStorage;
      }
      else if (FacingHelper.getLeft(getCurrentFacing()) == facing)
      {
        return (T) compostStorage;
      }
    }
    
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == getCurrentFacing())
    {
      return (T) this;
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
    return 0;
  }
  
  @Nullable
  @Override
  public FluidStack drain(FluidStack resource, boolean doDrain)
  {
    if (resource.getFluid() != PoopFluids.METHANE)
    {
      return null;
    }
    
    int maxDrain = Math.min(resource.amount, methaneTank.getFluidAmount());
    
//    if (doDrain)
//    {
//      methaneTank.drainInternal(maxDrain, true);
//    }
    
    return methaneTank.drainInternal(maxDrain, doDrain);// new FluidStack(PoopFluids.METHANE, maxDrain);
  }
  
  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain)
  {
    return drain(new FluidStack(PoopFluids.METHANE, maxDrain), doDrain);
  }
  
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound)
  {
    compound.setTag("PoopStorage", poopStorage.serializeNBT());
    compound.setTag("CompostStorage", compostStorage.serializeNBT());
    compound.setTag("MethaneTank", methaneTank.writeToNBT(new NBTTagCompound()));
    
    if (converting != null)
    {
      NBTTagCompound convertingTag = new NBTTagCompound();
      converting.writeToNBT(convertingTag);
      
      compound.setTag("Converting", convertingTag);
    }
    
    compound.setInteger("ConvertTime", convertTime);
    compound.setInteger("ConvertTotalTime", convertTotalTime);
    compound.setInteger("CompostTotal", compostTotal);
    
    super.writeSyncableNBT(compound);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound)
  {
    poopStorage.deserializeNBT(compound.getCompoundTag("PoopStorage"));
    compostStorage.deserializeNBT(compound.getCompoundTag("CompostStorage"));
    methaneTank.readFromNBT(compound.getCompoundTag("MethaneTank"));
    
    if (compound.hasKey("Converting"))
    {
      if (converting == null)
      {
        converting = new ItemStack(Items.AIR);
      }
      
      converting.deserializeNBT(compound.getCompoundTag("Converting"));
    }
    
    convertTime = compound.getInteger("ConvertTime");
    convertTotalTime = compound.getInteger("ConvertTotalTime");
    compostTotal = compound.getInteger("CompostTotal");
    
    super.readSyncableNBT(compound);
  }
}
