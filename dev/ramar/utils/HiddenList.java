package dev.ramar.utils;

import java.util.*;


public class HiddenList<E>
{
    protected final List<E> list = new ArrayList<>();

    public HiddenList()
    {

    }

    public String toString()
    {
        return list.toString();
    }


    /* Accessors
    --===----------
    */

    public boolean isEmpty()
    {   return this.list.isEmpty();   }

    public boolean isntEmpty()
    {   return !this.isEmpty();   }


    public int size()
    {   return list.size();   }

    public boolean contains(E e)
    {   return list.contains(e);   }


    
    /* Mutators
    --===---------
    */

    public void add(E e)
    {
        synchronized(this)
        {
            list.add(e);
        }
    }

    public void remove(E e)
    {
        synchronized(this)
        {
            list.remove(e);
        }
    }

    public void remove(int i)
    {
        synchronized(this)
        {
            list.remove(i);
        }
    }

    public void removeAll(E... toRemove)
    {
        for( E e : toRemove )
            remove(e);
    }

}
