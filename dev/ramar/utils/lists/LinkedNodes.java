package dev.ramar.utils.lists;


import java.util.List;
import java.util.ArrayList;

public class LinkedNodes
{

    public interface Node<E>
    {
        public int getNodeCount();

        public Node<E> getNode(int n);

        public void setNode(int n, Node<E> node);

        public E getValue();

        public void setValue(E value);

    }




    public static class OneNode<E> implements Node<E>
    {
        protected Node<E> link;
        protected E value;


        public OneNode()
        {

        }

        public OneNode(E value)
        {
            setValue(value);
        }

        public OneNode(E value, Node<E> link)
        {
            setValue(value);
            setNode(0, link);
        }


        /* Node Implementation
        -===---------------------
        */


        public int getNodeCount() 
        {   return 1;   }


        public Node<E> getNode(int n)
        {
            if( n != 0 )
                throw new IndexOutOfBoundsException("Index " + n + " not 0");

            return link;
        }

        public void setNode(int n, Node<E> node)
        {
            if( n == 0 )
                throw new IndexOutOfBoundsException("Index " + n + " not 0");

            link = node;
        }


        public E getValue()
        {
            return value;
        }


        public void setValue(E value)
        {
            this.value = value;
        }



    }



    public static class TwoNode<E> implements Node<E>
    {
        protected Node<E> linkOne, linkTwo;
        protected E value;


        public TwoNode() {}

        public TwoNode(E value)
        {
            setValue(value);
        }

        public TwoNode(E value, Node<E> linkOne)
        {
            setValue(value);
            setNode(0, linkOne);
        }

        public TwoNode(E value, Node<E> linkOne, Node<E> linkTwo)
        {
            setNode(0, linkOne);
            setNode(1, linkTwo);
            setValue(value);
        }


        public TwoNode<E> withValue(E value)
        {
            setValue(value);
            return this;
        }


        /* Node Implementation
        -===---------------------
        */


        public int getNodeCount() 
        {   return 2;   }


        public Node<E> getNode(int n)
        {
            if( n == 0 )
                return linkOne;
            else if( n == 1 )
                return linkTwo;
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> 1");
        }


        public void setNode(int n, Node<E> node)
        {
            if( n  == 0 )
                linkOne = node;
            else if( n == 1 )
                linkTwo = node;
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> 1");
        }


        public E getValue()
        {
            return value;
        }


        public void setValue(E value)
        {
            this.value = value;
        }

    }



    /*
    Node: NNode
     - Holds an N number of Nodes, useful for graphs??? maybe?
    */
    public static class NNode<E> implements Node<E>
    {
        protected Node<E>[] nodes;
        protected int size = 0;
        protected E value;

        public NNode(int count)
        {
            nodes = new Node[count];
            size = 0;
        }


        public NNode<E> newNode(Node<E> node)
        {
            add(node);
            return this;
        }


        public void add(Node<E> node)
        {
            if( size >= nodes.length )
                throw new IllegalArgumentException("Node has reached max size (" + nodes.length + ")");
            nodes[size] = node;
            size++;
        }

        public Node<E> remove(int index)
        {
            if( index < 0 || index > size - 1 )
                throw new IndexOutOfBoundsException("Index " + index + " not in range 0 -> " + size);

            Node<E> toRem = nodes[index];
            nodes[index] = null;
            size--;

            return toRem;
        }


        /* Node Implementation
        -------------------------
        */

        public int getNodeCount()
        {
            return size;
        }


        public Node<E> getNode(int n)
        {
            if( n >= 0 && n < size )
                return nodes[n];
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> " + size);
        }


        public void setNode(int n, Node<E> node)
        {
            if( n >= 0 && n < nodes.length )
                nodes[n] = node;
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> " + nodes.length);
        }


        public E getValue()
        {
            return value;
        }   


        public void setValue(E value)
        {
            this.value = value;
        }


    }


    public static class GrowableNode<E> implements Node<E>
    {
        private List<Node> nodes = new ArrayList<>();
        private E value;

        public GrowableNode()
        {

        }

        public GrowableNode(E e)
        {
            value = e;
        }

        public void add(Node<E> node)
        {
            nodes.add(node);
        }

        public void remove(Node<E> node)
        {
            nodes.remove(node);
        }

        public Node<E> remove(int index)
        {
            return nodes.remove(index);
        }

        public void remove(E e)
        {
            int index = 0;
            for( Node n : nodes )
            {
                if( n.getValue().equals(e) )
                {
                    remove(index);
                    break;
                }

                index++;
            }
        }

        /* Node Implementation
        -------------------------
        */

        public int getNodeCount()
        {
            return nodes.size();
        }


        public Node<E> getNode(int n)
        {
            if( n >= 0 && n < nodes.size() )
                return nodes.get(n);
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> " + nodes.size());
        }


        public void setNode(int n, Node<E> node)
        {
            if( n >= 0 && n < nodes.size() )
                nodes.set(n, node);
            else
                throw new IndexOutOfBoundsException("Index " + n + " not in range 0 -> " + nodes.size());
        }


        public E getValue()
        {
            return value;
        }   


        public void setValue(E value)
        {
            this.value = value;
        }



    }
}