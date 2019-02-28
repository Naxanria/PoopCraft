package com.naxanria.poopcraft.tile;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.tile.base.TileEntityTickingBase;
import com.naxanria.poopcraft.tile.base.inventory.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileToilet extends TileEntityTickingBase
{
  private ItemStackHandlerBase input = new ItemStackHandlerBase(1).setSlotValidator(this::validateSlot).setInsertOnly();
  private ItemStackHandlerBase output = new ItemStackHandlerBase(1).setExtractOnly();
  
  //todo: use water for soggy poop.
  
  @Override
  protected void entityUpdate()
  {
    if (!(getTicks() % 5 == 0))
    {
      return;
    }
    
    ItemStack inputStack = input.getStackInSlot(0);
    if (inputStack.getCount() > 0)
    {
      ItemStack outputStack = output.getStackInSlot(0);
      
      if (outputStack.getCount() < 64)
      {
        int amount = Math.min(inputStack.getCount(), 64 - outputStack.getCount());
        
        if (outputStack.isEmpty() || outputStack.getItem() != PoopItems.POOPS.POOP_SOGGY)
        {
          output.setStackInSlot(0, new ItemStack(PoopItems.POOPS.POOP_SOGGY, amount));
        }
        else
        {
          outputStack.grow(amount);
        }
        
        inputStack.shrink(amount);
        
        if (!world.isRemote)
        {
          markDirty();
        }
      }
    }
  }
  
  private boolean validateSlot(int slot, ItemStack stack)
  {
    return stack.getItem() == PoopItems.POOPS.POOP_HUMAN;
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
  {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      return  (facing == EnumFacing.UP || facing == EnumFacing.DOWN);
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
        return (T) input;
      }
      
      if (facing == EnumFacing.DOWN)
      {
        return (T) output;
      }
    }
    
    return super.getCapability(capability, facing);
  }
}
