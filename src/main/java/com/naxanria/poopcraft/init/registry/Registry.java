package com.naxanria.poopcraft.init.registry;
import java.util.ArrayList;
import java.util.List;

public abstract class Registry<TItem, TRegistry>
{
  protected ArrayList<TItem> toRegister = new ArrayList<>();
  
  public Registry<TItem, TRegistry> add(TItem item)
  {
    toRegister.add(item);
    
    return this;
  }
  
  public Registry<TItem, TRegistry> addAll(TItem... items)
  {
    for (TItem item : items)
    {
      add(item);
    }
    
    return this;
  }
  
  public Registry<TItem, TRegistry> addAll(List<TItem> items)
  {
    toRegister.addAll(items);
    
    return this;
  }
  
  public Registry<TItem, TRegistry> registerAll(TRegistry registry)
  {
    for (TItem item :
      toRegister)
    {
      register(registry, item);
    }
    
    return this;
  }
  
  public abstract void register(TRegistry registry, TItem item);
}