package dev.ramar.utils.nodes;


import dev.ramar.utils.BaseListeners;

import java.util.List;
import java.util.ArrayList;

/*
Abstract Class: Node
 - Representation of a graph node, could have N
   links connected and stores one <E> value
*/
public abstract class Node<E>
{
    public final ListenerContainer<E> listeners = new ListenerContainer<>();

    public static class ListenerContainer<F>
    {
        public final BaseListeners<LinkListener> links = new LinkListeners();
        public final BaseListeners<ValueListener<F>> value = new ValueListeners<F>();
    }



    protected E value;

    public Node(E value)
    {
        this.value = value;
    }

    /* Object-Only Listener methods 
    --===------------------------------
    */

    protected final void onChange_link(Node<E> from, Node<E> to)
    {
        ((LinkListeners)listeners.links).onChange(this, from, to);
    }


    protected final void onChange_value(E from, E to)
    {
        ((ValueListeners<E>)listeners.value).onChange(from, to);
    }



    /* Accessors/Mutators for <value>
    --===--------------------------------
    */

    public final E getValue()
    {  return value;  }

    public final void setValue(E val)
    {
        E old = this.value;
        this.value = val;
        onChange_value(old, val);
    }

    public Node<E> withValue(E val)
    {
        setValue(val);
        return this;
    }


    /* Abstract related Methods
    --==--------------------------
    */

    /* Get Methods
    --===------------
    */

    public abstract Node<E> getLink(int i);
    public abstract E get(int i);


    /* Add Methods
    --===------------
    */

    public abstract void addLink(Node<E> val);
    public Node<E>   withAddLink(Node<E> node)
    { addLink(node); return this; }

    public abstract Node<E> add(E val);
    public Node<E>      withAdd(E val)
    { add(val); return this; }


    /* Remove Methods
    --===-----------------
    */

    public abstract boolean removeLink(Node<E> n);
    public abstract Node<E> remove(int i);


    /* Set Methods
    --===-------------
    */

    public abstract Node<E> setLink(int i, Node<E> val);
    public Node<E>          withLink(int i, Node<E> val)
    { setLink(i, val); return this; }

    public abstract Node<E> set(int i, E val);


    /* Utility Methods
    --===-----------------
    */

    public abstract int size();
    public abstract void clear();
    public abstract boolean isConnected(Node<E> to);
    public abstract List<Node> getLinks();

    /*
    Method: traverse
     - traverses from this node the path.
     - path format: 
        "0,1,4" -> this.getLink(0).getLink(1).getLink(4)
    */
    public Node<E> traverse(String path)
    {
        if( path == null )
            throw new NullPointerException();
        String[] sections = path.split(",");

        Node<E> curr = this;
        for( String s : sections )
        {
            try
            {
                int i = Integer.parseInt(s);
                if( curr != null )
                    curr = curr.getLink(i);
            }
            catch(NumberFormatException e)
            {
                throw new IllegalArgumentException("path is not a comma separated sequence of integers ('" + s + "' is not an integer!");
            }
        }
        return curr;
    }


    /* Listening Classes
    --===-------------------
    */

    public interface LinkListener<F>
    {
        // (basically i can't have new so ---->   old        new)
        public void onChange(Node<F> who, Node<F> o, Node<F> n);
    }

    public interface ValueListener<K>
    {
        public void onChange(K from, K to);
    }



    private static class LinkListeners<F> extends BaseListeners<LinkListener<F>>
    {
        private void onChange(Node<F> owner, Node<F> from, Node<F> to)
        {
            for( LinkListener<F> ll : listeners )
                onChange(owner, from, to);
        }
    }


    private static class ValueListeners<K> extends BaseListeners<ValueListener<K>>
    {
        private void onChange(K from, K to)
        {
            for( ValueListener<K> listener : listeners )
                listener.onChange(from, to);
        }

    } 


}