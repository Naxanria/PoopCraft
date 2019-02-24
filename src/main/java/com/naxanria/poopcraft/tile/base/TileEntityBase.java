package com.naxanria.poopcraft.tile.base;

import com.naxanria.poopcraft.network.PacketHelper;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity
{
  protected IBlockState getState()
  {
    return getState(world, pos);
  }
  
  protected IBlockState getState(World world, BlockPos pos)
  {
    return world.getBlockState(pos);
  }
  
  protected <T extends Comparable<T>> T getProperty(IProperty<T> property)
  {
    return getState().getValue(property);
  }
  
  public final void sendUpdate()
  {
    if (world != null && !world.isRemote)
    {
      NBTTagCompound compound = new NBTTagCompound();
      writeSyncableNBT(compound);
      
      PacketHelper.updateAround(this, compound);
    }
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    
    readSyncableNBT(compound);
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    
    writeSyncableNBT(compound);
    
    return compound;
  }
  
  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket()
  {
    NBTTagCompound compound = new NBTTagCompound();
    writeSyncableNBT(compound);
    
    return new SPacketUpdateTileEntity(pos, -1, compound);
  }
  
  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
  {
    readSyncableNBT(pkt.getNbtCompound());
  }
  
  @Override
  public NBTTagCompound getUpdateTag()
  {
    NBTTagCompound compound = new NBTTagCompound();
    writeSyncableNBT(compound);
    
    return compound;
  }
  
  public void writeSyncableNBT(NBTTagCompound compound)
  { }
  
  public void readSyncableNBT(NBTTagCompound compound)
  { }
  
  public int getComparatorStrength()
  {
    return 0;
  }
  
  public int getComparatorStrength(EnumFacing side)
  {
    return getComparatorStrength();
  }
}
