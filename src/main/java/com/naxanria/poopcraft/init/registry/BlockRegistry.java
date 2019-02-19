package com.naxanria.poopcraft.init.registry;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.blocks.base.BlockTileBase;
import com.naxanria.poopcraft.blocks.base.IBlockBase;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry extends Registry<IBlockBase, IForgeRegistry<Block>>
{
  @Override
  public void register(IForgeRegistry<Block> registry, IBlockBase iBlockBase)
  {
    if (iBlockBase == null)
    {
      PoopCraft.logger.error("Tried to register a block that is null");
      
      new Exception().printStackTrace();
      return;
    }
    
    registry.register(iBlockBase.getBlock());
  }
  
  public void registerItemBlocks(IForgeRegistry<Item> registry)
  {
    for (IBlockBase b :
      toRegister)
    {
      if (b == null)
      {
        continue;
      }
      
      registry.register(b.createItemBlock());
      
      if (b instanceof BlockTileBase)
      {
        ((BlockTileBase) b).registerTileEntity();
      }
    }
  }
  
  public void registerModels()
  {
    for (IBlockBase b :
      toRegister)
    {
      b.registerItemModel();
    }
  }
}
