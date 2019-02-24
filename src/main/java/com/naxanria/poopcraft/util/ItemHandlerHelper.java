package com.naxanria.poopcraft.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerHelper
{
  /**
   * Tries to insert an itemstack into the item handler
   * Will return the remaining itemstack
   * @param handler the handler to insert into
   * @param stack the itemstack to try and insert
   * @return remaining itemstack
   */
  public static ItemStack insert(IItemHandler handler, ItemStack stack)
  {
    stack = stack.copy();
    
    for (int slot = 0; slot < handler.getSlots(); slot++)
    {
      if (handler.isItemValid(slot, stack))
      {
        ItemStack remainder = handler.insertItem(slot, stack, false);
        
        if (remainder.isEmpty())
        {
          return remainder;
        }
        
        stack.setCount(remainder.getCount());
      }
    }
    
    return stack;
  }
}
