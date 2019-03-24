package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.blocks.BlockComposter;
import com.naxanria.poopcraft.blocks.BlockToilet;
import com.naxanria.poopcraft.blocks.BlockTurbine;

import com.naxanria.poopcraft.items.ItemCompost;
import com.naxanria.poopcraft.items.ItemCompostBonemeal;
import com.naxanria.poopcraft.items.ItemPoop;
import com.naxanria.poopcraft.tile.TileComposter;
import com.naxanria.poopcraft.tile.TileToilet;
import com.naxanria.poopcraft.tile.TileTurbine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PoopCraft.MODID)
public class PoopInit
{
  private static Block withName(Block block, String name)
  {
    block.setCreativeTab(PoopCraft.tab);
    block.setRegistryName(name);
    block.setUnlocalizedName(PoopCraft.MODID + "." + name);
    
    return block;
  }
  
  private static Item withName(Item item, String name)
  {
    item.setCreativeTab(PoopCraft.tab);
    item.setRegistryName(name);
    item.setUnlocalizedName(PoopCraft.MODID + "." + name);
    
    return item;
  }
  
  private static BlockFluidBase createFluidBlock(Fluid fluid, Material material)
  {
    BlockFluidBase bfb = new BlockFluidClassic(fluid, material);
    
    bfb.setRegistryName(fluid.getName());
    bfb.setUnlocalizedName(fluid.getUnlocalizedName());
    
    PoopCraft.logger.info("Registry name: " + bfb.getRegistryName().toString() + " localization key: " + bfb.getUnlocalizedName());
    
    return bfb;
  }
  
  private static void registerTileEntity(Class<? extends TileEntity> clazz, String name)
  {
    GameRegistry.registerTileEntity(clazz, new ResourceLocation(PoopCraft.MODID, name));
  }
  
  private static Item getItemBlock(Block block)
  {
    return new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath());
  }
  
  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event)
  {
    PoopCraft.logger.info("Registering blocks");
    
    IForgeRegistry<Block> registry = event.getRegistry();
    
    registry.register(withName(new BlockComposter(), "composter"));
    registry.register(withName(new BlockToilet(), "toilet"));
    registry.register(withName(new BlockTurbine(), "turbine"));
  
    registerTileEntity(TileComposter.class, "composter");
    registerTileEntity(TileToilet.class, "toilet");
    registerTileEntity(TileTurbine.class, "turbine");
  
//    Fluid methane = createFluid("methane")
//      .setGaseous(true)
//      .setDensity(-1)
//      .setViscosity(800);
//
    FluidRegistry.registerFluid(PoopFluids.METHANE);
    FluidRegistry.addBucketForFluid(PoopFluids.METHANE);
    
    registry.register(createFluidBlock(PoopFluids.METHANE, PoopMaterials.METHANE));
  }
  
  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event)
  {
    PoopCraft.logger.info("Registering items");
    
    IForgeRegistry<Item> registry = event.getRegistry();
    
    registry.register(getItemBlock(PoopBlocks.TURBINE));
    registry.register(getItemBlock(PoopBlocks.TOILET));
    registry.register(getItemBlock(PoopBlocks.COMPOSTER));
    
    registry.register(new ItemPoop("poop_human", 3, 2, 60));
    registry.register(new ItemPoop("poop_soggy", 5, 1, 120));
    registry.register(new ItemPoop("poop_zombie", 2, 2, 160));
    registry.register(new ItemPoop("poop_chicken", 4, 2, 15));
    registry.register(new ItemPoop("poop_default", 3, 3, 30));
    
    registry.register(withName(new ItemCompost(), "compost"));
    registry.register(withName(new ItemCompostBonemeal(), "compost_bonemeal"));
  }

}
