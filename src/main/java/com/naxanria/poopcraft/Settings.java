package com.naxanria.poopcraft;

import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class Settings
{
  public static class TURBINE
  {
    public static int generatePerTick = 300;
    public static int timeToBurn = 2;
    public static int methaneAmount = 30;
    
    public static int tankCapacity = 2 * Fluid.BUCKET_VOLUME;
    
    public static int energyCapacity = 50000;
    public static int energyTransferMax = 1500;
  }
  
  public static class COMPOSTER
  {
    public static int poopStorage = 1;
    public static int methaneStorage = 10 * Fluid.BUCKET_VOLUME;
    public static int compostStorage = 9;
    public static int compostAmountNeeded = 120;
    
    public static int autoOutputAmount = 4;
  }
  
  
  public static void init()
  {
    // todo: load in the poop data with json
    ItemPoopCapabilities.init();
    EntityPoopCapabilities.init();
  }
  
  
}
