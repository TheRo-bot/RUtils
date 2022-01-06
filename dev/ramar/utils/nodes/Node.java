package dev.ramar.utils.nodes;


public abstract class Node<E>
{
    protected E value;

    public Node(E value)
    {
        this.value = value;
    }

    public E getValue()
    {  return value;  }

    public void setValue(E val)
    {
        this.value = val;
    }

    public abstract List<Node<E>> getLinks();

    public Node<E> withValue(E val)
    {
        setValue(val);
        return this;
    }



    public abstract Node<E> add(E val);

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


}