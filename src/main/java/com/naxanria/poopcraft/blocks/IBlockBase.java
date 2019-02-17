package com.naxanria.poopcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IBlockBase
{
  void registerItemModel(Item itemBlock);
  
  void registerItemModel();
  
  Item createItemBlock();
  
  Block getBlock();
}
