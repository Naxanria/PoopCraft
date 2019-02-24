package com.naxanria.poopcraft;

import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.util.PoopCapabilities;
import com.naxanria.poopcraft.util.SettingsHelper;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class Settings
{
  public static class TURBINE
  {
  
  }
  
  public static class COMPOSTER
  {
    public static int poopStorage = 1;
    public static int methaneStorage = 10 * Fluid.BUCKET_VOLUME;
    public static int compostStorage = 9;
    public static int compostAmountNeeded = 120;
    
    public static int autoOutputAmount = 4;
    
    public static Map<String, PoopCapabilities> poopCapabilities = new HashMap<>();
  }
  
  
  public static void init()
  {
    
    // todo: load in the poop data with json
//
//    PoopCapabilities capabilities = new PoopCapabilities();
//    capabilities.compostAmount = 15;
//    capabilities.compostSpeed = 15;
//    capabilities.methaneAmount = 10;
//    capabilities.id = StackUtil.getItemId(new ItemStack(PoopItems.POOP));
//
//    registerCapabilities(capabilities);
//
    SettingsHelper.registerCapabilities(new ItemStack(PoopItems.POOP), 1, 1, 60);
    
  }
  
  
}
