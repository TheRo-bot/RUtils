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

    public HiddenList<E> add(E... es)
    {
        synchronized(this)
        {
            for( E e : es)
                list.add(e);
        }
        return this;
    }

    public boolean remove(E e)
    {
        synchronized(this)
        {
            return list.remove(e);
        }
    }

    public E remove(int i)
    {
        synchronized(this)
        {
            return list.remove(i);
        }
    }

    public void remove(E... toRemove)
    {
        synchronized(this)
        {
            for( E e : toRemove )
                remove(e);
        }
    }

}
