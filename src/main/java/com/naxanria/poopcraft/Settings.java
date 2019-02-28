package com.naxanria.poopcraft;

import net.minecraftforge.fluids.Fluid;

public class Settings
{
  public static boolean debug = false;
  public static boolean makeNonMinecraftCreaturesPoop = true;
  
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
}
