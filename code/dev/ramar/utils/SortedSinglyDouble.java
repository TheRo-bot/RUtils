package dev.ramar.utils;

import dev.ramar.utils.Nodes.Node;
import dev.ramar.utils.Nodes.SinglyNode;


import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;

/*
Class: SortedSinglyDouble
 - A kind of LinkedList which sorts the information getting inserted into it,
   hence E being Comparable
*/
public class SortedSinglyDouble<E extends Comparable> extends SortedLinkedList<E>
{

    public class SortedSinglyDoubleListerator<K extends Comparable> implements ListIterator<K>
    {

        private int currIndex = 0;

        private Node<K> thisNode = null,
                        prevNode = null;

        private SortedSinglyDouble<K> list;

        public SortedSinglyDoubleListerator(SortedSinglyDouble<K> list)
        {  
            this.list = list;
            thisNode = list.head;
        }

        public SortedSinglyDoubleListerator(SortedSinglyDouble<K> list, int indexedStart)
        {
            this.list = list;
            thisNode = list.head;

            for(int ii = 0; ii < indexedStart - 1; ii++)
            {
                prevNode = thisNode;
                thisNode = thisNode.next();
            }

        }   


        public void add(K e)
        {
            throw new UnsupportedOperationException("Can't place elements at specific indicies");
        }


        public void set(K e)
        {
            throw new UnsupportedOperationException("Can't modify elements at specific indicies");
        }


        public boolean hasNext()
        {
            return thisNode != null && thisNode.next() != null;
        }


        public K next()
        {
            K value = thisNode.getValue();

            currIndex++;
            prevNode = thisNode;
            thisNode = thisNode.next();

            return value;
        }


        public int nextIndex()
        {
            return currIndex++;
        }


        public boolean hasPrevious()
        {
            return prevNode != null;
        }


        public K previous()
        {
            K value = null;

            if( prevNode != null )
            {
                value = prevNode.getValue();
                prevNode = list.getNode(currIndex - 1);
                currIndex--;
            }

            return value;
        }


        public int previousIndex()
        {
            return Math.max(-1, currIndex - 1);
        }  


        public void remove()
        {
            list.deleteNode(thisNode, prevNode);
        }


    }


    private Node<E> head, tail;
    private int size = 0;

    public SortedSinglyDouble()
    {
        head = newNode();
        tail = head;
    }


    /* LinkedList Implementation
    ---------------------------------
    */

    public LinkedList<E> newSelf()
    {
        return new SortedSinglyDouble();
    }

    public boolean backwardable()
    {
        return false;
    }

    public Node<E> getHead()
    {  
        return head;
    }


    public Node<E> getTail()
    {
        return tail;
    }


    public Node<E> newNode()
    {
        return new SinglyNode();
    }


    public Node<E> newNode(E val)
    {
        return new SinglyNode(val);
    }


    /* List implementation
    -------------------------
    */


    public boolean add(E e)
    {
        if( e == null )
            throw new NullPointerException("Sortable must not be null!");

        boolean added = true;
        // if list is empty
        if( isEmpty() )
        {
            head.setValue(e);
        }
        else if( e.compareTo(head.getValue()) < 0 )
        {
            // head insert (min value)
            Node<E> newNode = newNode(e);
            newNode.setNext(head);
            head = newNode;
        }
        else if( e.compareTo(tail.getValue()) >= 0 )
        {
            // tail insert (max value)
            Node<E> newNode = newNode(e);

            tail.setNext(newNode);
            tail = newNode;
        }
        else
        {
            // body insert (head is the min value guaranteed)
            // so we test from the second entry onward
            Node<E> thisNode = head.next();
            Node<E> lastNode = head;
            boolean bodyAdded = false;
            while(thisNode != null)
            {

                if( e.compareTo(thisNode.getValue()) < 0)
                {
                    Node<E> newNode = newNode(e);
                    lastNode.setNext(newNode);
                    newNode.setNext(thisNode);

                    bodyAdded = true;
                    break;
                }

                
                lastNode = thisNode;
                thisNode = thisNode.next();
            }
            added = bodyAdded;
        }

        if( added ) 
            size++;

        return added;
    }


