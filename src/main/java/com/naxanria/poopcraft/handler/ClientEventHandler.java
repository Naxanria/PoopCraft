package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.util.PlayerUtil;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{
  public static final String CAPABILITIES_PREFIX = TextFormatting.DARK_GRAY.toString();
  
  public static final String TOOLTIP_METHANE = "tooltip.poopcraft.methane";
  public static final String TOOLTIP_COMPOST = "tooltip.poopcraft.compost";
  public static final String TOOLTIP_TIME = "tooltip.poopcraft.time";
  
  @SubscribeEvent
  public void onTooltipEvent(ItemTooltipEvent event)
  {
    ItemStack stack = event.getItemStack();
    
    if (!StackUtil.isValid(stack))
    {
      return;
    }
    
    String id = StackUtil.getItemId(stack);
    
    if (Settings.debug && GuiScreen.isShiftKeyDown())
    {
      event.getToolTip().add(TextFormatting.GREEN + id);
    }
  
    ItemPoopCapabilities capabilities = ItemPoopCapabilities.get(stack);
    if (capabilities != null)
    {
      event.getToolTip().add(CAPABILITIES_PREFIX + PoopCraft.proxy.localized(TOOLTIP_COMPOST) + ": " + capabilities.compostAmount * capabilities.compostTime);
      event.getToolTip().add(CAPABILITIES_PREFIX + PoopCraft.proxy.localized(TOOLTIP_METHANE) + ": " + capabilities.methaneAmount * capabilities.compostTime);
      event.getToolTip().add(CAPABILITIES_PREFIX + PoopCraft.proxy.localized(TOOLTIP_TIME) + ": " + capabilities.compostTime);
    }
    
    EntityPlayer player = PlayerUtil.getLocalPlayer();
    
    if (player == null)
    {
      return;
    }
    
    if (player.getName().equalsIgnoreCase("MrGregHimself"))
    {
      event.getToolTip().add(TextFormatting.GOLD + "Items despawn after 5 minutes.");
    }
    
  }
}
