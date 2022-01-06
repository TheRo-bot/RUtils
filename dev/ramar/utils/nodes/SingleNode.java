package dev.ramar.utils.nodes;

import dev.ramar.utils.nodes.Node.NodeBuilder;

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


    public Node<E> addLink(E val)
    {
        this.link = getNodeBuilder().build(val);
        return link;
    }


    protected static class SingleNodeBuilder<V> extends NodeBuilder<V>
    {
        protected SingleNodeBuilder()
        {

        }

        Node<V> build(V val, Node... links)
        {
            SingleNode sn = new SingleNode(val);
            if( links.length > 1 )
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
