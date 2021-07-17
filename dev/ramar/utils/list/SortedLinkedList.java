package dev.ramar.utils.list;


import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;

/*
Abstract Class: SortedLinkedList
 - A sub-type of LinkedLists, which sort on insert
 - An intermediary step for the List implementation, which
   defines all the unsupported operations defined by these types of lists
*/
public abstract class SortedLinkedList<E> extends LinkedList<E>
{

    @Override
    public void add(int index, E element)
    {
        throw new UnsupportedOperationException("Indexing elements isn't supported by SortedLinkedLists!");
    }


    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        throw new UnsupportedOperationException("Indexing elements isn't supported by SortedLinkedLists!");
    }


    @Override
    public E set(int index, E element)
    {
        throw new UnsupportedOperationException("Modifying elements via Indexing isn't supported by SortedLinkedList");
    }


    /* Methods to implement when sub-classing:
    -------------------------------------------------
    */


    public abstract boolean add(E e);

    public abstract boolean addAll(Collection<? extends E> c);

    public abstract void clear();

    public abstract boolean contains(Object o);

    public abstract boolean containsAll(Collection<?> c);

    public abstract boolean equals(Object o);

    public abstract E get(int index);

    public abstract int hashCode();

    public abstract int indexOf(Object o);

    public abstract boolean isEmpty();

    public abstract Iterator<E> iterator();

    public abstract int lastIndexOf(Object o);

    public abstract ListIterator<E> listIterator();

    public abstract ListIterator<E> listIterator(int index);

    public abstract E remove(int index);

    public abstract boolean remove(Object o);

    public abstract boolean removeAll(Collection<?> c);

    public abstract boolean retainAll(Collection<?> c);

    public abstract int size();

    public abstract List<E> subList(int fromIndex, int toIndex);

    public abstract Object[] toArray();

    public abstract <T> T[] toArray(T[] arrayType);

}   