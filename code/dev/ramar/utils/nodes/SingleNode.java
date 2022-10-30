package dev.ramar.utils.nodes;

import java.util.List;
import java.util.ArrayList;


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


    public boolean equals(Object in)
    {
        // a **classic** curtin equals(Object) certified method
        boolean isEqual = false;
        if( in != null && in instanceof SingleNode )
        {
            SingleNode<E> sn = (SingleNode<E>)in;

            isEqual = 
            (
                ( value == null && sn.value == null       ) ||
                ( value != null && value.equals(sn.value) )
            ) &&
            (
                ( link == null && sn.link == null      ) ||
                ( link != null && link.equals(sn.link) )
            );
        }

        return isEqual;
    }



    public Node<E> getLink(int n)
    {
        if( n != 0 )
            throw new IndexOutOfBoundsException();
        return link;
    }


    public E get(int i)
    {
        Node<E> link = getLink(i);
    
        return link != null ? link.getValue() : null;        
    }


    /* Add Methods
    --===------------
    */

    public void addLink(Node<E> val)
    {
        if( link == null )
        {
            link = val;        
            onChange_link(null, link);
        }
    }

    public Node<E> add(E val)
    {
        addLink(getNodeBuilder().build(val));
        return link;
    }

    /* Remove Methods
    --===-----------------
    */

    public boolean removeLink(Node<E> n)
    {
        boolean done = false;
        if( link != null && link.equals(link) )
        {
            link = null;
            done = true;
        }
        return done;
    }

    public Node<E> remove(int i)
    {
        if( i != 0 )
            throw new IndexOutOfBoundsException();

        Node<E> saved = link;
        link = null;

        onChange_link(saved, null);

        return saved;
    }


    /* Set Methods
    --===-------------
    */

    public Node<E> setLink(int i, Node<E> node)
    {
        if( i != 0 )
            throw new IndexOutOfBoundsException();

        Node<E> oldLink = link;
        link = node;
        onChange_link(oldLink, link);
        return oldLink;
    }

    public Node<E> set(int i, E val)
    {
        setLink(i, getNodeBuilder().build(val));
        return link;
    }

    /* Utility Methods
    --===-----------------
    */

    public int size()
    {
        return link != null ? 1 : 0;
    }


    public void clear()
    {
        link = null;
    }


    public boolean isConnected(Node<E> n)
    {
        return link != null && link.equals(n);
    }


    public List<Node> getLinks()
    {
        ArrayList<Node> out = new ArrayList<>();
        if( link != null )
            out.add(link);
        
        return out;
    }



    /* Node Builder
    --===-------------
    */  


    protected static class SingleNodeBuilder<V extends Object>
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


    public SingleNodeBuilder<E> getNodeBuilder()
    {
        return new SingleNodeBuilder<E>();
    }


}
