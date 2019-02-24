package com.naxanria.poopcraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerUtil
{
  public static EntityPlayer getLocalPlayer()
  {
    Minecraft mc = Minecraft.getMinecraft();
    
    return mc.player;
  }
}
