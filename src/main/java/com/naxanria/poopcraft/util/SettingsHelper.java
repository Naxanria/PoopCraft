package com.naxanria.poopcraft.util;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import net.minecraft.item.ItemStack;

public class SettingsHelper
{
  public static PoopCapabilities getPoopCapabilities(ItemStack stack)
  {
    String itemId = StackUtil.getItemId(stack);
    
    //PoopCraft.logger.info(itemId);
  
    //    if (capabilities == null)
//    {
//      PoopCraft.logger.info("Couldn't find poop info for " + itemId);
//    }
    
    return Settings.COMPOSTER.poopCapabilities.getOrDefault(itemId, null);
  }
  
  public static boolean hasPoopCapabilities(ItemStack stack)
  {
    return getPoopCapabilities(stack) != null;
  }
  
  public static void registerCapabilities(ItemStack stack, int compost, int methane, int time)
  {
    PoopCapabilities capabilities = new PoopCapabilities();
    capabilities.id = StackUtil.getItemId(stack);
    capabilities.compostAmount = compost;
    capabilities.methaneAmount = methane;
    capabilities.compostSpeed = time;
    
    PoopCraft.logger.info("Registering poop capabilities for " + capabilities.id);
    Settings.COMPOSTER.poopCapabilities.put(capabilities.id, capabilities);
  }
}
