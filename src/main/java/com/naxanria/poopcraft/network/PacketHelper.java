package com.naxanria.poopcraft.network;

import com.naxanria.poopcraft.tile.base.TileEntityBase;
import com.naxanria.poopcraft.util.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class PacketHelper
{
  public static void updateAround(TileEntityBase tile, NBTTagCompound compound)
  {
    NBTTagCompound data = new NBTTagCompound();
    data.setTag("Data", compound);
  
    BlockPos pos = tile.getPos();
    
    NBTHelper.writeBlockPos(pos, data);
    
    PacketHandler.networkWrapper.sendToAllAround
    (
      new Packet.ServerToClient
      (
        data, PacketHandler.HANDLERS.TILE_ENTITY_HANDLER
      ),
      new NetworkRegistry.TargetPoint
      (
        tile.getWorld().provider.getDimension(),
        pos.getX(),
        pos.getY(),
        pos.getZ(),
        64
      )
    );
  }
}
