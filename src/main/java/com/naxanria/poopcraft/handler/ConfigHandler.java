package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
  public static Configuration config;
  
  public static final String GENERAL = Configuration.CATEGORY_GENERAL;
  public static final String COMPOSTER = "composter";
  public static final String TURBINE = "turbine";
  
  public static void init()
  {
    if (config == null)
    {
      PoopCraft.logger.error("Config being attempted to be loaded before its initialized");
      return;
    }
    
    config.load();
    
    Settings.playerPoopCooldown = config.getInt("Player Poop Cooldown", GENERAL, 6000, 1000, 300000,
    "The amount of ticks between the player can poop.");
    Settings.makeNonMinecraftCreaturesPoop = config.getBoolean("Non Minecraft Creatures Poop", GENERAL, true,
      "Adds the default poop to other mods entities.");
    Settings.debug = config.getBoolean("Debug", GENERAL, false,
      "Show debug info, like the item id for items in tooltips (hold shift)");
    
    Settings.COMPOSTER.methaneStorage = config.getInt("Methane Storage", COMPOSTER, 5000,  1000, 15000,
      "The amount of methane stored in the composter");
    
    Settings.TURBINE.timeToBurn = config.getInt("Time To Burn", TURBINE, 2, 1, 10,
      "How long the methane will burn for");
    Settings.TURBINE.methaneAmount = config.getInt("Methane Amount", TURBINE, 30, 1, 1000,
      "The amount of methane needed to start a burn cycle");
    Settings.TURBINE.generatePerTick = config.getInt("Generated Per Tick", TURBINE, 300, 1, 50000,
      "The amount of power the turbine generates per tick.");
    
    config.save();
  }
  
}
