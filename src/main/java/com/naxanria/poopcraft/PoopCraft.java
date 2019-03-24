package com.naxanria.poopcraft;

import com.naxanria.poopcraft.capability.PlayerPoopCapability;
import com.naxanria.poopcraft.command.CommandPoopCraft;
import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import com.naxanria.poopcraft.handler.ClientEventHandler;
import com.naxanria.poopcraft.handler.ConfigHandler;
import com.naxanria.poopcraft.handler.EventHandler;
import com.naxanria.poopcraft.network.PacketHandler;
import com.naxanria.poopcraft.proxy.CommonProxy;
import com.naxanria.poopcraft.tab.PoopCraftTab;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod
(
  modid = PoopCraft.MODID,
  name = PoopCraft.NAME,
  version = PoopCraft.VERSION,
  guiFactory = PoopCraft.PACKAGE + ".gui.ModGuiFactory"
)
public class PoopCraft
{
  static
  {
    net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
  }
  
  public static final String MODID = "poopcraft";
  public static final String NAME = "Poop Craft";
  public static final String VERSION = "${version}";
  
  public static final String PACKAGE = "com.naxanria." + MODID;
  public static final String PROXY = PACKAGE + ".proxy";
  
  public static PoopCraftTab tab;
  
  public static Logger logger;
  
  @Mod.Instance
  public static PoopCraft instance;
  
  @SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".ServerProxy")
  public static CommonProxy proxy;
  
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    logger = event.getModLog();
    logger.info("PreInit");
  
    File configFile = new File(event.getSuggestedConfigurationFile().getParent(), MODID + "/" + MODID + ".cfg");
  
    ConfigHandler.config = new Configuration(configFile);
    ConfigHandler.init();
    
    PacketHandler.init();
    
    proxy.registerHandler(new EventHandler());
    proxy.registerHandler(new ClientEventHandler());
    
    tab = new PoopCraftTab();
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    logger.info("Init");
  
    PlayerPoopCapability.register();
  }
  
  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {
    logger.info("Post");
  
    ItemPoopCapabilities.init();
    EntityPoopCapabilities.init();
  }
  
  @Mod.EventHandler
  public static void onServerStarting(FMLServerStartingEvent event)
  {
    event.registerServerCommand(new CommandPoopCraft());
  }
}
