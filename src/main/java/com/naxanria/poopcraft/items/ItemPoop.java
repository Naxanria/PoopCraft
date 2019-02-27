package com.naxanria.poopcraft.items;

import com.naxanria.poopcraft.items.base.ItemBase;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoop extends ItemBase
{
  public static List<ItemPoop> poops = new ArrayList<>();
  
  private ItemPoopCapabilities defaultCapabilities;
  
  public ItemPoop(String name, int methaneAmount, int compostAmount, int compostTime)
  {
    super(name);
    
    defaultCapabilities = new ItemPoopCapabilities();
    defaultCapabilities.methaneAmount = methaneAmount;
    defaultCapabilities.compostAmount = compostAmount;
    defaultCapabilities.compostTime = compostTime;
    defaultCapabilities.id = StackUtil.getItemId(new ItemStack(this));
    
    poops.add(this);
  }
  
  public ItemPoopCapabilities getDefaultCapabilities()
  {
    return defaultCapabilities;
  }
}
