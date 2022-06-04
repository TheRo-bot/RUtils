package dev.ramar.utils;


import java.util.List;

public class ListenableList<E> extends HiddenList<E>
{



    public final LocalList<OnAdd<E>> onAdd = new LocalList<>();
    public final RemoveList<E> onRemove = new RemoveList<>();

    protected final void onAdd(E... es)
    {
        List<OnAdd<E>> li = this.onAdd.getList();
        for( OnAdd<E> oa : li )
            oa.onAdd(es);
    }

    protected final void onRemove(E e)
    {
        List<OnRemoveElement<E>> li = this.onRemove.ele.getList();
        for( OnRemoveElement<E> ore : li )
            ore.onRemove(e);
    }

    protected final void onRemove(int i)
    {
        List<OnRemoveIndex> li = this.onRemove.ind.getList();
        for( OnRemoveIndex ori : li )
            ori.onRemove(i);
    }


    @Override
    public HiddenList<E> add(E... es)
    {
        HiddenList<E> out = super.add(es);
        this.onAdd(es);
        return out;
    }


    @Override
    public boolean remove(E e)
    {
        boolean out = super.remove(e);
        if( out )
            this.onRemove(e);

        return out;
    }


    @Override
    public E remove(int i)
    {
        E out = super.remove(i);
        this.onRemove(i);
        return out;
    }


    /* Interface Definitions
    --===----------------------
    */

    public interface OnAdd<K>
    {   public void onAdd(K... e);   }
    
    public interface OnRemoveElement<K>
    {  public void onRemove(K e);   }

    public interface OnRemoveIndex
    {  public void onRemove(int i);  }


    /* Class Definitions
    --===------------------
    */

    public class LocalList<K> extends HiddenList
    {
        private List<K> getList()
        {  return this.list;  }
    }


    public class RemoveList<K>
    {
        public LocalList<OnRemoveElement<E>> ele = new LocalList<>();
        public LocalList<OnRemoveIndex> ind = new LocalList<>();
    }
}