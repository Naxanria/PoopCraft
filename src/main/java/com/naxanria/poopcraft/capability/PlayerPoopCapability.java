package com.naxanria.poopcraft.capability;

import com.naxanria.poopcraft.PoopCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerPoopCapability
{
  @CapabilityInject(IPlayerPoop.class)
  public static Capability<IPlayerPoop> CAPABILITY = null;
  
  public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(PoopCraft.MODID, "capability.player-poop");
  
  public static void register()
  {
    CapabilityManager.INSTANCE.register(IPlayerPoop.class,
      new Capability.IStorage<IPlayerPoop>()
      {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerPoop> capability, IPlayerPoop instance, EnumFacing side)
        {
          return null;
        }
  
        @Override
        public void readNBT(Capability<IPlayerPoop> capability, IPlayerPoop instance, EnumFacing side, NBTBase nbt)
        {
    
        }
      },
      PlayerPoop::new
    );
  }
  
  public static PlayerPoop getHandler(EntityPlayer player)
  {
    if (player.hasCapability(CAPABILITY, null))
    {
      return (PlayerPoop) player.getCapability(CAPABILITY, null);
    }
    
    return null;
  }
  
  public static class Provider implements ICapabilitySerializable<NBTTagCompound>, ICapabilityProvider
  {
    public PlayerPoop handler = new PlayerPoop();
    
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
      
      if (capability == CAPABILITY)
      {
        return true;
      }
      
      return false;
    }
  
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
      if (capability == CAPABILITY)
      {
        return (T) handler;
      }
      
      return null;
    }
  
    @Override
    public NBTTagCompound serializeNBT()
    {
      return handler.serializeNBT();
    }
  
    @Override
    public void deserializeNBT(NBTTagCompound compound)
    {
      handler.deserializeNBT(compound);
    }
  }

}
