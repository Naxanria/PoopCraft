package com.naxanria.poopcraft.init.registry;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.fluid.base.BlockFluidBase;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FluidRegistry
{
  private List<BlockFluidBase> toAdd = new ArrayList<>();
  
  public void addAll(BlockFluidBase... fluidBlocks)
  {
    Collections.addAll(toAdd, fluidBlocks);
  }
  
  public void register(IForgeRegistry<Block> registry, BlockFluidBase blockFluidBase)
  {
    registry.register(blockFluidBase);
  
    PoopCraft.logger.info("Registered " + blockFluidBase.getFluid().getUnlocalizedName() + " as a fluid");
  }
  
  public void registerAll(IForgeRegistry<Block> registry)
  {
    toAdd.forEach((b) -> register(registry, b));
  }
}
