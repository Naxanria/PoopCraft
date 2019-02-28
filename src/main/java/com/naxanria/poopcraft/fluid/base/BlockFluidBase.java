package com.naxanria.poopcraft.fluid.base;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidBase extends BlockFluidClassic
{
  public BlockFluidBase(String name, Fluid fluid, Material material)
  {
    this(name, fluid, material, material.getMaterialMapColor());
  }
  
  public BlockFluidBase(String name, Fluid fluid, Material material, MapColor mapColor)
  {
    super(fluid, material, mapColor);
    
    setUnlocalizedName(PoopCraft.MODID + "." + name);
    setRegistryName(name);
    
    displacements.put(this, false);
  }
  
  @SideOnly(Side.CLIENT)
  public void render()
  {
    ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
  }
  
  
}
