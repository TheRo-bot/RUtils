package dev.ramar.utils;

import dev.ramar.utils.Nodes.Node;

public class SinglyDouble<E> extends LinkedList<E>
{

    public SinglyDouble()
    {
        super();
    }

    public Node<E> newNode()
    {
        return new Nodes.SinglyNode();
    }

    public Node<E> newNode(E val)
    {
        return new Nodes.SinglyNode(val);
    }

    
    @Override
    protected boolean backwardable()
    {
        return false;
    }


    @Override
    protected LinkedList<E> newSelf()
    {
        return (LinkedList<E>)new SinglyDouble<E>();
    }

}