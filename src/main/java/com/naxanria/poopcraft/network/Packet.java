package com.naxanria.poopcraft.network;

import com.naxanria.poopcraft.PoopCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public abstract class Packet implements IMessage
{
  protected NBTTagCompound data;
  protected DataHandler handler;
  
  public Packet()
  { }
  
  public Packet(NBTTagCompound data, DataHandler handler)
  {
    this.data = data;
    this.handler = handler;
  }
  
  @Override
  public void fromBytes(ByteBuf buf)
  {
    PacketBuffer buffer = new PacketBuffer(buf);
    
    try
    {
      data = buffer.readCompoundTag();
      
      int handlerId = buffer.readInt();
      handler = PacketHandler.getHandler(handlerId);
      
      if (handler == null)
      {
        PoopCraft.logger.warn("Unknown packet handler for id " + handlerId);
        new Exception().printStackTrace();
      }
    }
    catch (IOException e)
    {
      PoopCraft.logger.error("Something went wrong receiving a packet.");
      
      e.printStackTrace();
    }
  }
  
  @Override
  public void toBytes(ByteBuf buf)
  {
    PacketBuffer buffer = new PacketBuffer(buf);
    
    buffer.writeCompoundTag(data);
    buffer.writeInt(handler.id);
  }
  
  public static class ClientToServer extends Packet
  {
    public ClientToServer()
    { }
  
    public ClientToServer(NBTTagCompound data, DataHandler handler)
    {
      super(data, handler);
    }
    
    public static class Handler implements IMessageHandler<ClientToServer, IMessage>
    {
      @Override
      public IMessage onMessage(ClientToServer message, MessageContext ctx)
      {
        if (message.data == null || message.handler == null)
        {
          return null;
        }
        
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask
        (
          () ->
          {
            message.handler.handleData(message.data, ctx);
          }
        );
        
        return null;
      }
    }
  }
  
  public static class ServerToClient extends Packet
  {
    public ServerToClient()
    { }
  
    public ServerToClient(NBTTagCompound data, DataHandler handler)
    {
      super(data, handler);
    }
    
    public static class Handler implements IMessageHandler<ServerToClient, IMessage>
    {
      
      @Override
      public IMessage onMessage(ServerToClient message, MessageContext ctx)
      {
        if (message.data == null || message.handler == null)
        {
          return null;
        }
  
        Minecraft.getMinecraft().addScheduledTask
        (
          () ->
          {
            message.handler.handleData(message.data, ctx);
          }
        );
        
        return null;
      }
    }
  }
}
