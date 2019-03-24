package com.naxanria.poopcraft.network;

import com.naxanria.poopcraft.PoopCraft;
import com.naxanria.poopcraft.gui.IButtonResponder;
import com.naxanria.poopcraft.tile.base.TileEntityBase;
import com.naxanria.poopcraft.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class PacketHandler
{
  public static SimpleNetworkWrapper networkWrapper;
  
  public static final Map<Integer, DataHandler> DATA_HANDLERS = new HashMap<>();
  
  public static class HANDLERS
  {
    public static final DataHandler TILE_ENTITY_HANDLER = new DataHandler("TileEntityHandler")
    {
      @Override
      public void handleData(NBTTagCompound compound, MessageContext context)
      {
//        PoopCraft.logger.info("Tile Data");
        World world = Minecraft.getMinecraft().world;
      
        if (world != null)
        {
          TileEntity tileEntity = world.getTileEntity(NBTHelper.readBlockPos(compound));
        
          if (tileEntity instanceof TileEntityBase)
          {
            ((TileEntityBase) tileEntity).readSyncableNBT(compound.getCompoundTag("Data"));
          }
        }
      }
    };
    
    public static final DataHandler GUI_BUTTON_TO_TILE_HANDLER = new DataHandler("GuiButtonToTileHandler")
    {
      @Override
      public void handleData(NBTTagCompound compound, MessageContext context)
      {
        World world = NBTHelper.readWorld(compound);
        TileEntity tile = world.getTileEntity(NBTHelper.readBlockPos(compound));
        
        if (tile instanceof IButtonResponder)
        {
          IButtonResponder responder = (IButtonResponder) tile;
  
          Entity entity = world.getEntityByID(compound.getInteger("PlayerId"));
          if (entity instanceof EntityPlayer)
          {
            int buttonId = compound.getInteger("ButtonId");
            
            EntityPlayer player = (EntityPlayer) entity;
            
            if (responder.isButtonEnabled(buttonId, player))
            {
              responder.onButtonPressed(buttonId, player);
            }
          }
        }
      }
    };
  }
  
  public static void init()
  {
    Logger logger = PoopCraft.logger;
    
    logger.info("Setting up networking");
    
    networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(PoopCraft.MODID);
    
    networkWrapper.registerMessage(Packet.ServerToClient.Handler.class, Packet.ServerToClient.class, 0, Side.CLIENT);
    networkWrapper.registerMessage(Packet.ClientToServer.Handler.class, Packet.ClientToServer.class, 1, Side.SERVER);
    
    logger.info("Registering handlers");
    
    addAllHandlers();
    
    logger.info("Registered " + DATA_HANDLERS.keySet().size() + " network handlers");
  }
  
  public static DataHandler getHandler(int id)
  {
    return DATA_HANDLERS.get(id);
  }
  
  private static void addAllHandlers()
  {
    addAll
    (
      HANDLERS.TILE_ENTITY_HANDLER,
      HANDLERS.GUI_BUTTON_TO_TILE_HANDLER
    );
  }
  
  private static void addAll(DataHandler... handlers)
  {
    for (DataHandler handler :
      handlers)
    {
      DATA_HANDLERS.put(handler.id, handler);
    }
  }
}
