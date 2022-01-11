package dev.ramar.utils.nodes;


import java.util.List;
import java.util.ArrayList;

/*
Abstract Class: Node
 - Representation of a graph node, could have N
   links connected and stores one <E> value
*/
public abstract class Node<E>
{

    public final ValueListeners<E> listeners = new ValueListeners<E>();
    protected E value;

    public Node(E value)
    {
        this.value = value;
    }


    /*
    Listening callback: onChange (ValueListeners)
     - sends the signal to <listeners> to send a callback
    */
    protected final void onChange(E value)
    {
        listeners.onChange(this, value);
    }

    public final E getValue()
    {  return value;  }

    public final void setValue(E val)
    {
        this.value = val;
        onChange(val);
    }

    public abstract List<Node<E>> getLinks();

    public Node<E> withValue(E val)
    {
        setValue(val);
        return this;
    }

    public abstract Node<E> getLink(int n);

    public abstract Node<E> setLink(int i, E val);

    public abstract void setLink(int i, Node<E> val);

    public abstract Node<E> removeLink(int i);

    public abstract Node<E> add(E val);

    public abstract void clear();

    public Node<E> withAdd(E val)
    {
        add(val);
        return this;
    }


    protected abstract NodeBuilder getNodeBuilder();
    static abstract class NodeBuilder<V>
    {
        abstract Node<V> build(V val, Node... links);
    }

    public class ValueListeners<K>
    {
        public interface ValueListener<K>
        {
            public void onChange(K val);
        }

        private List<ValueListener<K>> listeners = new ArrayList<>();

        private ValueListeners()
        {

        }

        public void add(ValueListener<K> vl)
        {
            listeners.add(vl);
        }

        public void remove(ValueListener<K> vl)
        {
            listeners.remove(vl);
        }

        private void onChange(K val)
        {
            for( ValueListener<K> listener : listeners )
                listener.onChange(val);
        }

    } 

}