package dev.ramar.utils.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Consumer;


public class Nodes
{

    public static class NodeIterator<E> implements Iterator<E>
    {
        private Node<E> node;


        public NodeIterator(Node<E> node)
        {
            this.node = node;
        }

        public boolean hasNext()
        {
            return node != null;
        }


        public E next()
        {
            E value = null;
            if( hasNext() )
            {
                value = node.getValue();
                
                node = node.next();                
            }


            return value;
        }


        public void forEachRemaining(Consumer<? super E> action)
        {
            while( hasNext() )
                action.accept(next());
        }


    }
    
    public static abstract class Node<E>
    {
        private E value;

        public Node()
        {
            value = null;
        }

        public Node(E val)
        {
            value = val;
        }

        public boolean isEmpty()
        {
            return value == null;
        }


        public boolean equals(Object o)
        {
            boolean equal = false;


            if( o instanceof Node )
            {
                Node compNode = (Node)o;

                if( value == null && compNode.value == null ||
                    value.equals(compNode.value))
                {
                    equal = true;
                }
            }

            return equal;
        }

        public String toString()
        {
            String exp = "";

            if( value != null )
                exp = value.toString();

            if( hasNext() )
                exp += ", " + next().toString();

            return exp;
        }


        public E getValue()
        {
            return value;
        }

        public void setValue(E v)
        {
            value = v;
        }

        public int hashCode()
        {
            int hash = 7;

            if( value != null )
                hash += value.hashCode();

            return hash;
        }

        // if the next/last node exists
        public abstract boolean hasNext();

        public abstract boolean hasLast();

        // to obtain the next/last node
        public abstract Node<E> next();

        public abstract void setNext(Node<E> node);

        public abstract Node<E> last();

        public abstract void setLast(Node<E> node);

        // to obtain the next/last node value
        public E getNext()
        {
            E exp = null;
            if( hasNext() )
                exp = next().getValue();

            return exp;
        }

        public E getLast()
        {
            E exp = null;
            if( hasLast() )
                exp = last().getValue();

            return exp;
        }


        public void wipe()
        {
            value = null;
        }

    }


    public static class SinglyNode<T> extends Node<T>
    {
        private Node<T> next;

        public SinglyNode()
        {
            next = null;
        }

        public SinglyNode(T value)
        {
            super(value);
            this.next = null;
        }

        public SinglyNode(T value, Node<T> next)
        {
            super(value);
            this.next = next;
        }

        @Override
        public boolean hasNext()
        {
            return next != null;
        }


        @Override
        public boolean hasLast()
        {
            return false;
        }


        @Override
        public Node<T> next()
        {
            return next;
        }

        public void setNext(Node<T> node)
        {
            next = node;
        }


        @Override
        public Node<T> last()
        {
            return null;
        }

        public void setLast(Node<T> node)
        {
            throw new UnsupportedOperationException("SinglyNode does not have a last");
        }

        @Override
        public void wipe()
        {
            super.wipe();
            next = null;
        }
    }


    public static class DoublyNode<V> extends Node<V>
    {
        private Node<V> next;
        private Node<V> last;

        public DoublyNode()
        {
            super();
            next = last = null;
        }


        public DoublyNode(V value)
        {
            super(value);
            this.next = null;
            this.last = null;
        }


        public DoublyNode(V value, Node<V> next, Node<V> last)
        {
            super(value);
            this.next = next;
            this.last = last;
        }


        @Override
        public boolean hasNext()
        {
            return next != null;
        }


        @Override
        public boolean hasLast()
        {
            return last != null;
        }


        @Override
        public Node<V> next()
        {
            return next;
        }


        public void setNext(Node<V> node)
        {
            next = node;
        }


        @Override
        public Node<V> last()
        {
            return last;
        }


        public void setLast(Node<V> node)
        {
            last = node;
        }

        @Override
        public void wipe()
        {
            super.wipe();
            next = null;
            last = null;
        }
    }
}