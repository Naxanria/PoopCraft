package com.naxanria.poopcraft.init;

import com.naxanria.poopcraft.init.registry.ItemRegistry;
import com.naxanria.poopcraft.items.ItemCompost;
import com.naxanria.poopcraft.items.ItemCompostBonemeal;
import com.naxanria.poopcraft.items.ItemPoop;

public class PoopItems
{
  public static class POOPS
  {
    public static final ItemPoop POOP_HUMAN = new ItemPoop("poop_human", 3, 2, 60);
    public static final ItemPoop POOP_SOGGY = new ItemPoop("poop_soggy", 5, 1, 120);
    
    // hostiles
    public static final ItemPoop POOP_ZOMBIE = new ItemPoop("poop_zombie", 2, 2, 160);
    
    // passives
    public static final ItemPoop POOP_CHICKEN = new ItemPoop("poop_chicken", 4, 2, 15);
    
  }
  
  public static final ItemCompost COMPOST = new ItemCompost();
  public static final ItemCompostBonemeal COMPOST_BONEMEAL = new ItemCompostBonemeal();
  
  public static void register(ItemRegistry registry)
  {
    registry.addAll
    (
      POOPS.POOP_HUMAN,
      POOPS.POOP_SOGGY,
      
      POOPS.POOP_ZOMBIE,
      
      POOPS.POOP_CHICKEN,
      
      COMPOST,
      COMPOST_BONEMEAL
    );
  }
}
