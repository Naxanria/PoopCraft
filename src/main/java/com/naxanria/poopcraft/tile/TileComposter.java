package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.blocks.BlockComposter;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileComposter extends TileEntityTickingBase
{
  private ItemStackHandlerBase poopStorage = new ItemStackHandlerBase(Settings.COMPOSTER.poopStorage).setSlotValidator(this::isItemPoop);
  private ItemStackHandlerBase compostStorage = new ItemStackHandlerBase(Settings.COMPOSTER.compostStorage).setExtractOnly();
  
  private int methaneStored = 0;
  private int methaneCapacity = Settings.COMPOSTER.methaneStorage;
  
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
        methaneStored += capabilities.methaneAmount;
  
        if (methaneStored >= methaneCapacity)
        {
          
          methaneStored = methaneCapacity / 2;
        }
      } else
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
    if (methaneStored < methaneCapacity)
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
      if (facing == EnumFacing.UP || FacingHelper.getLeft(getCurrentFacing()) == facing)
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
    
    return super.getCapability(capability, facing);
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound)
  {
    compound.setTag("PoopStorage", poopStorage.serializeNBT());
    compound.setTag("CompostStorage", compostStorage.serializeNBT());
    
    if (converting != null)
    {
      NBTTagCompound convertingTag = new NBTTagCompound();
      converting.writeToNBT(convertingTag);
      
      compound.setTag("Converting", convertingTag);
    }
    
    compound.setInteger("MethaneStored", methaneStored);
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
    
    if (compound.hasKey("Converting"))
    {
      if (converting == null)
      {
        converting = new ItemStack(Items.AIR);
      }
      
      converting.deserializeNBT(compound.getCompoundTag("Converting"));
    }
    
    methaneStored = compound.getInteger("MethaneStored");
    convertTime = compound.getInteger("ConvertTime");
    convertTotalTime = compound.getInteger("ConvertTotalTime");
    compostTotal = compound.getInteger("CompostTotal");
    
    super.readSyncableNBT(compound);
  }
}
