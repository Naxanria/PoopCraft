package com.naxanria.poopcraft.tile.base.inventory;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerBase extends ItemStackHandler
{
  public interface SlotValidator
  {
    boolean isItemValid(int slot, ItemStack stack);
  }
  
  public interface ContentsChangedHandler
  {
    void onContentsChanged(int slot);
  }
  
  protected SlotValidator slotValidator;
  protected ContentsChangedHandler contentsChangedHandler;
  
  protected boolean canExtract = true;
  protected boolean canInsert = true;
  
  public ItemStackHandlerBase()
  {
    super();
  }
  
  public ItemStackHandlerBase(int size)
  {
    super(size);
  }
  
  public ItemStackHandlerBase(NonNullList<ItemStack> stacks)
  {
    super(stacks);
  }
  
  
  
  public SlotValidator getSlotValidator()
  {
    return slotValidator;
  }
  
  public ItemStackHandlerBase setSlotValidator(SlotValidator slotValidator)
  {
    this.slotValidator = slotValidator;
    return this;
  }
  
  public ContentsChangedHandler getContentsChangedHandler()
  {
    return contentsChangedHandler;
  }
  
  public ItemStackHandlerBase setContentsChangedHandler(ContentsChangedHandler contentsChangedHandler)
  {
    this.contentsChangedHandler = contentsChangedHandler;
    return this;
  }
  
  public ItemStackHandlerBase setExtractOnly()
  {
    canInsert = false;
    canExtract = true;
    
    return this;
  }
  
  public ItemStackHandlerBase setInsertOnly()
  {
    canExtract = false;
    canInsert = true;
    
    return this;
  }
  
  public ItemStackHandlerBase setCanExtract(boolean canExtract)
  {
    this.canExtract = canExtract;
    return this;
  }
  
  public ItemStackHandlerBase setCanInsert(boolean canInsert)
  {
    this.canInsert = canInsert;
    return this;
  }
  
  public boolean isCanExtract()
  {
    return canExtract;
  }
  
  public boolean isCanInsert()
  {
    return canInsert;
  }
  
  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack)
  {
    PoopCraft.logger.info("Checking for slot " + slot + " for stack: " + StackUtil.getItemId(stack));
    return slotValidator == null ? super.isItemValid(slot, stack) : slotValidator.isItemValid(slot, stack);
  }
  
  @Override
  protected void onContentsChanged(int slot)
  {
    if (contentsChangedHandler == null)
    {
      super.onContentsChanged(slot);
    }
    else
    {
      contentsChangedHandler.onContentsChanged(slot);
    }
  }
  
  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
  {
    if (!canInsert)
    {
      return stack;
    }
    
    return super.insertItem(slot, stack, simulate);
  }
  
  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate)
  {
    if (!canExtract)
    {
      return ItemStack.EMPTY;
    }
    
    return super.extractItem(slot, amount, simulate);
  }
}
