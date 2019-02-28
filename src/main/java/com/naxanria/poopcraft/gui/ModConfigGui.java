package com.naxanria.poopcraft.gui;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.handler.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ModConfigGui extends GuiConfig
{
  public ModConfigGui(GuiScreen parent)
  {
    super(parent, getElements(), PoopCraft.MODID, false, true, PoopCraft.NAME);
  }
  
  private static List<IConfigElement> getElements()
  {
    List<IConfigElement> elements = new ArrayList<>();
  
    for (String cat: ConfigHandler.config.getCategoryNames())
    {
      elements.add(new ConfigElement(ConfigHandler.config.getCategory(cat)));
    }
    
    return elements;
  }
}
