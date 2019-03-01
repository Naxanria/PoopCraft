package com.naxanria.poopcraft.handler;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.Settings;
import com.naxanria.poopcraft.capability.PlayerPoop;
import com.naxanria.poopcraft.capability.PlayerPoopCapability;
import com.naxanria.poopcraft.init.PoopItems;
import com.naxanria.poopcraft.util.PlayerUtil;
import com.naxanria.poopcraft.data.ItemPoopCapabilities;
import com.naxanria.poopcraft.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
  
  private int posX = 0;
  private int posY = 0;
  
  private ItemStack stack = new ItemStack(PoopItems.POOPS.POOP_HUMAN);
  
  @SubscribeEvent
  public void onGameOverlay(RenderGameOverlayEvent.Post event)
  {
    if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
    {
      EntityPlayer player = PlayerUtil.getLocalPlayer();
      
      if (player != null && player.hasCapability(PlayerPoopCapability.CAPABILITY, null))
      {
        PlayerPoop playerPoop = (PlayerPoop) player.getCapability(PlayerPoopCapability.CAPABILITY, null);
        if (playerPoop == null)
        {
          PoopCraft.logger.error("PlayerPoop capability was null!");
          new Exception().printStackTrace();
          return;
        }
        
        
        if (playerPoop.hasToPoop())
        {
          calculatePos(event.getResolution());
          drawHasToPoop();
        }
        
      }
    }
  }
  
  private void drawHasToPoop()
  {
    RenderHelper.disableStandardItemLighting();
    RenderHelper.enableGUIStandardItemLighting();
  
    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    renderItem.renderItemIntoGUI(stack, posX, posY);
    
    RenderHelper.enableStandardItemLighting();
  }
  
  private void calculatePos(ScaledResolution resolution)
  {
    posX = (resolution.getScaledWidth() / 3) * 2;
    posY = (resolution.getScaledHeight() / 4) * 3;
  }
  
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
