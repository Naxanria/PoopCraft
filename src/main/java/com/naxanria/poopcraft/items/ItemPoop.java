package com.naxanria.poopcraft.items;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemPoop extends Item// extends ItemBase
{
  public static List<ItemPoop> poops = new ArrayList<>();
  
  private ItemPoopCapabilities defaultCapabilities;
  
  public ItemPoop(String name, int methaneAmount, int compostAmount, int compostTime)
  {
    setCreativeTab(PoopCraft.tab);
    setRegistryName(name);
    setUnlocalizedName(PoopCraft.MODID + "." + name);
    
    defaultCapabilities = new ItemPoopCapabilities();
    defaultCapabilities.methaneAmount = methaneAmount;
    defaultCapabilities.compostAmount = compostAmount;
    defaultCapabilities.compostTime = compostTime;
    defaultCapabilities.id = PoopCraft.MODID + ":" + name;//StackUtil.getItemId(new ItemStack(this));
    
    poops.add(this);
  }
  
  public ItemPoopCapabilities getDefaultCapabilities()
  {
    return defaultCapabilities;
  }
}
