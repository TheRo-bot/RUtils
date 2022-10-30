package dev.ramar.utils;


public class PairedValues<K, V>
{
    private K k = null;
    private V v = null;



    public PairedValues()
    {}

    public PairedValues(K k, V v)
    {
        this.setKV(k, v);
    }

    public String toString()
    {
        return "{" + k + ", " + v + "}";
    }

    public void setK(K k)
    {
        this.k = k;
    }

    public PairedValues<K, V> withK(K k)
    {
        this.setK(k);
        return this;
    }

    public void setV(V v)
    {
        this.v = v;
    }

    public PairedValues<K, V> withV(V v)
    {
        this.setV(v);
        return this;
    }

    public void setKV(K k, V v)
    {
        this.setK(k);
        this.setV(v);
    }

    public PairedValues<K, V> withKV(K k, V v)
    {
        this.setKV(k, v);
        return this;
    }


    public void setVK(V v, K k)
    {
        this.setV(v);
        this.setK(k);
    }


    public PairedValues<K, V> withVK(V v, K k)
    {
        this.setVK(v, k);
        return this;
    }


    public K getK()
    {   return this.k;   }


    public V getV()
    {   return this.v;   }

}