    public boolean addAll(Collection<? extends E> c)
    {
        boolean allAdded = true;
        // since c could be unsorted, we shoudl add each element individually
        // should define some way of adding sorted collections to list
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
        boolean contained = false;
        while(thisNode != null)
        {

            contained = thisNode.getValue().equals(o);
            if( contained )
                break;

            thisNode = thisNode.next();
        }

        return contained;
    }


    public boolean containsAll(Collection<?> c)
    {
        boolean allContained = true;
        for( Object o : c )
        {
            allContained = allContained && contains(o);

            if(! allContained )
                break;
        }

        return allContained;
    }


    public boolean equals(Object o)
    {
        boolean equal = false;
        if( o instanceof SortedSinglyDouble )
        {
            SortedSinglyDouble comp = (SortedSinglyDouble)o;

            if( size == comp.size )
                // in testing if heads match, the entire list gets tested recursively
                if( head.equals(comp.head) )
                {
                    equal = true;
                }
        }

        return equal;
    }


    public String toString()
    {
        return "[" + head.toString() + "]"; 
    }


    public Node<E> getNode(int index)
    {
        if( index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " Out of bounds for range 0 - " + (size - 1) );

        Node<E> thisNode = head;
        for( int ii = 0; ii < index; ii++ )
            thisNode = thisNode.next();

        return thisNode;
    }


    public E get(int index)
    {
        return getNode(index).getValue();
    }


    public int hashCode()
    {
        int hash = 7;
        hash = (int) (size ^ (size >>> 32) );

        hash += head.hashCode();
        

        return hash;
    }


    public int indexOf(Object o)
    {
        int index = 0;
        Node<E> thisNode = head;

        while(thisNode != null)
        {

            if( thisNode.getValue().equals(o) )
                break;
            thisNode = thisNode.next();
            index++;
        }

        return index;
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
        int index = 0, found = 0;
        Node<E> thisNode = head;

        while(thisNode != null )
        {

            if( thisNode.getValue().equals(o)) 
                found = index;

            index++;
            thisNode = thisNode.next();
        }

        return found;
    }


    public ListIterator<E> listIterator()
    {
        return new SortedSinglyDoubleListerator<E>(this);
    }

    public ListIterator<E> listIterator(int index)
    {
        return new SortedSinglyDoubleListerator<E>(this, index);
    }

    public E remove(int index)
    {
        if( index < 0 || index >= size )
            throw new IndexOutOfBoundsException("Index " + index + " Out of bounds for range 0 - " + (size - 1) );


        E value = null;
        Node<E> thisNode = head,
                prevNode = null;
        for( int ii = 0; ii < index; ii++ )
        {
            prevNode = thisNode;
            thisNode = thisNode.next();
        }

        if( thisNode != null )
            value = thisNode.getValue();

        deleteNode(thisNode, prevNode);



        // E value = null;
        // if( index == 0 )
        // {
        //     Node<E> oldHead = head;
        //     value = oldHead.getValue();

        //     if( size == 1 )
        //         head.wipe();
        //     else
        //     {
        //         // head delete
        //         head = head.next();
        //         oldHead.wipe();
        //     }
        // }
        // else
        // {
        //     // body delete
        //     Node<E> thisNode = head;

        //     // we want the node before the node we're deleting
        //     for( int ii = 0; ii < index - 2; ii++ )
        //         thisNode = thisNode.next();

        //     //thisNode is the node before the node we're deleting

        //     Node<E> toRemoveNode = thisNode.next();

        //     thisNode.setNext(thisNode.next() != null ? thisNode.next().next() : null);
        //     value = toRemoveNode.getValue();
        //     toRemoveNode.wipe();            

        //     // should only happen if we deleted the tail element
        //     if( thisNode.next() == null )
        //         tail = thisNode;
        // }

        size--;
        return value;
    }

