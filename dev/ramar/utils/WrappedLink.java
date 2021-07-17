package dev.ramar.utils;


public class WrappedLink<E, K>
{
    private WrappedLink<K, ?> link;

    private E value;

    public WrappedLink()
    {
        value = null;
        link = null;
    }

    public WrappedLink(E val)
    {
        value = val;
    }


    /* Accessors
    -==------------
    */

    public E getValue()
    {
        return value;
    }

    public WrappedLink<K, ?> getLink()
    {
        return link;
    }


    public K getLinkVal()
    {
        if( link != null )
            return link.value;

        return null;
    }


    /* Mutators
    -==-----------
    */

    public void setValue(E val)
    {
        value = val;
    }


    public void setLink(WrappedLink<K, ?> link)
    {
        this.link = link;
    }


    public void setLinkVal(K val)
    {
        if( this.link == null )
            throw new NullPointerException();

        this.link.setValue(val);
    }

}