package dev.ramar.utils;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class BaseListeners<E>
{
    protected final List<E> listeners = new ArrayList<>();
    private final List<E> toRemove = new LinkedList<>();

    public BaseListeners() {}


    /*
    Protected method: clean
     - removes all that have been queued to be removed
    */
    protected void clean()
    {
        listeners.removeAll(toRemove);
    }

    public void add(E val)
    {
        listeners.add(val);
    }

    /*
    Modifier: remove
     - queues <val> to be removed on the next clean() call
     - any implementing class should always clean() before
       their listeners should fire, i don't think there's a
       way to force this while still having the nice writability
       i want for this class, though-
    */
    public void remove(E val)
    {
        toRemove.add(val);
    }

    public boolean contains(E val)
    {
        return (!toRemove.contains(val)) && listeners.contains(val);
    }

}