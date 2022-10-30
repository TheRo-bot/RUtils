package dev.ramar.utils;

import dev.ramar.utils.Nodes.Node;


import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;


public abstract class LinkedList<E> implements List<E>
{

    // prev is node.last()
    // next is node
    private class GenericListerator<E> implements ListIterator<E>
    {

        private LinkedList<E> list;
        private Node<E> node = null;

        // cursor = 0 - list.size() + 1
        private int cursor = 0;

        public GenericListerator(LinkedList<E> list, int startIndex)
        {
            this.list = list;
            node = list.getNode(cursor);
            cursor = startIndex - 1;
        }


        public void add(E e)
        {

        }


        public boolean hasNext()
        {
            return cursor <= list.size();
        }

        public boolean hasPrevious()
        {
            return cursor > 0;
        }

        public E next()
        {
            E value = node.getValue();

            node = node.next();

            cursor++;

            return value;
        }


        public E previous()
        {  
            if( node.hasLast() )
                node = node.last();

            else
                node = list.getNode(cursor - 1);

            E value = node.getValue();
            cursor--;

            return value;

        }


        public int nextIndex()
        {
            return Math.min(cursor + 1, list.size());
        }


        public int previousIndex()
        {
            return Math.max(0, cursor - 1);
        }


        public void remove()
        {
            throw new UnsupportedOperationException();
        }


        public void set(E e)
        {
            throw new UnsupportedOperationException();
        }
    }



    protected Node<E> head, tail;
    protected int size = 0;

    public LinkedList()
    {
        head = newNode();
        tail = head;
    }

    public Node<E> getHead()
    {
        return head;
    }


    public void setHead(Node<E> node)
    {
        if( node == null )
        {
            head.wipe();
        }
        else
            head = node;
    }

    public Node<E> getTail()
    {
        return tail;
    }


    public void setTail(Node<E> node)
    {
        if( node == null )
            tail = head;
        else
            tail = node;
    }

    public abstract Node<E> newNode();

    public abstract Node<E> newNode(E val);

    protected abstract boolean backwardable();

    protected abstract LinkedList<E> newSelf();


    /* List Implementation
    -------------------------
    */


        @Override
    public void add(int index, E element)
    {
        boolean added = false;


        if( index < 0 || index >= size )
            throw new IndexOutOfBoundsException("Index " + index + " Out of range for 0 - " + (size - 1) );


        if( index == 0 )
        {
            // head insert
            Node<E> newNode = newNode(element);
            newNode.setNext(head);
            head = newNode;
            added = true;
        }
        else if( index == size - 1 )
        {
            // tail insert
            Node<E> newNode = newNode(element);
            tail.setNext(newNode);
            tail = newNode;
            added = true;
        }
        else
        {
            // body insert ( 1 -> size - 2)

            // head to the place before we're meant to insert
            Node<E> thisNode = getHead();
            for( int ii = 0; ii < index - 1; ii++ )
                thisNode = thisNode.next();

            // insert a new element here
            Node<E> newNode = newNode(element);

            newNode.setNext(thisNode.next());
            thisNode.setNext(newNode);
            added = true;
        }

        if( added )
            size++;
    }


    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        boolean added = false;
        if( index < 0 || index > size )
            throw new IndexOutOfBoundsException("Index " + index + " Out of range for 0 - " + (size - 1) );

        // body insert
        Node<E> startNode = newNode(),
                thisNode = startNode;

        int addSize = 0;

        Iterator<? extends E> iter = c.iterator();

        while(iter.hasNext())
        {
            thisNode.setValue(iter.next());
            if( iter.hasNext() )
            {
                thisNode.setNext(newNode());
                thisNode = thisNode.next();
            }
            addSize++;
        }

        // thisNode represents tailNode

        if( index == 0 )
        {
            // head insert
            thisNode.setNext(head);
            head = startNode;
            added = true;
        }
        else if( index == size - 1 )
        {
            // tail insert
            addNodeSegments(startNode, thisNode, tail);
            added = true;
        }
        else
        {
            Node<E> insertNode = head;
            for( int ii = 0; ii < index - 1; ii++ )
                insertNode = insertNode.next();

            addNodeSegments(startNode, thisNode, insertNode);
            added = true;
        }


