package dev.ramar.utils;



public class ValuePair<E, K>
{


    private WrappedLink<E, K> one = new WrappedLink<E, K>();
    private WrappedLink<K, E> two = new WrappedLink<K, E>();

    public ValuePair(E a, K b)
    {
        this.one.setValue(a);
        this.one.setLink(this.two);
        this.two.setValue(b);
        this.two.setLink(this.one);
    }   


    public void setOneVal(E val)
    {
        this.one.setValue(val);
    }


    public void setTwoVal(K val)
    {
        this.two.setValue(val);
    }

    public E getOneVal()
    {
        return this.one.getVal();
    }


    public K getTwoVal()
    {
        return this.two.getVal();
    }





}