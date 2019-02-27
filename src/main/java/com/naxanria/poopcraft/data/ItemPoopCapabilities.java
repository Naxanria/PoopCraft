package com.naxanria.poopcraft.data;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.items.ItemPoop;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemPoopCapabilities
{
  private static Map<String, ItemPoopCapabilities> poopCapabilities = new HashMap<>();
  
  public String id;
  public int compostAmount;
  public int methaneAmount;
  public int compostTime;
  
  public static ItemPoopCapabilities get(ItemStack stack)
  {
    String itemId = StackUtil.getItemId(stack);
    
    return get(itemId);
  }
  
  public static ItemPoopCapabilities get(String id)
  {
    return poopCapabilities.getOrDefault(id, null);
  }
  
  public static boolean hasCapabilities(ItemStack stack)
  {
    return get(stack) != null;
  }
  
  public static boolean hasCapabilities(String id)
  {
    return get(id) != null;
  }
  
  public static void register(ItemStack stack, int compost, int methane, int time)
  {
    ItemPoopCapabilities capabilities = new ItemPoopCapabilities();
    capabilities.id = StackUtil.getItemId(stack);
    capabilities.compostAmount = compost;
    capabilities.methaneAmount = methane;
    capabilities.compostTime = time;
    
    register(capabilities);
  }
  
  public static void register(ItemPoopCapabilities capabilities)
  {
    PoopCraft.logger.info("Registering poop capabilities for " + capabilities.id);
    poopCapabilities.put(capabilities.id, capabilities);
  }
  
  public static void reset()
  {
    poopCapabilities.clear();
  }
  
  public static void init()
  {
    for (ItemPoop poop :
      ItemPoop.poops)
    {
      ItemPoopCapabilities defaultCapabilities = poop.getDefaultCapabilities();
    
      // check if loaded; if not, register default
      if (!hasCapabilities(defaultCapabilities.id))
      {
        register(defaultCapabilities);
        // todo: need to create default json for this
      }
    }
  
  }
}
