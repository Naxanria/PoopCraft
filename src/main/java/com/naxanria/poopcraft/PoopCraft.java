package com.naxanria.poopcraft;

import com.naxanria.poopcraft.command.CommandPoopCraft;
import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import com.naxanria.poopcraft.fluid.base.BlockFluidBase;
import com.naxanria.poopcraft.handler.ClientEventHandler;
import com.naxanria.poopcraft.handler.EventHandler;
import com.naxanria.poopcraft.init.PoopBlocks;
import com.naxanria.poopcraft.init.PoopFluids;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.init.registry.BlockRegistry;
import com.naxanria.poopcraft.init.registry.FluidRegistry;
import com.naxanria.poopcraft.init.registry.ItemRegistry;
import com.naxanria.poopcraft.network.PacketHandler;
import com.naxanria.poopcraft.proxy.CommonProxy;
import com.naxanria.poopcraft.tab.PoopCraftTab;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.Set;

@Mod
(
  modid = PoopCraft.MODID,
  name = PoopCraft.NAME,
  version = PoopCraft.VERSION
)
public class PoopCraft
{
  static
  {
    net.minecraftforge.fluids.FluidRegistry.enableUniversalBucket();
  }
  
  public static final String MODID = "poopcraft";
  public static final String NAME = "PoopCraft";
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
  
    PacketHandler.init();
    
    proxy.registerHandler(new EventHandler());
    proxy.registerHandler(new ClientEventHandler());
    
    tab = new PoopCraftTab();
    
    // todo: make more elegant
    ((BlockFluidBase) PoopFluids.METHANE.getBlock()).render();
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    logger.info("Init");
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
  
  @Mod.EventBusSubscriber
  public static class ObjectRegistryHandler
  {
    private static ItemRegistry itemRegistry;
    private static BlockRegistry blockRegistry;
    private static FluidRegistry fluidRegistry;
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
      logger.info("Registering items.");
      itemRegistry = new ItemRegistry();
      
      PoopItems.register(itemRegistry);
      
      itemRegistry.registerAll(event.getRegistry());
      blockRegistry.registerItemBlocks(event.getRegistry());
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
      logger.info("Registering blocks.");
      
      blockRegistry = new BlockRegistry();
      PoopBlocks.register(blockRegistry);
      blockRegistry.registerAll(event.getRegistry());
     
      logger.info("Registering fluids");
      
      fluidRegistry = new FluidRegistry();
      PoopFluids.register(fluidRegistry);
      fluidRegistry.registerAll(event.getRegistry());
    }
    
//    @SubscribeEvent
//    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
//    {
////      initRecipeClass();
////      instance.recipeRegistry.registerAll(event.getRegistry());
//    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
      itemRegistry.registerModels();
      blockRegistry.registerModels();
    }
  }
}