    public boolean remove(Object o)
    {
        boolean removed = false;

        Node<E> thisNode = head;
        Node<E> prevNode = null;
        while(thisNode != null)
        {
            if( o == null && thisNode.getValue() == null )
                deleteNode(thisNode, prevNode);

            else if( o != null && o.equals(thisNode.getValue()))
                deleteNode(thisNode, prevNode);

            prevNode = thisNode;
            thisNode = thisNode.next();
        }


        return removed;
    }

    public boolean removeAll(Collection<?> c)
    {
        boolean allRemoved = true;
        for( Object o : c )
            allRemoved = allRemoved && remove(o);

        return allRemoved;
    }   

    public boolean retainAll(Collection<?> c)
    {
        Node<E> thisNode = head;
        Node<E> prevNode = null;

        while(thisNode != null )
        {

            // if this value is not in c, delete
            if(! c.contains(thisNode.getValue()) )
                deleteNextNode(prevNode);

            prevNode = thisNode;
            thisNode = thisNode.next();

        }

        return true;
    }


    public void deleteNextNode(Node<E> prevNode)
    {
        Node<E> thisNode = prevNode.next();

        if( thisNode != null )
        {
            prevNode.setNext(thisNode.next());

            thisNode.wipe();

            if( prevNode.next() == null )
            {
                // tail delete
                tail = prevNode;
            }
        }
    }

    /*
    Method: deleteNode
     - Deletes thisNode, requires the node previous to delete
    */  
    public void deleteNode(Node<E> thisNode, Node<E> prevNode )
    {
        if( prevNode == null )
        {
            // head delete
            if( thisNode.next() == null )
            {
                head.wipe();
            }
            else
            {
                head = thisNode.next();
                thisNode.wipe();
            }
        }
        else
        {
            // body delete
            if( thisNode != null )
            {
                prevNode.setNext(thisNode.next());
                thisNode.wipe();
            }
            else
                tail = prevNode;


            if( prevNode.next() == null )
            {
                // tail delete
                tail = prevNode;
            }
        }
    }

    public int size()
    {
        return size;
    }

    public List<E> subList(int fromIndex, int toIndex)
    {
        if( fromIndex < 0 || fromIndex > size )
            throw new IndexOutOfBoundsException("Index " + fromIndex + " Out of bounds for range 0 - " + (size - 1) );

        if( toIndex < 0 || toIndex > size )
            throw new IndexOutOfBoundsException("Index " + toIndex + " Out of bounds for range 0 - " + (size - 1) );


        List<E> sortedList = new SortedSinglyDouble();

        Node<E> thisNode = head;

        int index = 0;
        for( index = 0; index < fromIndex - 1; index++ )
            thisNode = thisNode.next();

        while(index < toIndex - 1)
        {
            if( thisNode != null )
            {
                sortedList.add(thisNode.getValue());
                thisNode = thisNode.next();
            }

            index++;
        }

        return sortedList;
    }

    public Object[] toArray()
    {
        Object[] exp = new Object[size];

        Node<E> thisNode = head;

        int index = 0;
        while(thisNode != null)
        {
            exp[index] = thisNode.getValue();

            index++;
            thisNode = thisNode.next();
        }

        return exp;
    }

    public <T> T[] toArray(T[] arrayType)
    {
        if( arrayType == null )
            throw new NullPointerException("null is not a valid array type!");

        // if(! T.isAssignableFrom(E) )
        //     throw new ArrayStoreException(); 

        T[] toUse = arrayType;

        // if( arrayType.length < size )
        // {
        //     Object array = Array.newInstance(T.getClass(), size);
        //     try
        //     {
        //         toUse = (T[])array;
        //     }
        //     catch(ClassCastException e)
        //     {
        //         throw new ArrayStoreException(e.getMessage());
        //     }
        //     // make a new array 
        // }


        Node<E> thisNode = head;

        int index = 0;
        while(thisNode != null)
        {
            toUse[index] = (T)thisNode.getValue();

            index++;
            thisNode = thisNode.next();
        }

        if( toUse.length > size )
            toUse[index] = null;


        return toUse;

    }
}
