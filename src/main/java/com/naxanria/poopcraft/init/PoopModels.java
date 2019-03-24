package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PoopCraft.MODID, value = Side.CLIENT)
public class PoopModels
{
  public static final String INVENTORY = "inventory";
  
  private static final StateMap FLUID_STATE_MAP = new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build();
  
  private static void addModel(Item item, String variant)
  {
    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
  }
  
  private static void addFluidModel(Block block)
  {
    PoopCraft.logger.info("Registering fluid model for " + ((BlockFluidBase) block).getFluid().getName());
    ModelLoader.setCustomStateMapper(block, FLUID_STATE_MAP);
  }
  
  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event)
  {
    addModel(PoopItems.COMPOSTER, INVENTORY);
    addModel(PoopItems.TOILET, INVENTORY);
    addModel(PoopItems.TURBINE, INVENTORY);
    
    addModel(PoopItems.POOP_HUMAN, INVENTORY);
    addModel(PoopItems.POOP_SOGGY, INVENTORY);
    addModel(PoopItems.POOP_ZOMBIE, INVENTORY);
    addModel(PoopItems.POOP_CHICKEN, INVENTORY);
    addModel(PoopItems.POOP_DEFAULT, INVENTORY);
    addModel(PoopItems.COMPOST, INVENTORY);
    addModel(PoopItems.COMPOST_BONEMEAL, INVENTORY);
    
    addFluidModel(PoopBlocks.METHANE);
  }
}
