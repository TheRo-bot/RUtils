package dev.ramar.utils.list;

public interface Sortable<E>
{
    public E getValue();

    public int compareTo(E val);
}