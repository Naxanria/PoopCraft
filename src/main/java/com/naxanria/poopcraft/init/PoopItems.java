package com.naxanria.poopcraft.init;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder("poopcraft")
public class PoopItems
{
  /** block items **/
  
  public static final Item COMPOSTER = Items.AIR;
  public static final Item TOILET = Items.AIR;
  public static final Item TURBINE = Items.AIR;
  
  
  /** poop **/
  public static final Item POOP_HUMAN = Items.AIR;
  public static final Item POOP_SOGGY = Items.AIR;
  
  // hostiles
  public static final Item POOP_ZOMBIE = Items.AIR;
  
  // passives
  public static final Item POOP_CHICKEN = Items.AIR;
  
  public static final Item POOP_DEFAULT = Items.AIR;
  
  
  public static final Item COMPOST = Items.AIR;;
  public static final Item COMPOST_BONEMEAL = Items.AIR;;

//  public static void register(ItemRegistry registry)
//  {
//    registry.addAll
//    (
//      POOP_HUMAN,
//      POOP_SOGGY,
//
//      POOP_ZOMBIE,
//
//      POOP_CHICKEN,
//
//      POOP_DEFAULT,
//
//      COMPOST,
//      COMPOST_BONEMEAL
//    );
//  }
}
