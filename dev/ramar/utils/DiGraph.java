package dev.ramar.utils;

import dev.ramar.utils.nodes.Node;
import dev.ramar.utils.nodes.ListNode;
import dev.ramar.utils.nodes.Node.ValueListener;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import java.util.Iterator;

public class DiGraph<E> implements Iterable<E>
{
    private Map<E, Node<E>> nodes = new HashMap<>();

    private ValueListener<E> vl = (E from, E to) ->
    {
        Node<E> selected = nodes.get(from);
        if( selected != null )
        {
            nodes.remove(from);
            nodes.put(to, selected);
        }
    };

    public DiGraph()
    {

    }

    /* Iterable Implementation
    --===------------------------
    */

    public Iterator<E> iterator()
    {
        return nodes.keySet().iterator();
    }

/*    private static class DigraphIterator<T> implements Iterator<T>
    {
        Set<T> values;
        int index = 0;
        DiGraph<T> d;
        public DigraphIterator(DiGraph<T> d)
        {   
            this.d = d;
            values = d.nodes.keySet();
        }

        public boolean hasNext()
        {
            return index < values.size();
        }

        public T next()
        {
            index++;
            return values.get(index - 1);
        }
    }
*/

    public void add(Node<E> node)
    {
        if( nodes.containsKey(node.getValue()) )
            throw new IllegalArgumentException("Can't add: " + node.getValue() + " already exists");

        set(node);
    }

    public Node<E> add(E val)
    {
        ListNode<E> node = new ListNode<E>(val);
        add(node);
        return node;
    }


    public Node<E> get(E val)
    {
        return nodes.get(val);
    }


    public void set(Node<E> node)
    {
        if( !node.listeners.value.contains(vl) )
            node.listeners.value.add(vl);

        Node<E> removed = nodes.remove(node.getValue());
        if( removed != null )
            removed.listeners.value.remove(vl);

        nodes.put(node.getValue(), node);
    }


    public void connect(E from, E to)
    {
        Node<E> fn = nodes.get(from),
                tn = nodes.get(to);

        fn.addLink(tn);
    }

    public void remove(E from, E to)
    {
        Node<E> fn = nodes.get(from),
                tn =   nodes.get(to);

        fn.removeLink(tn);
    }

    public void connect(Node<E> from, Node<E> to)
    {
        // we only want to connect if <from> and <to> are in the network
        if( nodes.get(from.getValue()).equals(from) && 
            nodes.get(  to.getValue()).  equals(to) )
            connect(from.getValue(), to.getValue());
        else
            throw new IllegalArgumentException(from.getValue() + " -> " + to.getValue() + " does not exist in the digraph!");
    }


    public boolean contains(E val)
    {
        return nodes.containsKey(val);
    }

    public boolean contains(Node<E> node)
    {
        boolean out = false;

        if( node != null )
            out = this.contains(node.getValue());

        return out;
    }


    public void clear()
    {
        nodes.clear();
    }

    /* Testing
    -===---------
    */


    private static abstract class Test
    {
        public final String name;
        private Boolean success = null;
        public Test(String name)
        {
            this.name = name;
        }

        protected abstract boolean test();

        public void start()
        {
            success = test();
        }

        public boolean isFinished()
        {  return success != null; }

        public boolean isSuccess()
        {  return success != null && success; }
    }

    public static void main(String[] args)
    {
        List<Test> tests = new ArrayList<>();

        tests.add(new Test("listening test")
        {
            protected boolean test()
            {
                DiGraph<String> graph = new DiGraph<>();
                ListNode<String> n1 = new ListNode<>("1");

                graph.add(n1);
                n1.setValue("2");
                boolean check1 = graph.get("2").getValue().equals("2");

                return check1;

            }
        });

        tests.add(new Test("connectivity test")
        {
            protected boolean test()
            {
                DiGraph<String> graph = new DiGraph<>();

                ListNode<String> n1 = new ListNode<>("1"),
                                 n2 = new ListNode<>("2"),
                                 n3 = new ListNode<>("3");

                graph.add(n1);
                graph.add(n2);
                graph.add(n3);

                graph.connect("1", "2");
                graph.connect(n2, n3);
                graph.connect("3", "1");
                graph.connect(n1, n1);

                boolean out = false;
                try
                {
                    out = graph.get("1").getLink(0).equals(n2);

                    out = out && graph.get("2").getLink(0).equals(n3);

                    out = out && graph.get("3").getLink(0).equals(n1);

                    out = out && graph.get("1").getLink(1).equals(n1);
                }
                catch(IndexOutOfBoundsException e) { out = false; }

                return out;
            }
        });


        for( Test t : tests )
        {
            t.start();
            System.out.println(t.name + " :: " + (t.isSuccess() ? "Success" : "Failure"));
        }
    }


}