package com.naxanria.poopcraft.command;

import com.naxanria.poopcraft.data.EntityPoopCapabilities;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandReload extends CommandPoopCraftBase
{
  @Override
  public String getName()
  {
    return "reload";
  }
  
  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
  {
    sender.sendMessage(new TextComponentString("Reloading Poop Craft"));
    long start = System.currentTimeMillis();
  
    ItemPoopCapabilities.reset();
    EntityPoopCapabilities.reset();
    
    ItemPoopCapabilities.init();
    EntityPoopCapabilities.init();
    
    sender.sendMessage(new TextComponentString("Reloading done in " + (System.currentTimeMillis() - start) + "ms"));
  }
}

