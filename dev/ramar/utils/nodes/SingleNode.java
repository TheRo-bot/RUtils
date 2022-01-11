package dev.ramar.utils.nodes;

import java.util.List;
import java.util.ArrayList;

import dev.ramar.utils.nodes.Node.NodeBuilder;


/*
Node: SingleNode
 - A singularly linked node
*/
public class SingleNode<E> extends Node<E>
{
    private Node<E> link;


    public SingleNode(E val)
    {
        super(val);
        link = null;
    }

    public SingleNode(E val, Node<E> link)
    {
        super(val);
        this.link = link;
    }

    public String toString()
    {
        String out = "";

        out += value != null ? value.toString() : "null";

        if( link != null )
            out += " -> " + link.toString();

        return out;
    }

    public Node<E> add(E val)
    {
        if( link != null )
            throw new IllegalStateException("node limit reached");

        link = getNodeBuilder().build(val);
        return link;
    }

    public List<Node<E>> getLinks()
    {
        ArrayList<Node<E>> exp = new ArrayList<>();
        exp.add(link);
        return exp;
    }

    public Node<E> getLink(int n)
    {
        if( n != 0 )
            throw new IndexOutOfBoundsException();
        return link;
    }

    public Node<E> setLink(int i, E val)
    {
        if( i != 0 )
            throw new IndexOutOfBoundsException();

        link = getNodeBuilder().build(val);
        return link;
    }

    public void setLink(int i, Node<E> val)
    {
        if( i != 0 )
            throw new IndexOutOfBoundsException();

        link = val;
    }

    public Node<E> removeLink(int i)
    {
        if( i != 0 )
            throw new IndexOutOfBoundsException();

        Node<E> saved = link;
        link = null;
        return saved;
    }


    public void clear()
    {
        link = null;
    }



    protected static class SingleNodeBuilder<V> extends NodeBuilder<V>
    {
        protected SingleNodeBuilder()
        {

        }

        Node<V> build(V val, Node... links)
        {
            SingleNode sn = new SingleNode(val);
            if( links.length > 2 )
                throw new IllegalArgumentException("Only one link can be established to a SingleNode");
            if( links.length == 1 )
                sn.link = links[0];


            return sn;
        }

    }


    public Node.NodeBuilder getNodeBuilder()
    {
        return new SingleNodeBuilder();
    }


}
