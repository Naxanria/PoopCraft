package com.naxanria.poopcraft.command;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public abstract class CommandPoopCraftBase extends CommandBase
{
  @Override
  public int getRequiredPermissionLevel()
  {
    return 2;
  }
  
  @Override
  public String getUsage(ICommandSender sender)
  {
    return "commands.poopcraft." + getName() + ".usage";
  }
}
