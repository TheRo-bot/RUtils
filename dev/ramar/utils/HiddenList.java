package dev.ramar.utils;

import java.util.*;


public class HiddenList<E>
{
    protected final List<E> list = new ArrayList<>();

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

}
