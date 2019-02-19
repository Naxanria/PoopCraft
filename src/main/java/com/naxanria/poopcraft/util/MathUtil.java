package com.naxanria.poopcraft.util;

public class MathUtil
{
  public static int clamp(int val, int min, int max)
  {
    return val < min ? min : val > max ? max : val;
  }
  
  public static int clamp015(int val)
  {
    return clamp(val, 0, 15);
  }
}
