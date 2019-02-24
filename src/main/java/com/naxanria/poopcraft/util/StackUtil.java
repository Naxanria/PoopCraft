package com.naxanria.poopcraft.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StackUtil
{
  public static String getItemId(ItemStack stack)
  {
    String modid = getModId(stack);
    ResourceLocation location = stack.getItem().getRegistryName();
    
    if (location == null)
    {
      return modid + ":" + "UNKNOWN";
    }
    
    String name = location.getResourcePath();;
    int meta = stack.getMetadata();
  
    return modid + ":" + name + (meta != 0 ? "@" + meta : "" ); // "@" + meta;
  }
  
  public static boolean areIdsSame(String id1, String id2)
  {
    if (id1.equals(id2))
    {
      return true;
    }
    
    if (id1.contains("@0"))
    {
      id1 = id1.substring(0, id1.length() - 2);
    }
    else if (id2.contains("@0"))
    {
      id2 = id2.substring(0, id2.length() - 2);
    }
    
    return id1.equals(id2);
  }
  
  public static String getNoMetaId(String id)
  {
    int pos = id.indexOf('@');
    if (pos != -1)
    {
      id = id.substring(0, pos);
    }
    
    return id;
  }
  
  private static String getItemId(Item item)
  {
    return getItemId(new ItemStack(item));
  }
  
  public static String getModId(ItemStack stack)
  {
    return stack.getItem().getCreatorModId(stack);
  }
  
  public static boolean isValid(ItemStack stack)
  {
    if (stack == null)
    {
      return false;
    }
    
    return !stack.isEmpty();
  }
  
}