        if( added )
            size += addSize;

        return added;
    }


    private void addNodeSegments(Node<E> start, Node<E> end, Node<E> insertToNext)
    {
        end.setNext(insertToNext.next());
        insertToNext.setNext(start);
        if( end.next() == null )
            tail = end;
    }


    @Override
    public E set(int index, E element)
    {
        throw new UnsupportedOperationException("Modifying elements via Indexing isn't supported by SortedLinkedList");
    }


    private boolean addNode(Node<E> before, Node<E> toAdd)
    {
        before.setNext(toAdd);
        if(! toAdd.hasNext() )
            tail = toAdd;

        return true;
    }

    /* Methods to implement when sub-classing:
    -------------------------------------------------
    */


    public boolean add(E e)
    {
        return addNode(tail, newNode(e));
    }

    public boolean addAll(Collection<? extends E> c)
    {
        boolean allAdded = true;
        for( E e : c )
            allAdded = allAdded && add(e);

        return allAdded;
    }

    public void clear()
    {
        head.wipe();
        tail = head;
        size = 0;
    }

    public boolean contains(Object o)
    {
        Node<E> thisNode = head;

        while(thisNode != null)
        {
            if( o.equals(thisNode.getValue()) )
                return true;

            thisNode = thisNode.next();
        }

        return false;
    }


    public boolean containsAll(Collection<?> c)
    {
        boolean allContained = true;
        for( Object o : c )
            allContained = allContained && contains(o);

        return allContained;
    }

    public boolean equals(Object o)
    {
        boolean equal = false;
        if( o instanceof LinkedList )
        {
            LinkedList in = (LinkedList)o;
            if( size == in.size )
            {
                if( in.head.equals(head) )
                    equal = true;
            }
        }

        return equal;
    }


    public Node<E> getNode(int index)
    {
        Node<E> node = null;
        if( tail.hasLast() )
        {
            if( index / size > 0.5 )
            {
                // go from tail
                Node<E> thisNode = tail;
                for( int ii = 0; ii < size - index; ii++ )
                    thisNode = thisNode.last();

                node = thisNode;
            }
        }
        else
        {
            Node<E> thisNode = head;
            for( int ii = 0; ii < index; ii++ )
                thisNode = thisNode.next();

            node = thisNode;
        }

        return node;
    }


    public E get(int index)
    {

        return getNode(index).getValue();
    }


    public int hashCode()
    {
        int hash = 7;

        Node<E> thisNode = head;

        while(thisNode != null )
        {
            hash += 31 * thisNode.hashCode();

            thisNode = thisNode.next();
        }

        return hash;
    }

    public int indexOf(Object o)
    {
        Node<E> thisNode = head;

        int index = 0;
        while(thisNode != null )
        {
            if( o.equals(thisNode.getValue()) )
                return index;

            thisNode = thisNode.next();
            index++;
        }

        return -1;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public Iterator<E> iterator()
    {
        return new Nodes.NodeIterator(head);
    }

    public int lastIndexOf(Object o)
    {
        Node<E> thisNode = head;

        int index = 0, found = -1;

        while(thisNode != null )
        {
            if( o.equals(thisNode.getValue()))
                found = index;

            thisNode = thisNode.next();
            index++;
        }

        return found;
    }


    public ListIterator<E> listIterator()
    {
        return new GenericListerator<E>(this, 0);
    }


    public ListIterator<E> listIterator(int index)
    {
        return new GenericListerator<E>(this, index);
    }


    public E remove(int index)
    {
        E value = null;
        if( index < 0 || index >= size )
            throw new IndexOutOfBoundsException("Index " + index + " Out of range for 0 - " + (size - 1) );

        if( index == 0 )
        {
            // head delete
            value = head.getValue();

            if( head.next() != null )
                head = head.next();
            head.wipe();
        }
        else
        {
            // anywhere else delete

            if( backwardable() )
            {
                Node<E> node = getNode(index);
                node.last().setNext(node.next());

                if( node.next() == null )
                    tail = node.last();

                node.wipe();
            }
            else
            {
                Node<E> node = getNode(index - 1);
                Node<E> toRemove = node.next();

                node.setNext(toRemove.next());
                if( node.next() == null )
                    tail = node;
                toRemove.wipe();
            }
        }


        return value;
    }

    public boolean remove(Object o)
    {
        Node<E> thisNode = head;
        Node<E> prevNode = null;

        int index = 0;
        while(thisNode != null )
        {

            if( o.equals(thisNode.getValue()) )
                removeNode(thisNode, prevNode);

            prevNode = thisNode;
            thisNode = thisNode.next();
        }

        return false;
    }


    private boolean removeNode(Node<E> node, Node<E> prevNode)
    {
        if( prevNode != null && prevNode.next() != node )
            throw new IllegalStateException("node must be after prevNode in sequence! (" + prevNode.next() + " != " + node + ")" );

        boolean removed = false;


        if( prevNode == null )
        {
            // something to do with a head delete
            if( node.next() == null )
            {
                // size == 1 delete
                node.wipe();
                removed = true;
            }
            else
            {
                // head delete
                head = node.next();
                node.wipe();
                removed = true;
            }
        }
        else
        {
            if( node.next() == null )
            {
                // tail delete
                tail = prevNode;
                prevNode.setNext(null);
                node.wipe();
                removed = true;
            }
            else
            {
                // body delete
                prevNode.setNext(node.next());
                node.wipe();
                removed = true;
            }
        }

        if( removed )
            size--;

        return removed;
    }



    private boolean removeNode(Node<E> node)
    {
        boolean removed = false;
        if( !backwardable() )
            throw new IllegalStateException("This List is not permitted to go backward, but needs backwardable functionality, use removeNode(Node<E>, Node<E>)");

        if( node.last() == null )
        {
            // something to do with a head delete

            if( node.next() == null )
            {
                // size == 1 delete
                node.wipe();
                removed = true;
            }
            else
            {
                // head delete
                head = node.next();
                head.setLast(null);
                node.wipe();
                removed = true;
            }
        }
        else
        {
            if( node.next() == null )
            {
                // tail delete
                tail = node.last();
                tail.setNext(null);
                node.wipe();
                removed = true;
            }
            else
            {
                node.last().setNext(node.next());
                node.next().setLast(node.last());
                node.wipe();
                removed = true;
            }
        }

        if( removed )
            size--;

        return removed;
    }

    public boolean removeAll(Collection<?> c)
    {
        boolean allRemoved = true;
        for( Object o : c )
            allRemoved = allRemoved && remove(c);

        return allRemoved;
    }


    public boolean retainAll(Collection<?> c)
    {
        boolean allRetained = true;
        Node<E> thisNode = head,
                prevNode = null;

        while(thisNode != null )
        {

            if( !c.contains(thisNode.getValue()) )
            {
                allRetained = allRetained && removeNode(prevNode, thisNode);
            }

            prevNode = thisNode;
            thisNode = thisNode.next();
        }

        return allRetained;
    }

    public int size()
    {
        return size;
    }


    public List<E> subList(int fromIndex, int toIndex)
    {
        Node<E> start = getNode(fromIndex),
                end = getNode(toIndex),
                thisNode = start;

        List<E> exp = newSelf();

        while(thisNode != end)
        {

        }

        return exp;
    }

    public Object[] toArray()
    {
        Object[] arr = new Object[size];

        Node<E> thisNode = head;

        int ii = 0;
        while(thisNode != null)
        {
            arr[ii] = thisNode.getValue();

            thisNode = thisNode.next();
            ii++;
        }

        return arr;
    }   

    public <T> T[] toArray(T[] arrayType)
    {

        try
        {
            T value = ((T)head.getValue());
        }
        catch(ClassCastException e)
        {
            throw new ArrayStoreException(e.getMessage());
        }

        if( arrayType.length >= size )
        {
            Node<E> thisNode = head;
            int ii = 0;
            while(thisNode != null )
            {

                arrayType[ii] = (T)thisNode.getValue();

                ii++;
                thisNode = thisNode.next();
            }

            if( ii <= arrayType.length )
                arrayType[ii] = null;
        }
        else
        {
            return null;
        }

        return arrayType;
    }



}