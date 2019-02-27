package com.naxanria.poopcraft.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandPoopCraft extends CommandTreeBase
{
  public CommandPoopCraft()
  {
    addSubcommand(new CommandReload());
  }
  
  @Override
  public String getName()
  {
    return "poopcraft";
  }
  
  @Override
  public String getUsage(ICommandSender sender)
  {
    return "commands.poopcraft.usage";
  }
}
