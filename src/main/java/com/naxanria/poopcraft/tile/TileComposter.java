package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.base.TileEntityTickingBase;
import com.naxanria.poopcraft.tile.base.inventory.ItemStackHandlerBase;
import com.naxanria.poopcraft.util.FacingHelper;
import com.naxanria.poopcraft.util.PoopCapabilities;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileComposter extends TileEntityTickingBase
{
  private ItemStackHandlerBase poopStorage = new ItemStackHandlerBase(Settings.COMPOSTER.poopStorage).setSlotValidator(this::isItemPoop);
  private ItemStackHandlerBase compostStorage = new ItemStackHandlerBase(Settings.COMPOSTER.compostStorage).setExtractOnly();
  
  private int methaneStored = 0;
  private int methaneCapacity = Settings.COMPOSTER.methaneStorage;
  
  private Item converting;
  private PoopCapabilities capabilities;
  
  private int convertTime = 0;
  private int convertTotalTime = 100;
  
  private int compostTotal = 0;
  
  @Override
  protected void entityUpdate()
  {
    if (converting == null)
    {
      if (hasSpace())
      {
        converting = getNextToConverting();
  
        if (converting != null)
        {
          capabilities = getPoopCapabilities(converting);
    
          if (capabilities == null)
          {
            PoopCraft.logger.error(converting.getUnlocalizedName() + " is not a valid poop!.");
      
            converting = null;
          } else
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
        compostTotal += capabilities.compostAmount;
        methaneStored += capabilities.methaneAmount;
        
        if (methaneStored >= methaneCapacity)
        {
          methaneStored = methaneCapacity;
        }
      }
      else
      {
        converting = null;
        capabilities = null;
      }
    }
    
    if (compostTotal >= Settings.COMPOSTER.compostAmountNeeded)
    {
      if (createCompost())
      {
        compostTotal -= Settings.COMPOSTER.compostAmountNeeded;
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
        compostStorage.setStackInSlot(i, new ItemStack(PoopItems.POOP));
        
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
  
  private PoopCapabilities getPoopCapabilities(Item item)
  {
    String itemId = item.getUnlocalizedName();
  
    PoopCraft.logger.info(itemId);
    
    PoopCapabilities capabilities = Settings.COMPOSTER.poopCapabilities.getOrDefault(item, null);
    
    return capabilities;
  }
  
  private boolean hasPoopCapabilities(Item item)
  {
    return getPoopCapabilities(item) != null;
  }
  
  public Item getNextToConverting()
  {
    for (int i = 0; i < poopStorage.getSlots(); i++)
    {
      ItemStack stack = poopStorage.getStackInSlot(i);
      
      if (stack.isEmpty())
      {
        continue;
      }
      
      stack.setCount(stack.getCount() - 1);
      
      return stack.getItem();
    }
    
    return null;
  }
  
  private boolean isItemPoop(int slot, ItemStack stack)
  {
    return hasPoopCapabilities(stack.getItem());
  }
  
  private EnumFacing getCurrentFacing()
  {
    IBlockState state = world.getBlockState(pos);
  
    
    
    return state.getValue(BlockDirectional.FACING);
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      EnumFacing currentFacing = getCurrentFacing();
      
      if (FacingHelper.getLeft(currentFacing) == facing || facing == EnumFacing.UP)
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
      EnumFacing currentFacing = getCurrentFacing();
    
      if (facing == EnumFacing.UP)
      {
        return (T) poopStorage;
      }
      
      else if (FacingHelper.getLeft(currentFacing) == facing)
      {
        return (T) compostStorage;
      }
    }
    
    return super.getCapability(capability, facing);
  }
}
