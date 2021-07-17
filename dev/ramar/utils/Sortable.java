
package dev.ramar.utils;

public interface Sortable<E>
{
    public E getValue();

    public int compareTo(E val);
}